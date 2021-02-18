package br.com.renanfretta.ve.votoeletronico.configs;

import org.springframework.stereotype.Component;

import br.com.renanfretta.ve.commons.configs.OrikaMapperBase;
import br.com.renanfretta.ve.commons.dtos.usuario.UsuarioDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.PautaDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.sessaovotacao.SessaoVotacaoOutputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoInputDTO;
import br.com.renanfretta.ve.commons.dtos.votoeletronico.voto.VotoOutputDTO;
import br.com.renanfretta.ve.votoeletronico.entities.Pauta;
import br.com.renanfretta.ve.votoeletronico.entities.SessaoVotacao;
import br.com.renanfretta.ve.votoeletronico.entities.Usuario;
import br.com.renanfretta.ve.votoeletronico.entities.Voto;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

@Component
public class OrikaMapper extends OrikaMapperBase {

	public OrikaMapper() {
		if (mapperFacade != null)
			return;

		MapperFactory factory = new DefaultMapperFactory.Builder().build();

		factory.classMap(Usuario.class, UsuarioDTO.class) //
				.constructorA().constructorB().mapNulls(true).mapNullsInReverse(true) //
				.byDefault().register();

		factory.classMap(Pauta.class, PautaDTO.class) //
				.constructorA().constructorB().mapNulls(true).mapNullsInReverse(true) //
				.byDefault().register();

		factory.classMap(SessaoVotacao.class, SessaoVotacaoInputDTO.class) //
				.constructorA().constructorB().mapNulls(true).mapNullsInReverse(true) //
				.byDefault().register();

		factory.classMap(SessaoVotacao.class, SessaoVotacaoOutputDTO.class) //
				.constructorA().constructorB().mapNulls(true).mapNullsInReverse(true) //
				.byDefault().register();

		factory.classMap(Voto.class, VotoInputDTO.class) //
				.constructorA().constructorB().mapNulls(true).mapNullsInReverse(true) //
				.byDefault().register();

		factory.classMap(Voto.class, VotoOutputDTO.class) //
				.constructorA().constructorB().mapNulls(true).mapNullsInReverse(true) //
				.byDefault().register();

		mapperFacade = factory.getMapperFacade();
	}

}
