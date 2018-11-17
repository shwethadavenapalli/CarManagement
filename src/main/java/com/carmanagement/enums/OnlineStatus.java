package com.carmanagement.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Arrays;

/**
 * Created by Shwetha on 15-11-2018.
 */
@JsonFormat(shape = JsonFormat.Shape.OBJECT)
public enum OnlineStatus {
    ONLINE, OFFLINE;


    @JsonCreator
    public static OnlineStatus create (String value) {
        if(value == null) {
            throw new IllegalArgumentException();
        }
        for(OnlineStatus v : values()) {
            System.out.println("v "+v);
            if(value.equals(v.name())) {
                return v;
            }
        }
        throw new IllegalArgumentException();
    }

}

