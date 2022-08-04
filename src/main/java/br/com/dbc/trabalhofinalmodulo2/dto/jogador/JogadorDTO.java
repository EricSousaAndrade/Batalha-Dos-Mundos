package br.com.dbc.trabalhofinalmodulo2.dto.jogador;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class JogadorDTO {

    @Schema(description = "id do jogador")
    @NotNull
    private Integer idJogador;

    @NotBlank(message = "O nome do jogador é obrigatório")
    @Schema(description = "Nome do jogador")
    private String nomeJogador;

    @Email(message = "Email não e valido")
    @NotBlank(message = "Email do jogador é obrigatório")
    @Schema(description = "Email do jogador")
    private String email;
}
