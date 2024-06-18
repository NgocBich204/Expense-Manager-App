package com.example.smartwall.model;

public class Expenses {

        private String id;
        private String title;
        private String total;
        private String category;
        private String date;



        public Expenses(String id, String title, String total, String category, String date) {
            this.id = id;
            this.title = title;
            this.total = total;
            this.category = category;
            this.date = date;
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

        public String getDate() {
            return date;
        }

}
