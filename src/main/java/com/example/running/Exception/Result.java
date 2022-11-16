package com.example.running.Exception;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer code;

    private String msg;

    private String cause;
}
