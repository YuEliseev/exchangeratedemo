package ru.eliseev.exchangeratedemo.accessingdatajpa.entity;

import lombok.Getter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
public class Request {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private LocalDate date;
    private LocalDate comparingDate;
    private String symbols;
    private BigDecimal latestRates;
    private BigDecimal comparingRate;

    public Request(LocalDate date, LocalDate comparingDate, String symbols, BigDecimal latestRates, BigDecimal comparingRate){
        this.date = date;
        this.comparingDate = comparingDate;
        this.symbols = symbols;
        this.latestRates = latestRates;
        this.comparingRate = comparingRate;
    }

    @Override
    public String toString(){
        return String.format("Request [id = '%d', date = '%s', comparingDate = '%s', symbols = '%s', latestRates = '%d', comparingRate = '%d']",
                id, date.toString(), comparingDate.toString(), symbols, latestRates, comparingRate);
    }

    protected Request (){
    }
}