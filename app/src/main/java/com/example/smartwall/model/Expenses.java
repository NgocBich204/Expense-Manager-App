package com.example.smartwall.model;

public class Expenses {
    private String category;
    private String date;
    private String id;
    private String title;
    private String total;

    // Constructor mặc định không tham số
    public Expenses() {
        // Cần thiết cho Firebase
    }

    // Constructor đầy đủ tham số
    public Expenses(String category, String date, String id, String title, String total) {
        this.category = category;
        this.date = date;
        this.id = id;
        this.title = title;
        this.total = total;
    }

    // Getter và setter
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
}
