package br.com.dbc.trabalhofinalmodulo2.dto.personagem;

import br.com.dbc.trabalhofinalmodulo2.dto.classe.ClassePersonagemPostDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonagemDTO extends PersonagemCreateDTO {

    @NotNull
    @Schema(description = "Id do personagem")
    private Integer id;

    @NotNull
    @Schema(description = "Id do Jogador")
    private Integer idJogador;

    @NotNull
    @Schema(description = "Classe Personagem")
    ClassePersonagemPostDTO classePersonagem;

}
