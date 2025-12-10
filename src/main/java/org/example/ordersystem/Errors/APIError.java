package org.example.ordersystem.Errors;

import java.time.Instant;

public class APIError {
    private Instant timestamp = Instant.now();
    private int status;
    private String message;
    private String error;
    private String path;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Instant getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }

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

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public static class FieldError {
        private String field;
        private String message;

        public String getField() {
            return field;
        }

        public String getMessage() {
            return message;
        }
    }
}
