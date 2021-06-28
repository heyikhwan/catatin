package com.ikhwan.catatin.Response;

import com.ikhwan.catatin.Model.Product;

import java.util.List;

public class ResponseProduct {
    private String status, message;
    private List<Product> data;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Product> getData() {
        return data;
    }

    public void setData(List<Product> data) {
        this.data = data;
    }
}
