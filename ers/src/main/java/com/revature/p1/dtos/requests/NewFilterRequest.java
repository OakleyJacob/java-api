package com.revature.p1.dtos.requests;

import java.math.BigDecimal;

public class NewFilterRequest {
    private String author_id;
    private String type_id;
    private String status_id;


    public NewFilterRequest() {
        super();
    }

    public NewFilterRequest(String author_id, String type_id, String status_id) {
        this.author_id = author_id;
        this.type_id = type_id;
        this.status_id = status_id;
    }

    @Override
    public String toString() {
        return "NewFilterRequest{" +
                "author_id='" + author_id + '\'' +
                ", type_id='" + type_id + '\'' +
                ", status_id='" + status_id + '\'' +
                '}';
    }

    public String getAuthor_id() {
        return author_id;
    }

    public void setAuthor_id(String author_id) {
        this.author_id = author_id;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getStatus_id() {
        return status_id;
    }

    public void setStatus_id(String status_id) {
        this.status_id = status_id;
    }
}