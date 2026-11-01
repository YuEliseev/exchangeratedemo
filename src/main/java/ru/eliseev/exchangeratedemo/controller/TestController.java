package ru.eliseev.exchangeratedemo.controller;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.eliseev.exchangeratedemo.model.DTO.records.CurrencyRequest;
// ... другие импорты
@Controller
@RestController
public class TestController {

    private final RabbitTemplate rabbitTemplate;

    public TestController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @GetMapping("/api/test/mq-direct")
    public String sendDirectToQueue() {
        CurrencyRequest request = new CurrencyRequest("RUB", "2023-10-10");
        System.out.println("=== SPRING ОТПРАВИЛ СООБЩЕНИЕ В test.incoming ===");

        // Отправляем ПРЯМО в очередь. В RabbitMQ дефолтный exchange ("") маршрутизирует 
        // сообщение в очередь, имя которой совпадает с routing key.
        rabbitTemplate.convertAndSend("", "test.incoming", request);

        return "Сообщение отправлено напрямую в очередь test.incoming";
    }
}