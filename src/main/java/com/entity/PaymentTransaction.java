package com.entity;

public class PaymentTransaction {

    public static final String STATUS_NEW = "new";

    private int id;
    private String unid;

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    private int creator;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUnid() {
        return unid;
    }

    public void setUnid(String unid) {
        this.unid = unid;
    }

    public String getFromSystem() {
        return fromSystem;
    }

    public void setFromSystem(String fromSystem) {
        this.fromSystem = fromSystem;
    }

    public String getTransactionName() {
        return TransactionName;
    }

    public void setTransactionName(String transactionName) {
        TransactionName = transactionName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public int getNotifyCount() {
        return notifyCount;
    }

    public void setNotifyCount(int notifyCount) {
        this.notifyCount = notifyCount;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    private String fromSystem;

    private String TransactionName;

    private int amount;

    private String status;

    private String callbackUrl;

    private int notifyCount;

    private String createdAt;

    public String getUpdateAt() {
        return updateAt;
    }

    public void setUpdateAt(String updateAt) {
        this.updateAt = updateAt;
    }

    private String updateAt;

}
