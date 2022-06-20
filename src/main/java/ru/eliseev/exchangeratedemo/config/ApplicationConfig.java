package ru.eliseev.exchangeratedemo.config;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "gifcurrencyvisualiser")
public class ApplicationConfig {
    private String apiKey;
    private String appId;
    private String tagRich;
    private String tagBroke;
    private String symbols;
    private String tagBalance;
    private int comparisonDate;
}
