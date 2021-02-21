CREATE TABLE `sessao_votacao` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `id_pauta` bigint(20) NOT NULL,
  `data_hora_inicio` datetime(6) NOT NULL,
  `data_hora_fim` datetime(6) NOT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_sessao_votacao_pauta` FOREIGN KEY (`id_pauta`) REFERENCES `pauta` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;