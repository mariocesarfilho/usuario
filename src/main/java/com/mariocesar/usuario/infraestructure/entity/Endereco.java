package com.mariocesar.usuario.infraestructure.entity;

import jakarta.persistence.*;
import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "endereco")
public class Endereco {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String rua;
    private String numero;
    @Column(length = 10)
    private String complemento;
    @Column(length = 150)
    private String cidade;
    @Column(length = 2)
    private String estado;
    @Column(length = 9)
    private String cep;
}
