package br.com.dbc.trabalhofinalmodulo2.dto.personagem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonagemClasseDTO {


    @Schema(description = "Id do personagem")
    @NotNull(message = "Id do personagem não pode ser nulo")
    private int idPersonagem;

    @Schema(description = "Classe do personagem")
    @NotNull(message = "Classe do personagem não pode ser nulo")
    PersonagemClasseCreateDTO personagemClasseCreateDTO;
}
