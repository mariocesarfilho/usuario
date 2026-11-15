package com.mariocesar.usuario.business;

import com.mariocesar.usuario.business.converter.UsuarioConverter;
import com.mariocesar.usuario.business.dto.UsuarioDTO;
import com.mariocesar.usuario.entity.Usuario;
import com.mariocesar.usuario.infraestructure.exception.ConflictException;
import com.mariocesar.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

//    Primeiro precisa converter para Usuário normal, salvar,
//    Depois converter novamente para DTO para devolver para o usuário final.
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
