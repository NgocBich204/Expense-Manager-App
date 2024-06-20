package com.example.smartwall.model;

public class Income {
    private String id;
    private String title;
    private String total;
    private String category;
    private String selectedDate;

    public Income() {
        // Default constructor required for calls to DataSnapshot.getValue(Income.class)
    }

    public Income(String id, String title, String total, String category, String selectedDate) {
        this.id = id;
        this.title = title;
        this.total = total;
        this.category = category;
        this.selectedDate = selectedDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getTotal() {
        return total;
    }

    public String getCategory() {
        return category;
    }

    public String getSelectedDate() {
        return selectedDate;
    }
}
