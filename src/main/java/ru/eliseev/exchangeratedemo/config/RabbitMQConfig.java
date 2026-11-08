package ru.eliseev.exchangeratedemo.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class RabbitMQConfig {

    @Bean
    public MessageConverter jsonMessageConverter() {
        Jackson2JsonMessageConverter converter = new Jackson2JsonMessageConverter();


        DefaultJackson2JavaTypeMapper typeMapper = new DefaultJackson2JavaTypeMapper();
        typeMapper.setTrustedPackages("*"); // Разрешаем любые пакеты (для теста)

        typeMapper.setTypePrecedence(Jackson2JavaTypeMapper.TypePrecedence.INFERRED);

        converter.setJavaTypeMapper(typeMapper);
        return converter;
    }

    @Bean
    public Queue replyQueue() {
        return new Queue("currency.replies.queue", true);
    }

    @Bean
    public DirectExchange currencyExchange() {
        return new DirectExchange("currency.exchange");
    }

    @Bean
    public Binding replyBinding(Queue replyQueue, DirectExchange currencyExchange) {
        return BindingBuilder.bind(replyQueue).to(currencyExchange)
                .with("currency.reply");
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setMessageConverter(jsonMessageConverter());
        template.setReplyAddress("currency.replies.queue");
        template.setReplyTimeout(10000);
        return template;
    }

    @Bean
    public SimpleMessageListenerContainer replyContainer(ConnectionFactory connectionFactory, RabbitTemplate rabbitTemplate) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames("currency.replies.queue");
        container.setMessageListener(rabbitTemplate);
        // Добавь эти строки:
        container.setMissingQueuesFatal(false); // Не падать, если очереди еще нет
        container.setRecoveryInterval(5000L);   // Пробовать переподключиться каждые 5 секунд
        return container;
    }
}