package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.entities.BossEntity;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.mapper.BossMapper;
import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossDTO;
import br.com.dbc.trabalhofinalmodulo2.repository.BossRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BossService {

    private final BossRepository bossRepository;

    private final BossMapper bossMapper;

    public List<BossDTO> listar() {
        return bossRepository.findAll().stream()
                .map(bossMapper::toBossDTO)
                .collect(Collectors.toList());
    }

    public BossDTO adicionar(BossCreateDTO boss) {
        return bossMapper.toBossDTO(bossRepository.save(bossMapper.toCreateFromBoss(boss)));
    }

    public void remover(int id) throws Exception {
        bossRepository.delete(buscarBoss(id));
    }

    public BossDTO editar(BossCreateDTO boss, int id) throws NaoEncontradoException {
        this.buscarBoss(id);
        BossEntity bossEntity = bossMapper.toCreateFromBoss(boss);
        bossEntity.setIdBoss(id);
        bossRepository.save(bossEntity);
        return bossMapper.toBossDTO(bossEntity);
    }

    public BossEntity buscarBoss(int id) throws NaoEncontradoException {
        return bossRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Boss não encontrado"));
    }
}
