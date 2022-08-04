package br.com.dbc.trabalhofinalmodulo2.dto.classe;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassePersonagemDTO extends ClassePersonagemCreateDTO{

    @Schema(description = "ID - Identificador único da classe do personagem")
    @NotNull(message = "Id do personagem não pode ser nulo")
    private int idClassePersonagem;
}
