package br.com.dbc.trabalhofinalmodulo2.dto.jogador;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginCreateDTO {

    @NotNull(message = "Nome jogador nao pode ser null")
    private String nomeJogador;

    private String email;

    private String senha;
}
