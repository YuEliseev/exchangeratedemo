package ru.eliseev.exchangeratedemo.service;


import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import ru.eliseev.exchangeratedemo.model.DTO.records.CurrencyRequest;
import ru.eliseev.exchangeratedemo.model.DTO.records.CurrencyResponse;

@Service
public class RabbitCurrencyService {

    private final RabbitTemplate rabbitTemplate;

    public RabbitCurrencyService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public CurrencyResponse getRateFromQuarkus(String targetCurrency, String date) {
        CurrencyRequest request = new CurrencyRequest(targetCurrency, date);

        // Отправляем в exchange currency.exchange с ключом currency.request
        // Ждём ответ (Spring сам подставит временную очередь в replyTo)
        CurrencyResponse response = (CurrencyResponse) rabbitTemplate.convertSendAndReceive(
                "currency.exchange",
                "currency.request",
                request
        );

        if (response == null) {
            throw new RuntimeException("Таймаут: Quarkus не ответил вовремя");
        }

        return response;
    }
}