package br.com.dbc.trabalhofinalmodulo2.dto.relatorios;

import br.com.dbc.trabalhofinalmodulo2.entities.TipoClassePersonagem;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RelatorioJogadorDTO {
    @Schema(description = "Identificador do Jogador")
    private Integer idJogador;

    @Schema(description = "Nome do Jogador")
    private String nomeJogador;

    @Schema(description = "Email do jogador")
    private String email;

    @Schema(description = "Quantidade de personagems do jogador")
    private Integer quantidadePersonagems;

    @Schema(description = "Tipo de classe do Personagem")
    private TipoClassePersonagem tipoClassePersonagem;

}
