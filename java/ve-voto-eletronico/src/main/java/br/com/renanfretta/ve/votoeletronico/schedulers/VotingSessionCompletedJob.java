package br.com.renanfretta.ve.votoeletronico.schedulers;

import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.services.SessaoVotacaoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
public class VotingSessionCompletedJob implements Job {

    public static final String IDENTITY_PREFIX = "Sessão de votação ID: ";

    public static final String DESCRIPTON = "Sessão de votação finalizada ID: ";

    public static final String ID_SESSAO_VOTACAO_JOB_DATA = "idSessaoVotacao";

    private final ObjectMapper objectMapper;
    private final SessaoVotacaoService sessaoVotacaoService;

    public VotingSessionCompletedJob(ObjectMapper objectMapper, SessaoVotacaoService sessaoVotacaoService) {
        this.objectMapper = objectMapper;
        this.sessaoVotacaoService = sessaoVotacaoService;
    }

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Long idSessaoVotacao =  jobDataMap.getLong(ID_SESSAO_VOTACAO_JOB_DATA);
        SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = sessaoVotacaoService.findById(idSessaoVotacao);

        System.out.println("Votação concluída com sucesso: " + objectMapper.writeValueAsString(sessaoVotacaoOutputDTO));
    }

}
