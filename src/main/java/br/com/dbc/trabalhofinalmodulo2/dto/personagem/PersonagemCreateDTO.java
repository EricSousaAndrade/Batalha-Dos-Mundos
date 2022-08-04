package br.com.dbc.trabalhofinalmodulo2.dto.personagem;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonagemCreateDTO {

    @NotEmpty
    @NotBlank
    @Schema(description = "Nome do personagem")
    private String nomePersonagem;
}
