package br.com.dbc.trabalhofinalmodulo2.dto.cenario;

import br.com.dbc.trabalhofinalmodulo2.entities.TipoReino;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Data
public class CenarioCreateDTO {

    @Schema(description = "Nome do cenário")
    @NotEmpty(message = "Nome do cenário não pode estar vazio")
    @NotBlank(message = "Nome do cenário não pode estar em branco")
    @NotNull(message = "Nome do cenário não pode ser nulo")
    @Size(max = 200, message = "Nome do cenário deve conter no máximo 200 caracteres")
    private String nomeCenario;

    @Schema(description = "Tipo de cenário")
    @NotNull(message = "Tipo de cenário não pode ser nulo")
    private TipoReino tipoReino;

    @Schema(description = "Horário")
    @NotNull(message = "Horário não pode ser nulo")
    private Date horario;

}
