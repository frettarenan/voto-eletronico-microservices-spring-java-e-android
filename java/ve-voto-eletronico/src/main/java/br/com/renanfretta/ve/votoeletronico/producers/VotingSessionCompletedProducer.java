package br.com.renanfretta.ve.votoeletronico.producers;

import br.com.renanfretta.ve.votoeletronico.configs.RabbitMqConfig;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.services.SessaoVotacaoService;
import br.com.renanfretta.ve.votoeletronico.services.VotoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class VotingSessionCompletedProducer {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotingSessionCompletedProducer.class);

    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper;
    private final VotoService votoService;

    public VotingSessionCompletedProducer(RabbitTemplate rabbitTemplate, ObjectMapper objectMapper, VotoService votoService) {
        this.rabbitTemplate = rabbitTemplate;
        this.objectMapper = objectMapper;
        this.votoService = votoService;
    }

    public void sendMessage(Long idSessaoVotacao) throws JsonProcessingException {
        List<RelatorioVotosContabilizadosPorSessaoVotacaoOutputDTO> votosContabilizados = votoService.contabilizaVotosPorSessaoVotacao(idSessaoVotacao);

        MessageProperties props = MessagePropertiesBuilder.newInstance().setContentType(MessageProperties.CONTENT_TYPE_JSON).build();
        Message message = MessageBuilder.withBody(objectMapper.writeValueAsBytes(votosContabilizados)).andProperties(props).build();

        rabbitTemplate.setExchange(RabbitMqConfig.SESSAO_VOTACAO_EXCHANGE);
        rabbitTemplate.setRoutingKey(RabbitMqConfig.SESSAO_VOTACAO_FINALIZADA_ROUTING_KEY);
        rabbitTemplate.convertAndSend(message);

        LOGGER.info("Apuração da sessão de votação adicionada na fila com sucesso: " + objectMapper.writeValueAsString(votosContabilizados));
    }

}
