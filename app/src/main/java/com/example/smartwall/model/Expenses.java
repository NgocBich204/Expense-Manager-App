package com.example.smartwall.model;

public class Expenses {
    private String id;
    private String title;
    private String total;
    private String category;
    private String date;

    public Expenses() {

    }

    public Expenses(String id, String title, String total, String category, String date) {
        this.id = id;
        this.title = title;
        this.total = total;
        this.category = category;
        this.date = date;
    }

    // Getters and setters (optional)
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
