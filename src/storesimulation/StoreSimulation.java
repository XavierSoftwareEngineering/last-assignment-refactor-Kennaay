/**
 * Store Simulation Project
 * This file controls the flow of the store simulation.
 */
package storesimulation;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;

/**
 *
 * @author Katie Timmerman CS 180 - 02 Prof: Dr Timmerman Assignment:
 * @author Kollen Gruizenga and Travis Wahl
 */
public class StoreSimulation {
 
    private static final int NUMBER_STANDARD_CHECKOUT = 4; // number of cashier registers
    private static final int NUMBER_SELF_CHECKOUTS = 2; // number of self-scan registers
    private static double simClock = 0; // elapsed time (minutes)
    private static MyHeap events = new MyHeap(); // events that occur in the store
    private static ArrayList<Register> registers = new ArrayList(); // registers used in the store
    private static ArrayList<Customer> customerArray = new ArrayList(); // customers in the store
 
    public static void main(String[] args) {
       
        loadRegisters();
        loadCustomerData();
 
        // Events are stored in a priority queue, so they will always be returned in order.
        while (events.getSize() > 0) {
            Event e = events.remove();
            simClock = e.getEventTime(); // Always set the clock to the time of the new event.
            if (e.getEventType() == EventType.ARRIVAL) {
                handleArrival(e);
            } else if (e.getEventType() == EventType.END_SHOPPING) {
                handleEndShopping(e);
            } else {
                handleEndCheckout(e);
            }
        }
        printCustomerAvgWaitTime();
        printAvgRegWaitTime();
        printRegTotalPassThrough();
        printRegMaxLength();
        printPercentCustomerWaitTime();
        printTotalNumItems();
 
    }
   
    // creates new empty registers top load customers into
    private static void loadRegisters() {
        for (int i = 0; i < NUMBER_STANDARD_CHECKOUT; i++) {
            Register r = new Register(0.01, 1.5, "STANDARD");
            registers.add(r);
        }
        for (int i = 0; i < NUMBER_SELF_CHECKOUTS; i++) {
            Register r = new Register(0.04, 3.0, "SELF");
            registers.add(r);
        }
    }
 
    //loads in the "arrival" text document containing the customer data for the simulation
    private static void loadCustomerData() {
        double arriveTime, avgSelectionTime;
        int items;
 
        try {
            File myFile = new File("arrival.txt");
            Scanner inputFile = new Scanner(myFile);
            while (inputFile.hasNext()) {
                arriveTime = inputFile.nextDouble();
                items = inputFile.nextInt();
                avgSelectionTime = inputFile.nextDouble();
                Customer customer = new Customer(arriveTime, items, avgSelectionTime);
                Event event = new Event(customer, arriveTime, EventType.ARRIVAL);
                events.insert(event);
            }//end while
            inputFile.close();
        } catch (FileNotFoundException e) {
            System.err.println("File was not found");
            System.exit(0);
        }
    }
 
    //gets the customer that arrived and calculates their shopping time at the end of their trip
    private static void handleArrival(Event e) {
        Customer c = e.getCustomer();
        double endShoppingTime = c.getArriveTime() + c.getNumItems() * c.getAvgSelectionTime();
        Event endShopping = new Event(c, endShoppingTime, EventType.END_SHOPPING);
        events.insert(endShopping);
    }
 
    //gets the customer that is readsy to checkout and sorts them into the shortest line
    private static void handleEndShopping(Event e) {
        Customer customer = e.getCustomer();
        int shortest = getShortestLine(customer);
       
        customer.setRegType(registers.get(shortest).getRegisterType());//store the type of register being added to line for
        customer.setCheckoutLine(shortest); // Customer will always enter shortest checkout line.
       
        registers.get(shortest).enqueue(customer); // Even if line is empty, customer must be enqueued and dequeued so that the customer gets included in the stats for the register
        customer.setStartWaitTime(simClock);//set the start waiting time
       
        if (registers.get(shortest).getLineLength() == 1) { // If new customer is the only one in line, begin checkout.
            startCheckout(customer);
        }
    }
 
    //dequeues the customer at the front of a checkout line to begin the end of checkout process
    private static void handleEndCheckout(Event e) {
        int line = e.getCustomer().getCheckoutLine();
        Customer c = registers.get(line).dequeue();
        customerArray.add(c);
        if (registers.get(line).isEmpty()) {
            return;
        } else {
            Customer customer = registers.get(line).peek();
            startCheckout(customer);
        }
    }
    
    //begins the checkout process for a customer necessary in knowing how long they waited in the line 
    private static void startCheckout(Customer customer) {
        int line = customer.getCheckoutLine();
        customer.setWaitTime(simClock - customer.getStartWaitTime());//calculate & save the waiting time

        double checkoutLength = customer.getNumItems() * registers.get(line).getScanTime() + registers.get(line).getPayTime();
        Event endCheckout = new Event(customer, checkoutLength + simClock, EventType.END_CHECKOUT);
        events.insert(endCheckout);   
    }
 
    //print to the console the statistics that were collected during the simulation
    private static void printCustomerAvgWaitTime() {
        System.out.println("COLLECTED STATISTICS:");
        
        double totalWaitTime = 0;
        //prints the Average Wait Time Per Customer For The Store
        for (int i = 0; i < customerArray.size(); i++) {
            totalWaitTime += customerArray.get(i).getWaitTime();
        }
        System.out.println("Average Waiting Time for Customers in the Store: " + totalWaitTime/customerArray.size());
    }
    private static void printAvgRegWaitTime() {    
        //prints the Average Waiting Time per Register Style
        double standardWaitTime = 0, selfWaitTime = 0;
        int counterStandard = 0, counterSelf = 0;
         for (int i = 0; i < customerArray.size(); i++) {
            if (customerArray.get(i).getRegType() == "STANDARD"){
                standardWaitTime += customerArray.get(i).getWaitTime();
                counterStandard++;
            } else {
                selfWaitTime += customerArray.get(i).getWaitTime();
                counterSelf++;                
            }    
        }
        System.out.println("Average Standard Register Wait Time: " + standardWaitTime/counterStandard + " minutes.");
        System.out.println("Average Self Register Wait Time: " + selfWaitTime/counterSelf + " minutes.");
    }
    
    private static void printRegTotalPassThrough() {
        //prints the Total Customers Passing Through Each Individual Register Line
        for (int i = 0; i < registers.size(); i++){
          System.out.println("Register " + (i+1) + ": Total Customer Pass Through: " + registers.get(i).getTotalPassThru());  
        }
    }
    
    private static void printRegMaxLength() {
        //prints the Maximum Length of Each Register Line
        for (int i = 0; i < registers.size(); i++) {
            System.out.println("Register " + (i+1) + ": Maximum Length of Line: " + registers.get(i).getMaxLength());
        }
    }

    private static void printPercentCustomerWaitTime() {    
        //prints the Percentage of Customers who waited for more than 2, 3, 5, and 10 minutes
        double counter2m = 0;
        double counter3m = 0;
        double counter5m = 0;
        double counter10m = 0;
        for (int i = 0; i < customerArray.size(); i++) {
            if (customerArray.get(i).getWaitTime() >= 2) {
                counter2m++;
            }
            if (customerArray.get(i).getWaitTime() >= 3) {
                counter3m++;
            }
            if (customerArray.get(i).getWaitTime() >= 5) {
                counter5m++;
            }
            if (customerArray.get(i).getWaitTime() >= 10)
                counter10m++;
        }
        System.out.println("Percentage of Customers waiting 2 minutes or longer: " + (counter2m/customerArray.size())*100);
        System.out.println("Percentage of Customers waiting 3 minutes or longer: " + (counter3m/customerArray.size())*100);
        System.out.println("Percentage of Customers waiting 5 minutes or longer: " + (counter5m/customerArray.size())*100);
        System.out.println("Percentage of Customers waiting 10 minutes or longer: " + (counter10m/customerArray.size())*100);
    }
    
    private static void printTotalNumItems() {
        //prints the total number of items scanned in the simulation
        int items = 0;
        for (int i = 0; i < customerArray.size(); i++) {
            items += customerArray.get(i).getNumItems();
        }
        System.out.println("Total Number of Items Scanned: " + items);
    }

   //find the shortest checkout line
    private static int getShortestLine(Customer c) { // initialize min to the length of the first register object within the arrayList of registers
        int currLeng, min = registers.get(0).getLineLength(), minIndex = 0;
 
        int endPoint = registers.size();
        if (c.getNumItems() > 50) {
            endPoint = NUMBER_STANDARD_CHECKOUT;
        }
 
        for (int r = 0; r < endPoint; r++) {
            currLeng = registers.get(r).getLineLength();
            if (currLeng < min) {
                min = currLeng;
                minIndex = r;
 
            }
        }
        return minIndex;
    }
 
}