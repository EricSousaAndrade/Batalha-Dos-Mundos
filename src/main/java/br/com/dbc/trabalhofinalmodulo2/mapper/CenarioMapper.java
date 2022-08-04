package br.com.dbc.trabalhofinalmodulo2.mapper;


import br.com.dbc.trabalhofinalmodulo2.dto.cenario.CenarioCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.cenario.CenarioDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.CenarioEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CenarioMapper {

    private final ObjectMapper objectMapper;

    public CenarioDTO toCreateDTO(CenarioEntity cenario) {
        return objectMapper.convertValue(cenario, CenarioDTO.class);
    }

    public CenarioEntity fromCreateDTO(CenarioCreateDTO cenarioCreateDTO) {
        return objectMapper.convertValue(cenarioCreateDTO, CenarioEntity.class);
    }

    public CenarioDTO toCenarioDTO(CenarioEntity cenario) {
        return objectMapper.convertValue(cenario, CenarioDTO.class);
    }


}
