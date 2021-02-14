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
 
    void setCheckoutLine(int registerIndex) {
        this.registerIndex = registerIndex;
    }
   
    int getCheckoutLine(){
        return this.registerIndex;
    }
   
    public void setStartWaitTime(double i){
        this.startWaitTime = i;
    }
   
    public void setWaitTime(double i){
        this.waitTime = i;
    }
   
    public void setRegType(String r){
        this.registerType = r;
    }
   
    public double getStartWaitTime(){
        return this.startWaitTime;
    }
   
    public double getWaitTime(){
        return this.waitTime;
    }
   
    public String getRegType(){
        return this.registerType;
    }
 
   
}