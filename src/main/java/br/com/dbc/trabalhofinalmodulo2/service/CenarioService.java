package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.entities.CenarioEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.TipoReino;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.mapper.CenarioMapper;
import br.com.dbc.trabalhofinalmodulo2.dto.cenario.CenarioCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.cenario.CenarioDTO;
import br.com.dbc.trabalhofinalmodulo2.repository.CenarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class CenarioService {

    private final CenarioRepository cenarioRepository;

    private final CenarioMapper cenarioMapper;


    public CenarioDTO adicionar(CenarioCreateDTO cenario){
                return cenarioMapper
                        .toCreateDTO(cenarioRepository.save(cenarioMapper.fromCreateDTO(cenario)));
    }


    public List<CenarioDTO> listar() {
        return cenarioRepository
                .findAll()
                .stream()
                .map(cenarioMapper::toCreateDTO)
                .collect(Collectors.toList());
    }

    public CenarioDTO editar(CenarioCreateDTO cenario, int id) throws Exception {
        this.verificarCenario(id);
        CenarioEntity cenarioEntity = cenarioMapper.fromCreateDTO(cenario);
        cenarioEntity.setIdCenario(id);
        cenarioRepository.save(cenarioEntity);
        return cenarioMapper.toCenarioDTO(cenarioEntity);
    }


    public void remover(int id) throws Exception {
        verificarCenario(id);
        cenarioRepository.deleteById(id);
    }

    public CenarioEntity verificarCenario(int id) throws Exception {
        return cenarioRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Cenário não encontrado"));
    }

    public List<CenarioDTO> listCenarioPorReino(TipoReino tipoReino) {
        List<CenarioEntity> cenarioEntities = cenarioRepository.getCenarioEntitiesByTipoReino(tipoReino);
        return cenarioEntities.stream()
                .map(cenarioMapper::toCreateDTO)
                .toList();
    }
}
