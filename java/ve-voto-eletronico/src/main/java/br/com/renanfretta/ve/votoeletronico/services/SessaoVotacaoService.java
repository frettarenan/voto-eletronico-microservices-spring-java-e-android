package br.com.renanfretta.ve.votoeletronico.services;

import br.com.renanfretta.ve.votoeletronico.configs.OrikaMapper;
import br.com.renanfretta.ve.votoeletronico.configs.properties.MessagesProperty;
import br.com.renanfretta.ve.votoeletronico.configs.properties.YamlConfig;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;
import br.com.renanfretta.ve.votoeletronico.enums.MessagesPropertyEnum;
import br.com.renanfretta.ve.votoeletronico.repositories.sessaovotacao.SessaoVotacaoRepository;
import br.com.renanfretta.ve.votoeletronico.schedulers.VotingSessionCompletedJob;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang.time.DateUtils;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import java.util.Date;
import java.util.NoSuchElementException;

@Service
@Validated
public class SessaoVotacaoService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoService.class);

    private final Scheduler scheduler;
    private final ObjectMapper objectMapper;
    private final OrikaMapper orikaMapper;
    private final MessagesProperty messagesProperty;
    private final YamlConfig yamlConfig;
    private final SessaoVotacaoRepository repository;

    public SessaoVotacaoService(Scheduler scheduler, ObjectMapper objectMapper, OrikaMapper orikaMapper, MessagesProperty messagesProperty, YamlConfig yamlConfig, SessaoVotacaoRepository repository) {
        this.scheduler = scheduler;
        this.objectMapper = objectMapper;
        this.orikaMapper = orikaMapper;
        this.messagesProperty = messagesProperty;
        this.yamlConfig = yamlConfig;
        this.repository = repository;
    }

    public SessaoVotacaoOutputDTO findById(Long id) {
        SessaoVotacao sessaoVotacao = repository.findById(id).orElseThrow(() -> new NoSuchElementException(messagesProperty.getMessage(MessagesPropertyEnum.ERRO__REGISTRO_NAO_ENCONTRADO_ENTIDADE_SESSAO_VOTACAO)));
        LOGGER.trace("SessaoVotacaoRepository/findById(" + id + ") teve êxito");
        SessaoVotacaoOutputDTO dto = orikaMapper.map(sessaoVotacao, SessaoVotacaoOutputDTO.class);
        return dto;
    }

    public SessaoVotacaoOutputDTO save(SessaoVotacaoInputDTO sessaoVotacaoInputDTO) throws JsonProcessingException {
        SessaoVotacao sessaoVotacao = orikaMapper.map(sessaoVotacaoInputDTO, SessaoVotacao.class);

        if (sessaoVotacaoInputDTO.getMinutosParaVotacao() == null)
            sessaoVotacaoInputDTO.setMinutosParaVotacao(yamlConfig.getQuantidademinutosessaovotacaopadrao());

        Date dataInicioSessao = new Date();
        Date dataFimSessao = DateUtils.addMinutes(dataInicioSessao, sessaoVotacaoInputDTO.getMinutosParaVotacao());

        sessaoVotacao.setDataHoraInicio(dataInicioSessao);
        sessaoVotacao.setDataHoraFim(dataFimSessao);

        sessaoVotacao = repository.save(sessaoVotacao);
        LOGGER.trace("SessaoVotacaoRepository/save(" + objectMapper.writeValueAsString(sessaoVotacao) + ") teve êxito");

        SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = findById(sessaoVotacao.getId());

        votingSessionCompletedSchedule(sessaoVotacaoOutputDTO);

        return sessaoVotacaoOutputDTO;
    }

    private void votingSessionCompletedSchedule(SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO) throws JsonProcessingException {
        JobDataMap jobDataMap = new JobDataMap();
        jobDataMap.put(VotingSessionCompletedJob.ID_SESSAO_VOTACAO_JOB_DATA, sessaoVotacaoOutputDTO.getId());

        String identity = VotingSessionCompletedJob.IDENTITY_PREFIX + sessaoVotacaoOutputDTO.getId();
        String description = VotingSessionCompletedJob.DESCRIPTON + sessaoVotacaoOutputDTO.getId();

        Date startAt = DateUtils.addSeconds(sessaoVotacaoOutputDTO.getDataHoraFim(), 10);

        JobDetail jobDetail = JobBuilder.newJob(VotingSessionCompletedJob.class)
                .withIdentity(identity)
                .withDescription(description)
                .usingJobData(jobDataMap)
                .storeDurably()
                .build();

        Trigger trigger = TriggerBuilder.newTrigger()
                .forJob(jobDetail)
                .withIdentity(identity)
                .withDescription(description)
                .startAt(startAt)
                .withSchedule(SimpleScheduleBuilder.simpleSchedule().withMisfireHandlingInstructionFireNow())
                .build();

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException e) {
            e.printStackTrace();
            LOGGER.error("SessaoVotacaoService/votingSessionCompletedSchedule(" + objectMapper.writeValueAsString(sessaoVotacaoOutputDTO) + ") ERRO AO AGENDAR");
        }
    }

}
