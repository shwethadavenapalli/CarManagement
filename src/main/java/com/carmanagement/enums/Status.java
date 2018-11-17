package com.carmanagement.enums;

/**
 * Created by Shwetha on 16-11-2018.
 */
public enum Status {
    ACTIVE, RELEASED, UNDEFINED;

    public static Status getValue(String status) {
        if (status.equalsIgnoreCase(Status.ACTIVE.name())) {
            return Status.ACTIVE;
        }
        if (status.equalsIgnoreCase(Status.RELEASED.name())) {
            return Status.RELEASED;
        } else return Status.UNDEFINED;
    }
}
