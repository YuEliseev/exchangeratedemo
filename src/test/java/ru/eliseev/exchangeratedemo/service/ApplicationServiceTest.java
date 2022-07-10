package ru.eliseev.exchangeratedemo.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;

import ru.eliseev.exchangeratedemo.ExchangeRateDemoApplication;
import ru.eliseev.exchangeratedemo.clients.GifClient;
import ru.eliseev.exchangeratedemo.clients.RatesClient;
import ru.eliseev.exchangeratedemo.config.ApplicationConfig;
import ru.eliseev.exchangeratedemo.model.DTO.GifDTO;
import ru.eliseev.exchangeratedemo.model.DTO.RatesDTO;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.ArgumentMatchers.isNotNull;

@SpringBootTest(classes = ExchangeRateDemoApplication.class)
class ApplicationServiceTest {
    @Autowired
    ApplicationConfig applicationConfig;
    @Autowired
    ApplicationService applicationService;
    @MockBean
    static GifClient gifClient;
    @MockBean
    static RatesClient ratesClient;

    String balance;
    String broke;
    String symbols;
    String rich;

    @BeforeEach
    void setUp() {
        initVariables();
    }

    @Test
    void checkLatestCurrencyIsLessThanYesterday() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("mock", "images={original={height=0, url=-1, width=0},");

        ResponseEntity<Map<Object, Object>> brokeGifResponse = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(gifClient.getGif(isNotNull(), eq(broke))).thenReturn(brokeGifResponse);

        RatesDTO latestRateDTO = new RatesDTO("USD", Map.of(symbols, BigDecimal.ONE));
        ResponseEntity<RatesDTO> latestRateResponse = new ResponseEntity<>(latestRateDTO, HttpStatus.OK);
        Mockito.when(ratesClient.getLatestRates(isNotNull(), isNotNull())).thenReturn(latestRateResponse);

        RatesDTO yesterdayRateDTO = new RatesDTO("USD", Map.of(symbols, BigDecimal.TEN));
        ResponseEntity<RatesDTO> yesterdayRateResponse = new ResponseEntity<>(yesterdayRateDTO, HttpStatus.OK);
        Mockito.when(ratesClient.getYesterdayRates(isNotNull(), isNotNull(), isNotNull())).thenReturn(yesterdayRateResponse);

        GifDTO gifByCurrency = applicationService.getGifByCurrency();

        Assert.isTrue("-1".equals(gifByCurrency.getUrl()), "Latest currency check failed. Expected 0, got " + gifByCurrency.getUrl());
    }

    @Test
    void checkLatestCurrencyIsNotChange() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("mock", "images={original={height=0, url=0, width=0},");

        ResponseEntity<Map<Object, Object>> balanceGifResponse = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(gifClient.getGif(isNotNull(), eq(balance))).thenReturn(balanceGifResponse);

        RatesDTO latestRateDTO = new RatesDTO("USD", Map.of(symbols, BigDecimal.ONE));
        ResponseEntity<RatesDTO> latestRateResponse = new ResponseEntity<>(latestRateDTO, HttpStatus.OK);
        Mockito.when(ratesClient.getLatestRates(isNotNull(), isNotNull())).thenReturn(latestRateResponse);

        RatesDTO yesterdayRateDTO = new RatesDTO("USD", Map.of(symbols, BigDecimal.ONE));
        ResponseEntity<RatesDTO> yesterdayRateResponse = new ResponseEntity<>(yesterdayRateDTO, HttpStatus.OK);
        Mockito.when(ratesClient.getYesterdayRates(isNotNull(), isNotNull(), isNotNull())).thenReturn(yesterdayRateResponse);

        GifDTO gifByCurrency = applicationService.getGifByCurrency();

        Assert.isTrue("0".equals(gifByCurrency.getUrl()), "Latest currency check failed. Expected 0, got " + gifByCurrency.getUrl());
    }

    @Test
    void checkLatestCurrencyIsBiggerThanYesterday() {

        HashMap<Object, Object> map = new HashMap<>();
        map.put("mock", "images={original={height=0, url=1, width=0},");

        ResponseEntity<Map<Object, Object>> richGifResponse = new ResponseEntity<>(map, HttpStatus.OK);
        Mockito.when(gifClient.getGif(isNotNull(), eq(rich))).thenReturn(richGifResponse);

        RatesDTO latestRateDTO = new RatesDTO("USD", Map.of(symbols, BigDecimal.TEN));
        ResponseEntity<RatesDTO> latestRateResponse = new ResponseEntity<>(latestRateDTO, HttpStatus.OK);
        Mockito.when(ratesClient.getLatestRates(isNotNull(), isNotNull())).thenReturn(latestRateResponse);

        RatesDTO yesterdayRateDTO = new RatesDTO("USD", Map.of(symbols, BigDecimal.ONE));
        ResponseEntity<RatesDTO> yesterdayRateResponse = new ResponseEntity<>(yesterdayRateDTO, HttpStatus.OK);
        Mockito.when(ratesClient.getYesterdayRates(isNotNull(), isNotNull(), isNotNull())).thenReturn(yesterdayRateResponse);

        GifDTO gifByCurrency = applicationService.getGifByCurrency();

        Assert.isTrue("1".equals(gifByCurrency.getUrl()), "Latest currency check failed. Expected 1, got " + gifByCurrency.getUrl());
    }

    private void initVariables() {
        balance = applicationConfig.getTagBalance();
        broke = applicationConfig.getTagBroke();
        symbols = applicationConfig.getSymbols();
        rich = applicationConfig.getTagRich();
    }
}