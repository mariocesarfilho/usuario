package com.mariocesar.usuario.business;

import com.mariocesar.usuario.entity.Usuario;
import com.mariocesar.usuario.entity.UsuarioDTO;
import com.mariocesar.usuario.infraestructure.exception.ConflictException;
import com.mariocesar.usuario.repository.UsuarioRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Builder
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;

//    Buscar Usuário por email
    public Optional<Usuario> buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

//    Criar Usuário
    public Usuario salvarUsuario(Usuario usuario) {
        try {
            emailExistente(usuario.getEmail());
            return usuarioRepository.save(usuario);
        } catch (ConflictException e) {
            throw new ConflictException("Erro, email já cadastrado!" + e.getCause());
        }
    }

//    Deletar Usuário
    public ResponseEntity<Void> deletarUsuarioPorEmail(String email) {
        return ResponseEntity.ok().build();
    }

    public void emailExistente(String email) {
        try {
            if (verificaEmailExistente(email)) {
                throw new ConflictException("Erro, já existe um usuário com esse email!");
            }
        } catch (ConflictException e) {
            throw new ConflictException("Erro, email já cadastrado!" + e.getCause());
        }
    }

//    Valida se esse email existe no banco de dados
    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsEmail(email);
    }
}