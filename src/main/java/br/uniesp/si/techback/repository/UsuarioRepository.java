package br.uniesp.si.techback.repository;

import br.uniesp.si.techback.model.Usuario; // Importa o modelo correto de Usuário
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Procura no banco um USUÁRIO pelo e-mail dele
    Optional<Usuario> findByEmail(String email);
}