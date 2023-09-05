package com.revature.p1.dtos.requests;

import java.math.BigDecimal;

public class NewUpdateTicketRequest {
    private BigDecimal amount;
    private String description;

    private String type_id;
    private String reimb_id;
    private String payment_id;

    public NewUpdateTicketRequest() {
        super();
    }

    public NewUpdateTicketRequest(BigDecimal amount, String description, String type_id, String reimb_id, String payment_id) {
        this.amount = amount;
        this.description = description;
        this.type_id = type_id;
        this.reimb_id = reimb_id;
        this.payment_id = payment_id;
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

    public String getReimb_id() {
        return reimb_id;
    }

    public void setReimb_id(String reimb_id) {
        this.reimb_id = reimb_id;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    @Override
    public String toString() {
        return "NewUpdateTicketRequest{" +
                "amount=" + amount +
                ", description='" + description + '\'' +
                ", type_id='" + type_id + '\'' +
                ", reimb_id='" + reimb_id + '\'' +
                ", payment_id='" + payment_id + '\'' +
                '}';
    }
}