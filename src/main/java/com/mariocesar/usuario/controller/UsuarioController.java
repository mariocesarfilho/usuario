package com.mariocesar.usuario.controller;

import com.mariocesar.usuario.business.UsuarioService;
import com.mariocesar.usuario.business.dto.EnderecoDTO;
import com.mariocesar.usuario.business.dto.TelefoneDTO;
import com.mariocesar.usuario.business.dto.UsuarioDTO;
import com.mariocesar.usuario.infraestructure.entity.Endereco;
import com.mariocesar.usuario.infraestructure.entity.Usuario;
import com.mariocesar.usuario.infraestructure.security.JwtUtil;
import jakarta.websocket.server.PathParam;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<UsuarioDTO> salvaUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.salvarUsuario(usuarioDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return ResponseEntity.ok("Bearer " + jwtUtil.generateToken(authentication.getName()));
    }

    @DeleteMapping("/{email}")
    public void deletaUsuarioPorEmail(@PathVariable String email) {
        usuarioService.deletaUsuarioPorEmail(email);
    }

    @GetMapping
    public ResponseEntity<UsuarioDTO> buscarPorEmail(@RequestParam("email") String email) {
        return ResponseEntity.ok(usuarioService.buscaPorEmail(email));
    }

    @PutMapping
    public ResponseEntity<UsuarioDTO> atualizarUsuario(@RequestHeader("Authorization") String token, @RequestBody UsuarioDTO usuarioDTO) {
        return ResponseEntity.ok(usuarioService.updateUsuario(token ,usuarioDTO));
    }

    @PutMapping("/endereco")
    public ResponseEntity<EnderecoDTO> atualizarEndereco(@RequestParam("id") Long id, @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(usuarioService.updateEndereco(id, enderecoDTO));
    }

    @PutMapping("/telefone")
    public ResponseEntity<TelefoneDTO> atualizarTelefone(@RequestParam("id") Long id, @RequestBody TelefoneDTO telefoneDTO) {
        return ResponseEntity.ok(usuarioService.updateTelefone(id, telefoneDTO));
    }

    @PostMapping("/endereco")
    public ResponseEntity<EnderecoDTO> cadastraEndereco(@RequestHeader("Authorization") String token, @RequestBody EnderecoDTO enderecoDTO) {
        return ResponseEntity.ok(usuarioService.cadastraEndereco(token, enderecoDTO));
    }

    @PostMapping("/telefone")
    public ResponseEntity<TelefoneDTO> cadastraTelefone(@RequestHeader("Authorization") String token, @RequestBody TelefoneDTO telefoneDTO) {
        return ResponseEntity.ok(usuarioService.cadastraTelefone(token, telefoneDTO));
    }
}
