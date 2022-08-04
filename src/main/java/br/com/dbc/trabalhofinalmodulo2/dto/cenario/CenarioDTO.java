package br.com.dbc.trabalhofinalmodulo2.dto.cenario;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CenarioDTO extends CenarioCreateDTO {

    @Schema(description = "ID - Identificador único do cenário da batalha")
    @NotNull(message = "Id cenário não pode ser nulo")
    private int idCenario;
}
