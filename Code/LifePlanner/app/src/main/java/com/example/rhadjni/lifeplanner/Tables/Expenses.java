/**
 * Implemented by Rhadjni Phipps
 */
package com.example.rhadjni.lifeplanner.Tables;

public class Expenses {
    private int id;
    private String Name;
    private float amount;
    private String checker;

    public Expenses(String name, float amount, float totalincome,String checker) {
        Name = name;
        this.amount = amount;
        this.totalincome = totalincome;
        this.checker=checker;
    }



    public Expenses(int id, String name, float amount, float totalincome,String checker) {
        this.id = id;
        Name = name;
        this.amount = amount;
        this.totalincome = totalincome;
        this.checker=checker;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public float getTotalincome() {
        return totalincome;
    }

    public void setTotalincome(float totalincome) {
        this.totalincome = totalincome;
    }

    private float totalincome;



    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }

    public String getChecker() {
        return checker;
    }

    public void setChecker(String checker) {
        this.checker = checker;
    }


}
