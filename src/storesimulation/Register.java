/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesimulation;
 
import java.util.ArrayList;
 
/**
 *
 * @author Kollen Gruizenga & Travis Wahl
 */
 
class Register {
    private double itemScanTime, payTime, maxL;
    private int totalPassedThru;
    private String regType;
   
   
    private ArrayList<Customer> que;//each registers' length
 
    Register(double d, double d0, String t){ //D=min/item D0=min to pay
        this.itemScanTime = d;
        this.payTime = d0;
        maxL = 0;
        totalPassedThru = 0;
        que = new ArrayList<Customer>();//given queue for any one register
        this.regType = t;
    }
   
    //get the type of register (standard checkout or self checkout)
    String getRegisterType(){
        return this.regType;
    }
 
    //get the size of the line for a register
    int getLineLength() {
        return this.que.size();
    }
 
    //put a customer into line for a register
    void enqueue(Customer customer) {
        this.que.add(customer);
        this.totalPassedThru++;//keep track of how many passed thru each register
        checkNewMaxL();
    }
 
    //remove a customer from the line of a register
    Customer dequeue() {//pop off the next up in line
        return this.que.remove(0);
    }
 
    //check if the queue is empty
    boolean isEmpty() {
        return this.que.isEmpty();
    }
 
    //view the next customer in line (used to see if there is another customer in line)
    Customer peek() {
        return this.que.get(0);
    }
   
    //Check the length of a line
    void checkNewMaxL(){
        if (this.que.size() > this.maxL) this.maxL = this.que.size();
    }
   
    //get the time it takles to scan an item for a given customer
    double getScanTime() {
        return this.itemScanTime; 
    }
 
    //get the time it takes to pay for a given customer
    double getPayTime() {
        return this.payTime;
    }
   
    //used to store the length into maxL
    double getMaxLength(){
        return this.maxL;
    }
   
    //get the number of customers that used a given register
    int getTotalPassThru(){
        return this.totalPassedThru;
    }
}