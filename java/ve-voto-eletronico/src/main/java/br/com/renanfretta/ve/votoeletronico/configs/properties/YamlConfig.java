package br.com.renanfretta.ve.votoeletronico.configs.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "votoeletronico")
public class YamlConfig {

	private String userinfoapiurl = null;
	
	private Integer quantidademinutosessaovotacaopadrao = null;

}
