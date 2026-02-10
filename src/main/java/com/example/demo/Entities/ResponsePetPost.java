package com.example.demo.Entities;

public class ResponsePetPost {
    private String transactionId;
    private String dateCreated;
    private String name;
    private Boolean status;

    public String getTransactionId() {
        return transactionId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public String getName() {
        return name;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public ResponsePetPost(Boolean status, String name, String dateCreated, String transactionId) {
        this.status = status;
        this.name = name;
        this.dateCreated = dateCreated;
        this.transactionId = transactionId;
    }

    public ResponsePetPost() {
    }
}
