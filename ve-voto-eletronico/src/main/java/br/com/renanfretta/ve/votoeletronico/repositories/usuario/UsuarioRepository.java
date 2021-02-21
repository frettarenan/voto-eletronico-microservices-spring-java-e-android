package br.com.renanfretta.ve.votoeletronico.repositories.usuario;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import br.com.renanfretta.ve.votoeletronico.entities.Usuario;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long>, JpaSpecificationExecutor<Usuario>, QuerydslPredicateExecutor<Usuario> {

	List<Usuario> findByCpfAndSenha(String cpf, String senha);

}