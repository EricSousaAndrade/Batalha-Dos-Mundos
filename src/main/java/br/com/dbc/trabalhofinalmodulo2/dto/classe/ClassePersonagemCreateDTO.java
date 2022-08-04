package br.com.dbc.trabalhofinalmodulo2.dto.classe;

import br.com.dbc.trabalhofinalmodulo2.entities.TipoClassePersonagem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassePersonagemCreateDTO {


    @Schema(description = "Nome da classe do personagem")
    @NotNull(message = "Nome da classe do personagem não pode ser nulo")
    private TipoClassePersonagem tipoClassePersonagem;

    @Schema(description = "Vida da classe do personagem")
    @NotNull(message = "Vida da classe do personagem não pode ser nulo")
    private Double vidaPersonagem;

    @Schema(description = "Defesa da classe do personagem")
    @NotNull(message = "Defesa da classe do personagem não pode ser nulo")
    private Double defesaPersonagem;

    @Schema(description = "Ataque da classe do personagem")
    @NotNull(message = "Ataque da classe do personagem não pode ser nulo")
    private Double ataquePersonagem ;

}
