package com.nqlo.ch.mkt.service.entities;

import java.time.LocalDateTime;

public class ErrorResponse {
    private String message;
    private String error;
    private String timestamp;
    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    private int status;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public ErrorResponse(String message, String error, int status) {
        this.message = message;
        this.error = error;
        this.timestamp = LocalDateTime.now().toString();
        this.status = status;
    }
}
