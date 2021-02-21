package br.com.renanfretta.ve.votoeletronico;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

import br.com.renanfretta.ve.votoeletronico.dtos.pauta.PautaInputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.repositories.pauta.PautaRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Pauta endpoint tests")
public class PautaEndpointTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PautaRepository repository;

	@Autowired
	private ObjectMapper objectMapper;

	private static Pauta pauta01;

	@BeforeAll
	private static void beforeAll() {
		pauta01 = Pauta.builder() //
				.id(1L) //
				.descricao("Aprova o novo orçamento para a saúde?") //
				.build();
	}

	@Nested
	@DisplayName("Method: GET Path: /pauta")
	class findAll {

		@Test
		@DisplayName("Retornando elementos corretamente")
		public void findAllComResultados() throws Exception {

			List<Pauta> list = new ArrayList<Pauta>();
			list.add(pauta01);

			BDDMockito.when(repository.findAll()).thenReturn(list);

			mockMvc.perform(get("/pauta")) //
					.andExpect(status().isOk()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(jsonPath("$.length()", is(1))) //
					.andExpect(jsonPath("$.[0].id").value(1)) //
					.andExpect(jsonPath("$.[0].descricao").value("Aprova o novo orçamento para a saúde?"));
		}

		@Test
		@DisplayName("Sem resultados")
		public void findAllSemResultado() throws Exception {

			List<Pauta> list = new ArrayList<Pauta>();

			BDDMockito.when(repository.findAll()).thenReturn(list);

			mockMvc.perform(get("/pauta")) //
					.andExpect(status().isNoContent());
		}

	}

	@Nested
	@DisplayName("Method: GET Path: /pauta/{id}")
	class findById {

		@Test
		@DisplayName("Retornando elementos corretamente")
		public void findByIdEncontrado() throws Exception {

			BDDMockito.when(repository.findById(1L)).thenReturn(Optional.of(pauta01));

			mockMvc.perform(get("/pauta/1")) //
					.andExpect(status().isOk()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(jsonPath("$.id").value(1)) //
					.andExpect(jsonPath("$.descricao").value("Aprova o novo orçamento para a saúde?")); //
		}

		@Test
		@DisplayName("Sem resultados")
		public void findByIdNaoEncontrado() throws Exception {

			BDDMockito.when(repository.findById(9999L)).thenReturn(null);

			mockMvc.perform(get("/pauta/1")) //
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("Erro: ID inválido")
		public void findByIdErro() throws Exception {

			mockMvc.perform(get("/pauta/AAAAA")) //
					.andExpect(status().isBadRequest());
		}

	}

	@Nested
	@DisplayName("Method: POST Path: /pauta")
	class salvar {

		@Test
		@DisplayName("Salvo com sucesso")
		public void salvarSucesso() throws Exception {
			
			Pauta pauta = Pauta.builder() //
					.descricao("Aprova o novo orçamento para a saúde?") //
					.build();

			PautaInputDTO pautaInputDTO = PautaInputDTO.builder() //
					.descricao("Aprova o novo orçamento para a saúde?") //
					.build();

			BDDMockito.when(repository.save(pauta)).thenReturn(pauta01);
			BDDMockito.when(repository.findById(1L)).thenReturn(Optional.of(pauta01));

			mockMvc.perform(post("/pauta") //
					.contentType(MediaType.APPLICATION_JSON) //
					.content(objectMapper.writeValueAsString(pautaInputDTO))) //
					.andExpect(status().isCreated()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(jsonPath("$.id").value(1)) //
					.andExpect(jsonPath("$.descricao").value("Aprova o novo orçamento para a saúde?")); //
		}

	}

	@Nested
	@DisplayName("Method: DELETE Path: /pauta")
	class deleteById {

		@Test
		@DisplayName("Deletado com sucesso")
		public void deleteByIdSucesso() throws Exception {

			BDDMockito.when(repository.findById(1L)).thenReturn(Optional.of(pauta01));

			mockMvc.perform(get("/pauta/1")) //
					.andExpect(status().isOk()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(jsonPath("$.id").value(1)) //
					.andExpect(jsonPath("$.descricao").value("Aprova o novo orçamento para a saúde?")); //
		}

		@Test
		@DisplayName("Sem resultados")
		public void deleteByIdNaoEncontrado() throws Exception {

			BDDMockito.when(repository.findById(9999L)).thenReturn(null);

			mockMvc.perform(get("/pauta/1")) //
					.andExpect(status().isNotFound());
		}

		@Test
		@DisplayName("Erro: ID inválido")
		public void deleteByIdErro() throws Exception {

			mockMvc.perform(get("/pauta/AAAAA")) //
					.andExpect(status().isBadRequest());
		}

	}

}
