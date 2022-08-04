package br.com.dbc.trabalhofinalmodulo2.dto.batalha;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BatalhaDTO extends BatalhaCreateDTO {

    @Schema(description = "Id da Batalha")
    @NotNull
    private Integer idBatalha;
}
