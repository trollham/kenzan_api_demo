package com.example.kenzan_api_demo.models;

public enum EmployeeStatus {
    ACTIVE(true), INACTIVE(false);

    private boolean status;

    private EmployeeStatus(boolean status) {
        this.status = status;
    }
    public boolean getStatus() {
        return status;
    }
}
