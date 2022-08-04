package br.com.dbc.trabalhofinalmodulo2.mapper;

import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemPageDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemPutDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.PersonagemEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PersonagemMapper {

    private final ObjectMapper objectMapper;

    public PersonagemDTO toDTO(PersonagemEntity personagem) {
        return objectMapper.convertValue(personagem, PersonagemDTO.class);
    }

    public PersonagemEntity fromCreateDTO(PersonagemCreateDTO personagemCreateDTO) {
        return objectMapper.convertValue(personagemCreateDTO, PersonagemEntity.class);
    }

    public PersonagemEntity fromCreateDTO(PersonagemDTO personagemDTO) {
        return objectMapper.convertValue(personagemDTO, PersonagemEntity.class);
    }

    public PersonagemDTO fromCreateDTOToDTO(PersonagemPutDTO personagemPutDTO) {
        return objectMapper.convertValue(personagemPutDTO, PersonagemDTO.class);
    }


    public PersonagemPageDTO fromCreatePageDTO(PersonagemEntity personagemEntity) {
        return objectMapper.convertValue(personagemEntity, PersonagemPageDTO.class);
    }
}
