package com.mariocesar.usuario.repository;

import com.mariocesar.usuario.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsEmail(String email);

    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deletarByEmail(String email);
}
