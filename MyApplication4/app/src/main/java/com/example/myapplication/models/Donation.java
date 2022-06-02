package com.example.myapplication.models;

public class Donation {
    public int id;
    public int upvotes;
    public int amount;
    public String paymentmethod;

    public Donation(int upvotes, int amount, String paymentmethod) {
        this.upvotes = upvotes;
        this.amount = amount;
        this.paymentmethod = paymentmethod;
    }

    public Donation() {
        this.upvotes = 0;
        this.amount = 0;
        this.paymentmethod = "";
    }

    public String toString() {
        return id  + ", " + upvotes + ", " + amount + ", " + paymentmethod;
    }
}

