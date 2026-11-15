package com.mariocesar.usuario.business;

import com.mariocesar.usuario.entity.Usuario;
import com.mariocesar.usuario.infraestructure.exception.ConflictException;
import com.mariocesar.usuario.infraestructure.exception.ResourceNotFoundException;
import com.mariocesar.usuario.repository.UsuarioRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

//    Buscar Usuário por email
    public Usuario buscarUsuarioPorEmail(String email) {

        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Erro, Usuário não encontrado!" + email));
    }

//    Criar Usuário
    public Usuario salvarUsuario(Usuario usuario) {
        try {
            emailExistente(usuario.getEmail());
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
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