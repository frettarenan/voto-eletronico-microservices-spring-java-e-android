package br.com.renanfretta.ve.votoeletronico.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.renanfretta.ve.votoeletronico.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

	List<Usuario> findByCpfAndSenha(String cpf, String senha);

}
