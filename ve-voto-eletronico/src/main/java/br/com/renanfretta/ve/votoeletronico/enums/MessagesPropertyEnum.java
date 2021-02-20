package br.com.renanfretta.ve.votoeletronico.enums;

public enum MessagesPropertyEnum {
	
	ERRO__COMUNICACAO("erro.comunicacao"),
	ERRO__REGISTRO_NAO_EXISTE("erro.registro-nao-existe"),
	ERRO__INTEGRIDADE_BANCO_DADOS("erro.integridade-banco-dados"),
	ERRO__EXISTEM_REGISTROS_DEPENDENTES("erro.existem-registros-dependentes"),
	ERRO__DUPLICIDADE_BANCO_DADOS("erro.duplicidade-banco-dados"),
	
	// UNIQUE CONSTRAINTS
	RN__UNIQUE_CNPJ_CONSTRUTORA("rn.unique_voto_usuario"),
	
	// Regras de negócio
	RN__SESSAO_VOTACAO_ENCERRADA("rn.sessao_votacao_encerrada"),
	RN__USUARIO_NAO_AUTORIZADO_VOTAR("rn.usuario_nao_autorizado_votar"),
	
	// Comunicação com sistemas de terceiros
	ERRO__USER_INFO_SERVICE_CPF_NAO_ENCONTRADO("erro.user_info_service_cpf_nao_encontrado");
	
	private String key;
	
	MessagesPropertyEnum(String key) {
        this.key = key;
    }

	public String getKey() {
		return key;
	}
	
}