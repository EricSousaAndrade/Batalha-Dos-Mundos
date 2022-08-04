package br.com.dbc.trabalhofinalmodulo2.dto.classe;

import br.com.dbc.trabalhofinalmodulo2.entities.TipoClassePersonagem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ClassePersonagemPostDTO {

    @Schema(description = "Id Classe Personagem")
    @NotNull(message = "Id do personagem não pode ser nulo")
    private int id;

    @Schema(description = "Tipo de classe do personagem")
    @NotBlank(message = "Tipo do personagem é obrigatório")
    private TipoClassePersonagem tipoClassePersonagem;

    @Schema(description = "Vida da Classe")
    @NotBlank(message = "Vida do personagem é obrigatório")
    private Double vidaPersonagem;

    @Schema(description = "Defesa da classe")
    @NotBlank(message = "Defesa do personagem é obrigatório")
    private Double defesaPersonagem;

    @Schema(description = "Ataque da classe")
    @NotBlank(message = "Ataque do personagem é obrigatório")
    private Double ataquePersonagem;
}
