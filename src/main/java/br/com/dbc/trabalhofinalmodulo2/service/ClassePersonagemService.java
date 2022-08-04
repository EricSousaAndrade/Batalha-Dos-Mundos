package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.entities.ClassePersonagemEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.PersonagemEntity;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.mapper.ClassePersonagemMapper;
import br.com.dbc.trabalhofinalmodulo2.dto.classe.ClassePersonagemDTO;
import br.com.dbc.trabalhofinalmodulo2.repository.ClassePersonagemRepository;
import br.com.dbc.trabalhofinalmodulo2.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassePersonagemService {

    private final ClassePersonagemRepository classePersonagemRepository;

    private final ClassePersonagemMapper classePersonagemMapper;

    private final PersonagemRepository personagemRepository;

    public List<ClassePersonagemDTO> listarClassePersonagem() {
        return classePersonagemRepository.findAll().stream()
                .map(classePersonagemMapper::toClassePersonagemDTO).toList();
    }

    public ClassePersonagemEntity retornaClasseEntityPorPersonagem(Integer idPersonagem) throws NaoEncontradoException {
        PersonagemEntity personagemEntity = personagemRepository.findById(idPersonagem)
                .orElseThrow(() -> new NaoEncontradoException("Personagem não encontrado"));
       return personagemEntity.getClassePersonagem();
    }
}

