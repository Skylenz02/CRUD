package com.bhanu.library.dto;

import lombok.Data;

@Data
public class Currency {
    private String code;
    private String rate;
    private String description;
    private double rate_float;
}
