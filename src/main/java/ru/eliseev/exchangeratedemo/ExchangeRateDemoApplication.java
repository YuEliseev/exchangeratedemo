package ru.eliseev.exchangeratedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import ru.eliseev.exchangeratedemo.config.ApplicationConfig;
import ru.eliseev.exchangeratedemo.service.RabbitCurrencyService;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(ApplicationConfig.class)
public class ExchangeRateDemoApplication {

    @Autowired
    private ApplicationConfig applicationConfig;

    public static void main(String[] args) {
        SpringApplication.run(ExchangeRateDemoApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner testRabbit(RabbitCurrencyService rabbitCurrencyService) {
//        return args -> {
//            System.out.println("=== ОТПРАВЛЯЕМ ЗАПРОС В QUARKUS ===");
//            try {
//                var response = rabbitCurrencyService.getRateFromQuarkus("EUR");
//                System.out.println("=== ПОЛУЧЕН ОТВЕТ ОТ QUARKUS ===");
//                System.out.println("Курс: " + response.rate());
//                System.out.println("Дата: " + response.date());
//            } catch (Exception e) {
//                System.out.println("=== ОШИБКА: " + e.getMessage() + " ===");
//            }
//        };
//    }
}
