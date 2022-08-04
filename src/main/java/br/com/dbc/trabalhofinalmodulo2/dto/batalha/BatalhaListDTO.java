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
public class BatalhaListDTO {

    @Schema(description = "Id da Batalha")
    @NotNull
    private Integer idBatalha;

    @NotNull(message = "O status da batalha não pode ser nulo")
    @NotEmpty(message = "O status da batalha não pode ser vazio")
    @Schema(description = "Resultado da batalha")
    private String status;

    @NotNull(message = "Round da Batalha")
    @Schema(description = "Round atual da batalha")
    private Integer roundBatalha;
}
