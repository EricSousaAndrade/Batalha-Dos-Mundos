package br.com.dbc.trabalhofinalmodulo2.dto.jogador;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRetornaDTO extends LoginCreateDTO{

    @Schema(name = "id do jogador")
    private Integer idJogador;

}
