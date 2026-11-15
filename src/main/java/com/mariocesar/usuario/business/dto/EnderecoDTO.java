package com.mariocesar.usuario.business.dto;

import lombok.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EnderecoDTO {

    private String rua;
    private String numero;
    private String cidade;
    private String complemento;
    private String cep;
    private String estado;
}
