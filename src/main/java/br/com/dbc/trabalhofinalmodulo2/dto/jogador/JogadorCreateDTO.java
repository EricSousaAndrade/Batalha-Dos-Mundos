package br.com.dbc.trabalhofinalmodulo2.dto.jogador;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JogadorCreateDTO {

    @NotBlank(message = "O nome do jogador é obrigatório")
    @Schema(description = "Nome do jogador")
    private String nomeJogador;

    @NotBlank(message = "Senha é obrigatória")
    @Schema(description = "Senha do jogador")
    private String senha;

    @Email(message = "Email não e valido")
    @NotBlank(message = "Email do jogador é obrigatório")
    @Schema(description = "Email do jogador")
    private String email;
}
