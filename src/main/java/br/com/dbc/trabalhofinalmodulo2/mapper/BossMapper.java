package br.com.dbc.trabalhofinalmodulo2.mapper;

import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.BossEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BossMapper {

    private final ObjectMapper objectMapper;

    public BossCreateDTO toCreateDTO(BossEntity boss) {
        return objectMapper.convertValue(boss, BossCreateDTO.class);
    }

    public BossDTO toBossDTO(BossEntity boss) {
        return objectMapper.convertValue(boss, BossDTO.class);
    }

    public BossEntity toCreateFromBoss(BossCreateDTO bossCreateDTO) {
        return objectMapper.convertValue(bossCreateDTO, BossEntity.class);
    }

}
