package br.com.renanfretta.ve.votoeletronico.configs;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {

    public static final String SESSAO_VOTACAO_FINALIZADA_QUEUE = "sessao_votacao_finalizada";

    public static final String SESSAO_VOTACAO_EXCHANGE = "sessao_votacao";

    public static final String SESSAO_VOTACAO_FINALIZADA_ROUTING_KEY = "sessao_votacao_finalizada";

    @Bean
    public Queue sessaoVotacaoFinalizadaQueue() {
        return new Queue(SESSAO_VOTACAO_FINALIZADA_QUEUE);
    }

    @Bean
    public DirectExchange sessaoVotacaoExchange() {
        return new DirectExchange(SESSAO_VOTACAO_EXCHANGE);
    }

    @Bean
    public Binding sessaoVotacaoExchangeBinding01() {
        return BindingBuilder.bind(sessaoVotacaoFinalizadaQueue()).to(sessaoVotacaoExchange()).with(SESSAO_VOTACAO_FINALIZADA_ROUTING_KEY);
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate template(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());
        return rabbitTemplate;
    }

}
