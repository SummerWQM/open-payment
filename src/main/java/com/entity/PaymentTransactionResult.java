package com.entity;

public class PaymentTransactionResult {

    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPaymentTransactionUnid() {
        return paymentTransactionUnid;
    }

    public void setPaymentTransactionUnid(String paymentTransactionUnid) {
        this.paymentTransactionUnid = paymentTransactionUnid;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(int discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFinishAt() {
        return finishAt;
    }

    public void setFinishAt(String finishAt) {
        this.finishAt = finishAt;
    }

    public String getMessageInfo() {
        return messageInfo;
    }

    public void setMessageInfo(String message_info) {
        this.messageInfo = message_info;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    private String paymentTransactionUnid;

    private int amount;

    private int discountAmount;

    private String status;

    private String finishAt;


    private String messageInfo;

    private String createdAt;

    private String updateAt;


}
