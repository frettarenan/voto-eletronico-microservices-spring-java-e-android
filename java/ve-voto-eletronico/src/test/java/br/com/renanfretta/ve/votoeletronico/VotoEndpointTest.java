package br.com.renanfretta.ve.votoeletronico;

import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.commons.integracoes.userinfo.users.UserInfoApiUsersFindByCpfOutput;
import br.com.renanfretta.ve.votoeletronico.configs.properties.YamlConfig;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.voto.VotoInputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;
import br.com.renanfretta.ve.votoeletronico.entities.Usuario;
import br.com.renanfretta.ve.votoeletronico.entities.Voto;
import br.com.renanfretta.ve.votoeletronico.repositories.sessaovotacao.SessaoVotacaoRepository;
import br.com.renanfretta.ve.votoeletronico.repositories.usuario.UsuarioRepository;
import br.com.renanfretta.ve.votoeletronico.repositories.voto.VotoRepository;
import br.com.renanfretta.ve.votoeletronico.services.VotoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.apache.commons.lang.time.DateUtils;
import org.junit.jupiter.api.*;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@DisplayName("Voto endpoint tests")
public class VotoEndpointTest {

	private MockMvc mockMvc;
	private ObjectMapper objectMapper;
	private YamlConfig yamlConfig;

	@MockBean
	private VotoRepository votoRepository;

	@MockBean
	private SessaoVotacaoRepository sessaoVotacaoRepository;

	@MockBean
	private UsuarioRepository usuarioRepository;

	private static DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	private static MockWebServer mockWebServer = new MockWebServer();

	private static Usuario usuario01;

	private static Pauta pauta01;

	private static SessaoVotacao sessaoVotacao01;

	private static Voto voto01;

	private static Date dataHoraInicioSessaoVotacaoVoto01;
	private static Date dataHoraFimSessaoVotacaoVoto01;
	private static Date dataHoraVotacaoVoto01;

	@Autowired
	public VotoEndpointTest(MockMvc mockMvc, ObjectMapper objectMapper, YamlConfig yamlConfig) {
		this.mockMvc = mockMvc;
		this.objectMapper = objectMapper;
		this.yamlConfig = yamlConfig;
	}

	@BeforeAll
	private static void beforeAll() throws IOException {
		mockWebServer.start();

		dataHoraInicioSessaoVotacaoVoto01 = new Date();
		dataHoraFimSessaoVotacaoVoto01 = DateUtils.addMinutes(dataHoraInicioSessaoVotacaoVoto01, 1);
		dataHoraVotacaoVoto01 = new Date();

		usuario01 = Usuario.builder() //
				.id(1L) //
				.cpf("23248538090") //
				.build();

		pauta01 = Pauta.builder() //
				.id(1L) //
				.build();

		sessaoVotacao01 = SessaoVotacao.builder() //
				.id(1L) //
				.pauta(pauta01) //
				.dataHoraInicio(dataHoraInicioSessaoVotacaoVoto01) //
				.dataHoraFim(dataHoraFimSessaoVotacaoVoto01) //
				.build();

		voto01 = Voto.builder() //
				.id(1L) //
				.pauta(pauta01) //
				.sessaoVotacao(sessaoVotacao01) //
				.usuario(usuario01) //
				.dataHoraVotacao(dataHoraVotacaoVoto01) //
				.voto(true) //
				.build();
	}

	@AfterAll
	private static void afterAll() throws IOException {
		mockWebServer.shutdown();
	}

	@Nested
	@DisplayName("Method: POST Path: /voto")
	class salvar {

		@Test
		@DisplayName("Salvo com sucesso")
		public void salvarSucesso() throws Exception {
			Date dataHoraVotacao = new Date();

			Voto voto = Voto.builder() //
					.pauta(Pauta.builder().id(1L).build()) //
					.sessaoVotacao(SessaoVotacao.builder().id(1L).build()) //
					.usuario(Usuario.builder().id(1L).cpf("23248538090").build()) //
					.dataHoraVotacao(dataHoraVotacao) //
					.voto(true) //
					.build();

			UserInfoApiUsersFindByCpfOutput userInfoApiUsersFindByCpfOutput = UserInfoApiUsersFindByCpfOutput.builder().status(VotoService.STATUS_ABLE_TO_VOTE).build();

			BDDMockito.when(votoRepository.save(voto)).thenReturn(voto01);
			BDDMockito.when(votoRepository.findById(1L)).thenReturn(Optional.of(voto01));
			BDDMockito.when(sessaoVotacaoRepository.findById(1L)).thenReturn(Optional.of(sessaoVotacao01));
			BDDMockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario01));

			yamlConfig.setUserinfoapiurl(String.format("http://localhost:%s", mockWebServer.getPort()));
			mockWebServer.enqueue(new MockResponse() //
					.setBody(objectMapper.writeValueAsString(userInfoApiUsersFindByCpfOutput)) //
					.addHeader("Content-Type", "application/json"));

			mockMvc.perform(post("/voto") //
					.contentType(MediaType.APPLICATION_JSON) //
					.content(objectMapper.writeValueAsString(voto))) //
					.andExpect(status().isCreated()) //
					.andExpect(content().contentType(MediaType.APPLICATION_JSON)) //
					.andExpect(jsonPath("$.id").value(1)) //
					.andExpect(jsonPath("$.pauta.id").value(1)) //
					.andExpect(jsonPath("$.usuario.id").value(1)) //
					.andExpect(jsonPath("$.dataHoraVotacao").value(dateFormat.format(voto01.getDataHoraVotacao()))) //
					.andExpect(jsonPath("$.voto").value(true)); //
		}

		@Test
		@DisplayName("CPF não autorizado")
		public void salvarSucessoCpfNaoAutorizado() throws Exception {
			Date dataHoraVotacao = new Date();

			Voto voto = Voto.builder() //
					.pauta(Pauta.builder().id(1L).build()) //
					.sessaoVotacao(SessaoVotacao.builder().id(1L).build()) //
					.usuario(Usuario.builder().id(1L).cpf("23248538090").build()) //
					.dataHoraVotacao(dataHoraVotacao) //
					.voto(true) //
					.build();

			UserInfoApiUsersFindByCpfOutput userInfoApiUsersFindByCpfOutput = UserInfoApiUsersFindByCpfOutput.builder().status(VotoService.STATUS_UNABLE_TO_VOTE).build();

			BDDMockito.when(votoRepository.save(voto)).thenReturn(voto01);
			BDDMockito.when(votoRepository.findById(1L)).thenReturn(Optional.of(voto01));
			BDDMockito.when(sessaoVotacaoRepository.findById(1L)).thenReturn(Optional.of(sessaoVotacao01));
			BDDMockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario01));

			yamlConfig.setUserinfoapiurl(String.format("http://localhost:%s", mockWebServer.getPort()));
			mockWebServer.enqueue(new MockResponse() //
					.setBody(objectMapper.writeValueAsString(userInfoApiUsersFindByCpfOutput)) //
					.addHeader("Content-Type", "application/json"));

			mockMvc.perform(post("/voto") //
					.contentType(MediaType.APPLICATION_JSON) //
					.content(objectMapper.writeValueAsString(voto))) //
					.andExpect(status().isUnprocessableEntity()); //
		}

		@Test
		@DisplayName("CPF não encontrado")
		public void salvarCpfNaoEncontrado() throws Exception {

			Date dataHoraVotacao = new Date();

			Voto voto = Voto.builder() //
					.pauta(Pauta.builder().id(1L).build()) //
					.sessaoVotacao(SessaoVotacao.builder().id(1L).build()) //
					.usuario(Usuario.builder().id(1L).cpf("17358218027").build()) //
					.dataHoraVotacao(dataHoraVotacao) //
					.voto(true) //
					.build();

			BDDMockito.when(votoRepository.save(voto)).thenReturn(voto01);
			BDDMockito.when(votoRepository.findById(1L)).thenReturn(Optional.of(voto01));
			BDDMockito.when(sessaoVotacaoRepository.findById(1L)).thenReturn(Optional.of(sessaoVotacao01));
			BDDMockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario01));

			yamlConfig.setUserinfoapiurl(String.format("http://localhost:%s", mockWebServer.getPort()));
			mockWebServer.enqueue(new MockResponse() //
					.setStatus("HTTP/1.1 404"));

			mockMvc.perform(post("/voto") //
					.contentType(MediaType.APPLICATION_JSON) //
					.content(objectMapper.writeValueAsString(voto))) //
					.andExpect(status().isUnprocessableEntity()); //
		}

		@Test
		@DisplayName("Horário da sessão de votação expirado")
		public void salvarCpfHorarioSessaoVotacaoExpirado() throws Exception {

			Date dataHoraInicioSessaoVotacao = new Date();
			dataHoraInicioSessaoVotacao = DateUtils.addDays(dataHoraInicioSessaoVotacao, -1);

			Date dataHoraFimSessaoVotacao = new Date();
			dataHoraFimSessaoVotacao = DateUtils.addDays(dataHoraFimSessaoVotacao, -1);
			dataHoraFimSessaoVotacao = DateUtils.addMinutes(dataHoraFimSessaoVotacao, 1);

			VotoInputDTO votoInputDTO = VotoInputDTO.builder() //
					.sessaoVotacao(SessaoVotacaoOutputDTO.builder() //
							.id(1L) //
							.dataHoraInicio(dataHoraInicioSessaoVotacao) //
							.dataHoraFim(dataHoraFimSessaoVotacao) //
							.build()) //
					.usuario(UsuarioDTO.builder().id(1L).cpf("23248538090").build()) //
					.voto(true) //
					.build();

			SessaoVotacao sessaoVotacao = SessaoVotacao.builder() //
					.id(1L) //
					.pauta(pauta01) //
					.dataHoraInicio(dataHoraInicioSessaoVotacao) //
					.dataHoraFim(dataHoraFimSessaoVotacao) //
					.build();

			UserInfoApiUsersFindByCpfOutput userInfoApiUsersFindByCpfOutput = UserInfoApiUsersFindByCpfOutput.builder().status(VotoService.STATUS_ABLE_TO_VOTE).build();

			BDDMockito.when(votoRepository.save(voto01)).thenReturn(voto01);
			BDDMockito.when(votoRepository.findById(1L)).thenReturn(Optional.of(voto01));
			BDDMockito.when(sessaoVotacaoRepository.findById(1L)).thenReturn(Optional.of(sessaoVotacao));
			BDDMockito.when(usuarioRepository.findById(1L)).thenReturn(Optional.of(usuario01));

			yamlConfig.setUserinfoapiurl(String.format("http://localhost:%s", mockWebServer.getPort()));
			mockWebServer.enqueue(new MockResponse() //
					.setBody(objectMapper.writeValueAsString(userInfoApiUsersFindByCpfOutput)) //
					.addHeader("Content-Type", "application/json"));

			mockMvc.perform(post("/voto") //
					.contentType(MediaType.APPLICATION_JSON) //
					.content(objectMapper.writeValueAsString(votoInputDTO))) //
					.andExpect(status().isUnprocessableEntity()); //
		}

	}

}
