package br.com.dbc.trabalhofinalmodulo2.mapper;

import br.com.dbc.trabalhofinalmodulo2.dto.classe.ClassePersonagemCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.classe.ClassePersonagemDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.classe.ClassePersonagemPostDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.ClassePersonagemEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ClassePersonagemMapper {

    private final ObjectMapper objectMapper;

    public ClassePersonagemDTO toClassePersonagemDTO(ClassePersonagemEntity classePersonagem) {
        return objectMapper.convertValue(classePersonagem, ClassePersonagemDTO.class);
    }

    public ClassePersonagemPostDTO fromCreateClasse(ClassePersonagemEntity classePersonagem) {
        return objectMapper.convertValue(classePersonagem, ClassePersonagemPostDTO.class);
    }

    public ClassePersonagemPostDTO fromCreateDTOClasse(ClassePersonagemEntity classePersonagem) {
        return objectMapper.convertValue(classePersonagem, ClassePersonagemPostDTO.class);
    }

    public ClassePersonagemCreateDTO fromCreateToEntity(ClassePersonagemEntity classePersonagem) {
        return objectMapper.convertValue(classePersonagem, ClassePersonagemCreateDTO.class);
    }

}
