package br.com.renanfretta.ve.votoeletronico.schedulers;

import br.com.renanfretta.ve.votoeletronico.producers.VotingSessionCompletedProducer;
import lombok.SneakyThrows;
import org.quartz.Job;
import org.quartz.JobDataMap;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class VotingSessionCompletedJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(VotingSessionCompletedJob.class);

    public static final String IDENTITY_PREFIX = "Sessão de votação ID: ";

    public static final String DESCRIPTON = "Sessão de votação finalizada ID: ";

    public static final String ID_SESSAO_VOTACAO_JOB_DATA = "idSessaoVotacao";

    private final VotingSessionCompletedProducer votingSessionCompletedProducer;

    public VotingSessionCompletedJob(VotingSessionCompletedProducer votingSessionCompletedProducer) {
        this.votingSessionCompletedProducer = votingSessionCompletedProducer;
    }

    @SneakyThrows
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap jobDataMap = jobExecutionContext.getMergedJobDataMap();
        Long idSessaoVotacao =  jobDataMap.getLong(ID_SESSAO_VOTACAO_JOB_DATA);
        votingSessionCompletedProducer.sendMessage(idSessaoVotacao);
    }

}
