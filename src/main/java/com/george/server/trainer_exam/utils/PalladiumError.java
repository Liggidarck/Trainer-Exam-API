package com.george.server.trainer_exam.utils;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PalladiumError {

    private int statusCode;
    private String message;

    public PalladiumError(int statusCode, String message) {
        this.statusCode = statusCode;
        this.message = message;
    }
}
