package br.com.dbc.trabalhofinalmodulo2.mapper;

import br.com.dbc.trabalhofinalmodulo2.dto.jogador.*;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JogadorMapper {


    private final ObjectMapper objectMapper;

    public JogadorDTO toDTO(JogadorEntity jogador) {
        return objectMapper.convertValue(jogador, JogadorDTO.class);
    }
    public JogadorEntity fromCreateDTO(JogadorCreateDTO jogadorCreateDTO) {
        return objectMapper.convertValue(jogadorCreateDTO, JogadorEntity.class);
    }

    public JogadorEntity fromCreateDTO(JogadorDTO jogadorDTO) {
        return objectMapper.convertValue(jogadorDTO, JogadorEntity.class);
    }

    public JogadorDTO fromCreateDTOToDTO(JogadorCreateDTO jogadorCreateDTO) {
        return objectMapper.convertValue(jogadorCreateDTO, JogadorDTO.class);
    }

    public JogadorEntity fromCreateLoginDTO(LoginCreateDTO loginCreateDTO) {
        return objectMapper.convertValue(loginCreateDTO, JogadorEntity.class);
    }

    public LoginRetornaDTO toLoginDTO(JogadorEntity jogadorEntity) {
        return objectMapper.convertValue(jogadorEntity, LoginRetornaDTO.class);
    }

    public JogadorEntity fromCreateRetornaLoginDTO(LoginRetornaDTO retornaDTO) {
        return objectMapper.convertValue(retornaDTO, JogadorEntity.class);
    }

    public JogadorLogadoDTO toLogadoDTO(JogadorEntity jogador) {
        return objectMapper.convertValue(jogador, JogadorLogadoDTO.class);
    }



}
