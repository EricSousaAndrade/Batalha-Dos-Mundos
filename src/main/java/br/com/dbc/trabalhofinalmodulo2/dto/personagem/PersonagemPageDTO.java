package br.com.dbc.trabalhofinalmodulo2.dto.personagem;

import br.com.dbc.trabalhofinalmodulo2.dto.classe.ClassePersonagemCreateDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PersonagemPageDTO {

    private Integer id;

    private String nomePersonagem;

    private ClassePersonagemCreateDTO classePersonagemCreateDTO;
}
