package br.com.dbc.trabalhofinalmodulo2.mapper;

import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaListDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.BatalhaEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatalhaMapper {

    private final ObjectMapper objectMapper;

    public BatalhaDTO toBatalhaDTO(BatalhaEntity batalha) {
        return objectMapper.convertValue(batalha, BatalhaDTO.class);
    }
    public BatalhaEntity fromCreateDTO(BatalhaCreateDTO batalhaCreateDTO) {
        return objectMapper.convertValue(batalhaCreateDTO, BatalhaEntity.class);
    }
    public BatalhaListDTO fromCreateDTO(BatalhaEntity batalhaEntity) {
        return objectMapper.convertValue(batalhaEntity, BatalhaListDTO.class);
    }


}
