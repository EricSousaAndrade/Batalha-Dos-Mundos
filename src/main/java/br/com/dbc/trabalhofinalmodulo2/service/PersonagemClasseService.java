package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.entities.ClassePersonagemEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.PersonagemEntity;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.exceptions.PersonagemPossuiClasseException;
import br.com.dbc.trabalhofinalmodulo2.mapper.ClassePersonagemMapper;
import br.com.dbc.trabalhofinalmodulo2.mapper.PersonagemMapper;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.TipoClassePersonagem;
import br.com.dbc.trabalhofinalmodulo2.repository.ClassePersonagemRepository;
import br.com.dbc.trabalhofinalmodulo2.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PersonagemClasseService {

    private final ClassePersonagemRepository classePersonagemRepository;

    private final ClassePersonagemMapper classePersonagemMapper;

    private final PersonagemRepository personagemRepository;

    private final PersonagemMapper personagemMapper;

    private final PersonagemService personagemService;

    private final JogadorService jogadorService;

    public PersonagemDTO adicionarPersonagemComClasse(Integer idPersonagem, TipoClassePersonagem tipoClassePersonagem) throws NaoEncontradoException, PersonagemPossuiClasseException {

        verificaPersonagemDoJogadorLogado(idPersonagem);
        if(personagemService.personagemPorID(idPersonagem).getClassePersonagem() == null) {
            ClassePersonagemEntity classePersonagem = new ClassePersonagemEntity();
            if (TipoClassePersonagem.MAGO == tipoClassePersonagem) {
                classePersonagem.setVidaPersonagem(800.0);
                classePersonagem.setAtaquePersonagem(200.0);
                classePersonagem.setDefesaPersonagem(100.0);
            } else if (TipoClassePersonagem.GUERREIRO == tipoClassePersonagem) {
                classePersonagem.setVidaPersonagem(800.0);
                classePersonagem.setAtaquePersonagem(100.0);
                classePersonagem.setDefesaPersonagem(200.0);
            } else if (TipoClassePersonagem.ELFO == tipoClassePersonagem) {
                classePersonagem.setVidaPersonagem(800.0);
                classePersonagem.setAtaquePersonagem(150.0);
                classePersonagem.setDefesaPersonagem(150.0);
            }
            classePersonagem.setTipoClassePersonagem(tipoClassePersonagem);


            PersonagemEntity personagem = personagemMapper.fromCreateDTO(personagemService.findById(idPersonagem));
            PersonagemDTO personagemDTO = personagemMapper.toDTO(personagem);

            personagemDTO.setClassePersonagem(classePersonagemMapper.fromCreateClasse(classePersonagem));
            personagemDTO.getClassePersonagem().setTipoClassePersonagem(tipoClassePersonagem);
            personagem.setClassePersonagem(classePersonagem);
            classePersonagemRepository.save(classePersonagem);
            personagemService.adicionarClasse(personagem);
            personagemDTO.getClassePersonagem().setId(classePersonagem.getId());

            return personagemDTO;
        } else {
            throw new PersonagemPossuiClasseException("Personagem já possui uma classe!");
        }
    }

    private void verificaPersonagemDoJogadorLogado(Integer idPersonagem) throws NaoEncontradoException {
        personagemRepository.findAllByIdJogador(jogadorService.getLoggedUser().getIdJogador())
                .stream()
                .filter(personagem -> personagem.getId().equals(idPersonagem))
                .findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Personagem não encontrado"));
    }
}
