package br.com.dbc.trabalhofinalmodulo2.dto.batalha;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatalhaCreateDTO {

    @NotNull(message = "O id do cenario não pode ser nulo")
    @Schema(description = "Id do cenario que ocorreu a batalha")
    private int idCenario;

    @NotNull(message = "O id do jogador não pode ser nulo")
    @Schema(description = "Id do jogador que lutou a batalha")
    private int idPersonagem;

    @NotNull(message = "O id do boss não pode ser nulo")
    @Schema(description = "Id do boss que ocorreu a batalha")
    private Integer idBoss;

    @NotNull(message = "O status da batalha não pode ser nulo")
    @NotEmpty(message = "O status da batalha não pode ser vazio")
    @Schema(description = "Resultado da batalha")
    private String status;

    @NotNull(message = "Round da Batalha")
    @Schema(description = "Round atual da batalha")
    private Integer roundBatalha;

}
