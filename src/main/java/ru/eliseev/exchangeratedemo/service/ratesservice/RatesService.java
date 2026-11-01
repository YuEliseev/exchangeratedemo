package ru.eliseev.exchangeratedemo.service.ratesservice;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import ru.eliseev.exchangeratedemo.clients.RatesClient;
import ru.eliseev.exchangeratedemo.config.ApplicationConfig;
import ru.eliseev.exchangeratedemo.model.DTO.RatesDTO;
import ru.eliseev.exchangeratedemo.model.DTO.records.CurrencyResponse;
import ru.eliseev.exchangeratedemo.service.RabbitCurrencyService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Service
public class RatesService {
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private RatesClient ratesClient;

    private final RabbitCurrencyService rabbitCurrencyService;

    public RatesService(RabbitCurrencyService rabbitCurrencyService) {
        this.rabbitCurrencyService = rabbitCurrencyService;
    }

    public RatesDTO getLatestRates() {

        ResponseEntity<RatesDTO> latestRatesResponse = ratesClient.getLatestRates(applicationConfig.getAppId(),
                applicationConfig.getSymbols());
        if (HttpStatus.OK.equals(latestRatesResponse.getStatusCode())) {
            return latestRatesResponse.getBody();
        }
        throw new IllegalArgumentException("Status code was not 200 OK" + latestRatesResponse.getStatusCodeValue());
    }

    public RatesDTO getYesterdayRates() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        today = today.minusDays(applicationConfig.getComparisonDate());
        String date = formatter.format(today);

        ResponseEntity<RatesDTO> yesterdayRatesResponse = ratesClient.getYesterdayRates(date,
                applicationConfig.getAppId(),
                applicationConfig.getSymbols());

        if (HttpStatus.OK.equals(yesterdayRatesResponse.getStatusCode())) {
            return yesterdayRatesResponse.getBody();
        }
        throw new IllegalArgumentException("Status code was not 200 OK" + yesterdayRatesResponse.getStatusCodeValue());
    }

    public BigDecimal getRateViaQuarkus(String currency, String date) {
        CurrencyResponse response = rabbitCurrencyService.getRateFromQuarkus(currency, date);
        if (response == null || response.rate() == null) {
            throw new RuntimeException("Не удалось получить курс для " + currency);
        }
        return BigDecimal.valueOf(response.rate());
    }
}
