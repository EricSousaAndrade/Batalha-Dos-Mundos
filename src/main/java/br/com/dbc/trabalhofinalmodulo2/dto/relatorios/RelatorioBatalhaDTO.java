package br.com.dbc.trabalhofinalmodulo2.dto.relatorios;

import br.com.dbc.trabalhofinalmodulo2.entities.TipoClassePersonagem;
import br.com.dbc.trabalhofinalmodulo2.entities.TipoReino;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioBatalhaDTO {
    @Schema(description = "Identificador da Batalha")
    private Integer idBatalha;
    @Schema(description = "Número total de rounds")
    private Integer roundBatalha;
    @Schema(description = "Nome do Boss")
    private String nomeBoss;
    @Schema(description = "Vida final do Boss")
    private Double vidaBoss;
    @Schema(description = "Reino da batalha" )
    private TipoReino tipoReino;
    @Schema(description = "Nome do personagem" )
    private String nomePersonagem;
    @Schema(description = "Classe do personagem" )
    private TipoClassePersonagem tipoClassePersonagem;
    @Schema(description = "Vida final do Personagem")
    private Double vidaPersonagem;

}
