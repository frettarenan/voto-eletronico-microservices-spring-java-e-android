CREATE TABLE `voto` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_pauta` bigint(20) NOT NULL,
  `id_sessao_votacao` bigint(20) NOT NULL,
  `id_usuario` bigint(20) NOT NULL,
  `data_hora_votacao` datetime(6) NOT NULL,
  `voto` bit(1) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_voto_pauta` (`id_pauta`),
  KEY `fk_voto_usuario` (`id_usuario`),
  CONSTRAINT `uc_voto_pauta_usuario` UNIQUE (id_pauta , id_usuario),
  CONSTRAINT `fk_voto_pauta` FOREIGN KEY (`id_pauta`) REFERENCES `pauta` (`id`),
  CONSTRAINT `fk_voto_sessao_votacao` FOREIGN KEY (`id_sessao_votacao`) REFERENCES `sessao_votacao` (`id`),
  CONSTRAINT `fk_voto_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;