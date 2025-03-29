package com.nhom07.DAMH_LTUD.model;

public class VnpayResponse {
    private boolean success;
    private String orderDescription;
    private String orderId;
    private String transactionId;
    private String token;
    private String vnPayResponseCode;

    // Constructor, getters and setters

    public VnpayResponse(boolean success) {
        this.success = success;
    }
    public VnpayResponse(boolean success, String orderDescription, String orderId, String transactionId, String token, String vnPayResponseCode) {
        this.success = success;
        this.orderDescription = orderDescription;
        this.orderId = orderId;
        this.transactionId = transactionId;
        this.token = token;
        this.vnPayResponseCode = vnPayResponseCode;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getOrderDescription() {
        return orderDescription;
    }

    public void setOrderDescription(String orderDescription) {
        this.orderDescription = orderDescription;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getVnPayResponseCode() {
        return vnPayResponseCode;
    }

    public void setVnPayResponseCode(String vnPayResponseCode) {
        this.vnPayResponseCode = vnPayResponseCode;
    }
}
