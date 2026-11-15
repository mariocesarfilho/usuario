package com.mariocesar.usuario.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(length = 100)
    private String nome;
    @Column(length = 100)
    private String email;
    private String senha;
    private List<Endereco> enderecos;
    private List<Telefone> telefones;
}
