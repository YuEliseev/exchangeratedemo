package ru.eliseev.exchangeratedemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.openfeign.EnableFeignClients;

import ru.eliseev.exchangeratedemo.config.ApplicationConfig;

@SpringBootApplication
@EnableFeignClients
@EnableConfigurationProperties(ApplicationConfig.class)
public class ExchangeRateDemoApplication {

	@Autowired
	private ApplicationConfig applicationConfig;

	public static void main(String[] args) {
		SpringApplication.run(ExchangeRateDemoApplication.class, args);
	}

}
