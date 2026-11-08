package ru.eliseev.exchangeratedemo.controller;

import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import ru.eliseev.exchangeratedemo.model.DTO.records.CurrencyRequest;

import java.util.Map;

@Controller
@RestController
public class GreetingController {

    private final RabbitTemplate rabbitTemplate;

    public GreetingController(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }


    @GetMapping("/")
    public ModelAndView greeting(){
        ModelAndView modelAndView = new ModelAndView();
        Map<String, Object> model = modelAndView.getModel();

        CurrencyRequest request = new CurrencyRequest("RUB", "2023-10-10");

        MessagePostProcessor messagePostProcessor = message -> {
            message.getMessageProperties().setContentType("application/json");

            message.getMessageProperties().getHeaders().remove("__TypeId__");

            return message;
        };

        System.out.println("=== SPRING ОТПРАВИЛ СООБЩЕНИЕ В test.incoming ===");

        // Отправляем ПРЯМО в очередь. В RabbitMQ дефолтный exchange ("") маршрутизирует
        // сообщение в очередь, имя которой совпадает с routing key.
        rabbitTemplate.convertAndSend("", "test.incoming", request, messagePostProcessor);

        System.out.println( "Сообщение отправлено напрямую в очередь test.incoming");

        modelAndView.setViewName("greeting");
        return modelAndView;
    }
}
