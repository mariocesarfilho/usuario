package com.mariocesar.usuario.infraestructure.repository;

import com.mariocesar.usuario.business.dto.UsuarioDTO;
import com.mariocesar.usuario.infraestructure.entity.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
    @Transactional
    void deleteUserByEmail(String email);
}
