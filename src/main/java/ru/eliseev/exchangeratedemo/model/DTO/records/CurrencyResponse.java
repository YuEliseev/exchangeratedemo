package ru.eliseev.exchangeratedemo.model.DTO.records;

public record CurrencyResponse(
        String targetCurrency,
        Double rate,
        Long timestamp,
        String date
) {}
