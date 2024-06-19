package com.example.smartwall.model;

public class Income {
    private String category;
    private String date;
    private String total;

    public Income() {

    }

    public Income(String category, String date, String total) {
        this.category = category;
        this.date = date;
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public String getDate() {
        return date;
    }

    public String getTotal() {
        return total;
    }
}
