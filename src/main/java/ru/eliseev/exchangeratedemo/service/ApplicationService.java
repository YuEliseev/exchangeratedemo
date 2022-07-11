package ru.eliseev.exchangeratedemo.service;

import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.eliseev.exchangeratedemo.accessingdatajpa.RatesRepository;
import ru.eliseev.exchangeratedemo.model.entity.Request;
import ru.eliseev.exchangeratedemo.config.ApplicationConfig;
import ru.eliseev.exchangeratedemo.model.DTO.GifDTO;
import ru.eliseev.exchangeratedemo.model.DTO.RatesDTO;
import ru.eliseev.exchangeratedemo.service.gifservice.GifService;
import ru.eliseev.exchangeratedemo.service.ratesservice.RatesService;

import java.math.BigDecimal;
import java.time.LocalDate;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationConfig applicationConfig;
    @Autowired
    private GifService gifService;
    @Autowired
    private RatesService ratesService;
    @Autowired
    private RatesRepository ratesRepository;

    BigDecimal latestRate;
    BigDecimal yesterdayRate;

    public GifDTO getGifByCurrency() {

        RatesDTO latestRates = ratesService.getLatestRates();
        RatesDTO yesterdayRates = ratesService.getYesterdayRates();

        latestRate = latestRates.getRates().get(applicationConfig.getSymbols());
        yesterdayRate = yesterdayRates.getRates().get(applicationConfig.getSymbols());

        if (ObjectUtils.firstNonNull(latestRate, yesterdayRate) == null){
            throw new IllegalArgumentException("Where some null values from rates service: Latest rate - " + latestRate
                    + " , yesterday rate - " + yesterdayRate);
        }
        String tag;

        switch (yesterdayRate.compareTo(latestRate)){
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

        ratesRepository.save(new Request(LocalDate.now(),
                LocalDate.now().minusDays(applicationConfig.getComparisonDate()),
                applicationConfig.getSymbols(),
                latestRate,
                yesterdayRate));

        return gifService.getGif(tag);
    }
}
