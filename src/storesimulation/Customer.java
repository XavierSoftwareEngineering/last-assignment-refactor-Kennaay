/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package storesimulation;

/**
 *
 * @author Kollen Gruizenga && Travis Wahl
 */
class Customer {
    private double arriveTime;
    private int numItems;
    private double avgSelectionTime;
    private int registerIndex;
   
    private double startWaitTime, waitTime;
    private String registerType;
 
    Customer(double arriveTime, int items, double avgSelectionTime) {
        setArriveTime(arriveTime);
        setNumItems(items);
        setAvgSelectionTime(avgSelectionTime);
    }
 
    /**
     * @return the arriveTime
     */
    public double getArriveTime() {
        return arriveTime;
    }
 
    /**
     * @param arriveTime the arriveTime to set
     */
    public void setArriveTime(double arriveTime) {
        this.arriveTime = arriveTime;
    }
 
    /**
     * @return the numItems
     */
    public int getNumItems() {
        return numItems;
    }
 
    /**
     * @param numItems the numItems to set
     */
    public void setNumItems(int numItems) {
        this.numItems = numItems;
    }
 
    /**
     * @return the avgSelectionTime
     */
    public double getAvgSelectionTime() {
        return avgSelectionTime;
    }
 
    /**
     * @param avgSelectionTime the avgSelectionTime to set
     */
    public void setAvgSelectionTime(double avgSelectionTime) {
        this.avgSelectionTime = avgSelectionTime;
    }
 
    //sets the checkout line that a customer is put into
    void setCheckoutLine(int registerIndex) {
        this.registerIndex = registerIndex;
    }
   
    //returns which checkout line the customer is in
    int getCheckoutLine(){
        return this.registerIndex;
    }
   
    //sets the time the customer started waiting
    public void setStartWaitTime(double i){
        this.startWaitTime = i;
    }
   
    //sets the time the customer spent waiting
    public void setWaitTime(double i){
        this.waitTime = i;
    }
   
    //sets the register type that the customer is using (Standard or Self)
    public void setRegType(String r){
        this.registerType = r;
    }
   
    //returns the time the customer started waiting
    public double getStartWaitTime(){
        return this.startWaitTime;
    }
   
    //returns the time the customer spent waiting
    public double getWaitTime(){
        return this.waitTime;
    }
   
    //returns the type of register the customer is using (Standard or Self)
    public String getRegType(){
        return this.registerType;
    }
 
   
}