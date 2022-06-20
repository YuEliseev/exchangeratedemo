package ru.eliseev.exchangeratedemo.model;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Map;

@Data
public class RatesDTO {
    private final String base;
    private final Map<String, BigDecimal> rates;
}
