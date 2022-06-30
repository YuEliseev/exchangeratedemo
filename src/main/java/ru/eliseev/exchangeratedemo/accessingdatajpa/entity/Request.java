package ru.eliseev.exchangeratedemo.accessingdatajpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
public class Request {
    @Id
    private UUID id;

    private LocalDate date;
    private LocalDate comparingDate;
    private String symbols;
    private BigDecimal latestRates;
    private BigDecimal comparingRate;

    public Request(LocalDate date, LocalDate comparingDate, String symbols, BigDecimal latestRates, BigDecimal comparingRate) {
        this.id = UUID.randomUUID();
        this.date = date;
        this.comparingDate = comparingDate;
        this.symbols = symbols;
        this.latestRates = latestRates;
        this.comparingRate = comparingRate;
    }

    @Override
    public String toString() {
        return String.format("Request [id = '%d', date = '%s', comparingDate = '%s', symbols = '%s', latestRates = '%d', comparingRate = '%d']",
                id, date.toString(), comparingDate.toString(), symbols, latestRates, comparingRate);
    }

    protected Request() {
    }
}