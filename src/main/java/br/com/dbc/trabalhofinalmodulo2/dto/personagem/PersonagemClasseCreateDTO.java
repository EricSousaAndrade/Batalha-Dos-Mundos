package br.com.dbc.trabalhofinalmodulo2.dto.personagem;

import br.com.dbc.trabalhofinalmodulo2.entities.TipoClassePersonagem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonagemClasseCreateDTO {

    @Schema(description = "Nome da classe do personagem")
    @NotNull(message = "Nome da classe do personagem não pode ser nulo")
    private TipoClassePersonagem tipoClassePersonagem;
}
