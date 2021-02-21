package br.com.renanfretta.ve.votoeletronico;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Date;
import java.util.Optional;

import org.apache.commons.lang.time.DateUtils;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;
import br.com.renanfretta.ve.votoeletronico.repositories.sessaovotacao.SessaoVotacaoRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Sessão votação endpoint tests")
public class SessaoVotacaoEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private SessaoVotacaoRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	private static SessaoVotacao sessaoVotacao01;

	@BeforeAll
	private static void beforeAll() {
		Date dataHoraInicio = new Date();
		Date dataHoraFim = DateUtils.addMinutes(dataHoraInicio, 10);

		sessaoVotacao01 = SessaoVotacao.builder() //
				.id(1L) //
				.pauta(Pauta.builder().id(1L).build()) //
				.dataHoraInicio(dataHoraInicio) //
				.dataHoraFim(dataHoraFim) //
				.build();
	}

	@Nested
	@DisplayName("Method: POST Path: /sessao-votacao")
	class salvar {

		@Test
		@DisplayName("Salvo com sucesso")
		public void salvarSucesso() throws Exception {

			SessaoVotacaoInputDTO sessaoVotacaoInput = SessaoVotacaoInputDTO.builder() //
					.pauta(PautaOutputDTO.builder().id(1L).build()) //
					.minutosParaVotacao(10) //
					.build();

			SessaoVotacao sessaoVotacao = SessaoVotacao.builder() //
					.pauta(Pauta.builder().id(1L).build()) //
					.dataHoraInicio(sessaoVotacao01.getDataHoraInicio()) //
					.dataHoraFim(sessaoVotacao01.getDataHoraFim()) //
					.build();

			BDDMockito.when(repository.save(sessaoVotacao)).thenReturn(sessaoVotacao01);
			BDDMockito.when(repository.findById(1L)).thenReturn(Optional.of(sessaoVotacao01));

			mockMvc.perform(post("/sessao-votacao") //
					.contentType(MediaType.APPLICATION_JSON) //
					.content(objectMapper.writeValueAsString(sessaoVotacaoInput))) //
					.andExpect(status().isCreated()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(jsonPath("$.id").value(1)) //
					.andExpect(jsonPath("$.pauta.id").value(1)); //
		}

	}

}
