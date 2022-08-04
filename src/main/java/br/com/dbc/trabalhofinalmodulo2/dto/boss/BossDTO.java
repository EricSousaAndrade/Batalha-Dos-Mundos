package br.com.dbc.trabalhofinalmodulo2.dto.boss;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BossDTO extends BossCreateDTO {

    @NotEmpty
    @Schema(description = "ID do boss")
    private int idBoss;
}
