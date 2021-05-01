package br.com.renanfretta.ve.votoeletronico.resources;

import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.votoeletronico.dtos.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.services.SessaoVotacaoService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/sessao-votacao")
public class SessaoVotacaoResource {

    private static final Logger LOGGER = LoggerFactory.getLogger(SessaoVotacaoResource.class);

    private final ObjectMapper objectMapper;
    private final SessaoVotacaoService service;

    public SessaoVotacaoResource(ObjectMapper objectMapper, SessaoVotacaoService service) {
        this.objectMapper = objectMapper;
        this.service = service;
    }

    @Operation(summary = "salvarEIniciar", description = "Cadastra uma nova sessão e inicia votação")
    @ApiResponses(value = { //
            @ApiResponse(responseCode = "201", description = "Recurso criado", content = @Content(schema = @Schema(implementation = SessaoVotacaoOutputDTO.class))), //
            @ApiResponse(responseCode = "401", description = "Não autorizado"), //
            @ApiResponse(responseCode = "403", description = "Não possui permissão para acessar o recurso"), //
            @ApiResponse(responseCode = "404", description = "Não encontrado") //
    })
    @PostMapping
    public ResponseEntity<SessaoVotacaoOutputDTO> salvarEIniciar(@Valid @RequestBody SessaoVotacaoInputDTO sessaoVotacaoInputDTO) throws JsonProcessingException {
        LOGGER.trace("SessaoVotacaoResource/salvarEIniciar executado com o seguinte parâmetro entrada: sessaoVotacaoInputDTO: " + objectMapper.writeValueAsString(sessaoVotacaoInputDTO));
        // FIXME: Implementar: validação criar uma mesma sessão para a mesma pauta no mesmo horário de vigência
        SessaoVotacaoOutputDTO sessaoVotacaoOutputDTO = service.save(sessaoVotacaoInputDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(sessaoVotacaoOutputDTO);
    }

}
