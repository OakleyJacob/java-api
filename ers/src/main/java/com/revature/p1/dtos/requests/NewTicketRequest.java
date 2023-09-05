package com.revature.p1.dtos.requests;

import java.math.BigDecimal;

public class NewTicketRequest {
    private BigDecimal amount;
    private String description;

    private String type_id;
    private String payment_id;

    public NewTicketRequest() {
    super();
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    @Override
    public String toString() {
        return "NewTicketRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", type_id='" + type_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                '}';
    }

    public NewTicketRequest(BigDecimal amount, String description, String type_id, String payment_id) {
        this.amount = amount;
        this.description = description;
        this.type_id = type_id;
        this.payment_id = payment_id;
    }
}