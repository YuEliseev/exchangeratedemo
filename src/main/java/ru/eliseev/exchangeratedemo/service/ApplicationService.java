package ru.eliseev.exchangeratedemo.service;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.eliseev.exchangeratedemo.config.ApplicationConfig;
import ru.eliseev.exchangeratedemo.model.GifDTO;
import ru.eliseev.exchangeratedemo.model.RatesDTO;
import ru.eliseev.exchangeratedemo.service.gifservice.GifService;
import ru.eliseev.exchangeratedemo.service.ratesservice.RatesService;

import java.math.BigDecimal;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private GifService gifService;
    @Autowired
    private RatesService ratesService;


    public GifDTO getGifByCurrency() {

        RatesDTO latestRates = ratesService.getLatestRates();
        RatesDTO yesterdayRates = ratesService.getYesterdayRates();

        BigDecimal latestRate = latestRates.getRates().get(applicationConfig.getSymbols());
        BigDecimal yesterdayRate = yesterdayRates.getRates().get(applicationConfig.getSymbols());
        if (ObjectUtils.firstNonNull(latestRate, yesterdayRate) == null){
            throw new IllegalArgumentException("Where some null values from rates service: Latest rate - " + latestRate
                    + " , yesterday rate - " + yesterdayRate);
        }
        String tag;

        switch (latestRate.compareTo(yesterdayRate)){
            case (1):
                tag = applicationConfig.getTagRich();
                break;
            case (0):
                tag = applicationConfig.getTagBalance();
                break;
            case (-1):
                tag = applicationConfig.getTagBroke();
                break;
            default:
                tag = "fail";
        }

        return gifService.getGif(tag);
    }
}
