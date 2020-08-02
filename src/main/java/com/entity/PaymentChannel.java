package com.entity;

import com.alibaba.fastjson.JSON;

import java.util.Map;

public class PaymentChannel {


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    private String code;

    private Map params;

    public Map getParams() {
        return params;
    }

    public void setParams(String params) {

        this.params = (Map) JSON.parse(params);
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    private String status;

    private String createdAt;

    private String updatedAt;

}
