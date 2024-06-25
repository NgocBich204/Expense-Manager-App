package com.example.smartwall.model;

public class Goal {

    public String id;
    public String goalName;
    public String goalAmount;
    public String startDate;
    public String endDate;
    public String notes;

    public Goal(String id, String goalName, String goalAmount, String startDate, String endDate, String notes) {
        this.id = id;
        this.goalName = goalName;
        this.goalAmount = goalAmount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.notes = notes;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoalName() {
        return goalName;
    }

    public void setGoalName(String goalName) {
        this.goalName = goalName;
    }

    public String getGoalAmount() {
        return goalAmount;
    }

    public void setGoalAmount(String goalAmount) {
        this.goalAmount = goalAmount;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }
}
