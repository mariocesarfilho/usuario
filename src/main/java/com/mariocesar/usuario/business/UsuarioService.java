package com.mariocesar.usuario.business;

import com.mariocesar.usuario.business.converter.UsuarioConverter;
import com.mariocesar.usuario.business.dto.EnderecoDTO;
import com.mariocesar.usuario.business.dto.TelefoneDTO;
import com.mariocesar.usuario.business.dto.UsuarioDTO;
import com.mariocesar.usuario.infraestructure.entity.Endereco;
import com.mariocesar.usuario.infraestructure.entity.Telefone;
import com.mariocesar.usuario.infraestructure.entity.Usuario;
import com.mariocesar.usuario.infraestructure.exception.ConflictException;
import com.mariocesar.usuario.infraestructure.exception.ResourceNotFoundException;
import com.mariocesar.usuario.infraestructure.repository.EnderecoRepository;
import com.mariocesar.usuario.infraestructure.repository.TelefoneRepository;
import com.mariocesar.usuario.infraestructure.repository.UsuarioRepository;
import com.mariocesar.usuario.infraestructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

//    Primeiro precisa converter para Usuário normal, salvar,
//    Depois converter novamente para DTO para devolver para o usuário final.
    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        try {
            emailExistente(usuarioDTO.getEmail());
            usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
            Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
            return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado: " + e.getCause());
        }
    }

    public void emailExistente(String email) {
        try {
            if (verificaEmailExistente(email)) {
                throw new ConflictException("Erro, email já cadastrado: " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Erro, este email já está cadastrado!" + e.getCause());
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public Usuario buscaPorEmail(String email) {
        try {
            if (!verificaEmailExistente(email)) {
                throw new ResourceNotFoundException("Erro, email não cadastrado!" + email);
            }
            return usuarioRepository.findByEmail(email)
                    .orElseThrow(() -> new ResourceNotFoundException("Usuário não encontrado!"));
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException("E-mail não encontrado: " + e.getCause());
        }
    }

    public void deletaUsuarioPorEmail(String email) {
            if (!verificaEmailExistente(email)) {
                throw new ResourceNotFoundException("Erro, email não encontrado! " + email);
            }
            usuarioRepository.deleteByEmail(email);
    }

    public List<Usuario> buscarTodosUsuarios() {
        return usuarioRepository.findAll();
    }

    public UsuarioDTO updateUsuario(String token, UsuarioDTO usuarioDTO) {
//      Pegar o email do token
        String email = jwtUtil.extractUsername(token.substring(7));

//       Depois verificamos se o e-mail existe.
        Usuario usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(() ->
                new ResourceNotFoundException("Erro, email não encontrado: " + email));

        usuarioEntity.setSenha(usuarioEntity.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

//       Agora estamos pegando as informações que o usuário passou para a api,
//       se o usuário não passou uma informação em específico, a api pega as informações
//       do banco de dados, para não salvar null. Pois estamos utilizando o método Put
        Usuario usuario = usuarioConverter.updateUser(usuarioDTO, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public EnderecoDTO updateEndereco(Long id, EnderecoDTO enderecoDTO) {
        Endereco enderecoEntity = enderecoRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Erro, endereço não encontrado! " + id));
       Endereco enderecoConvertido = usuarioConverter.updateEndereco(enderecoDTO, enderecoEntity);

        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(enderecoConvertido));
    }

    public TelefoneDTO updateTelefone(Long id, TelefoneDTO telefoneDTO) {
        Telefone telefoneEntity = telefoneRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Erro, telefone não encontrado: " + id));

        Telefone telefoneConvertido = usuarioConverter.updateTelefone(telefoneDTO, telefoneEntity);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefoneConvertido));

    }
}
