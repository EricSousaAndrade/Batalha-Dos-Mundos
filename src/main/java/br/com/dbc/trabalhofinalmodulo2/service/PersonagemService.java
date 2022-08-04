package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.dto.page.PageDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemPageDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.PersonagemEntity;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NomeExistenteException;
import br.com.dbc.trabalhofinalmodulo2.mapper.ClassePersonagemMapper;
import br.com.dbc.trabalhofinalmodulo2.mapper.JogadorMapper;
import br.com.dbc.trabalhofinalmodulo2.mapper.PersonagemMapper;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.PersonagemPutDTO;
import br.com.dbc.trabalhofinalmodulo2.repository.PersonagemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class PersonagemService {

    private final PersonagemRepository personagemRepository;

    private final PersonagemMapper personagemMapper;

    private final JogadorMapper jogadorMapper;

    private final ClassePersonagemMapper classePersonagemMapper;

    private final JogadorService jogadorService;

    private final ClassePersonagemService classePersonagemService;

    public PersonagemDTO adicionar(PersonagemCreateDTO personagem) throws NomeExistenteException, NaoEncontradoException {
        verificaNomePersonagem(personagem.getNomePersonagem());
        log.info("Personagem criado");
        Integer idJogador = jogadorService.getIdLoggedUser();
        PersonagemEntity personagemEntity = personagemMapper.fromCreateDTO(personagem);
        JogadorEntity jogadorEntity = jogadorMapper.fromCreateDTO(jogadorService.findById(idJogador));
        personagemEntity.setJogadorEntity(jogadorEntity);
        personagemEntity.setIdJogador(jogadorEntity.getIdJogador());
        return personagemMapper.toDTO(personagemRepository.save(personagemEntity));
    }


    public List<PersonagemDTO> listarTodos() {
        return personagemRepository.findAllByIdJogador(jogadorService.getIdLoggedUser())
                .stream().map(p -> {
               try {
                   PersonagemDTO personagemDTO = personagemMapper.toDTO(p);
                   personagemDTO.setClassePersonagem(classePersonagemMapper.fromCreateDTOClasse(classePersonagemService.retornaClasseEntityPorPersonagem(p.getId())));
                   return personagemDTO;
               } catch (NaoEncontradoException e) {
                   throw new RuntimeException(e);
               }
           }).collect(Collectors.toList());
    }

    public PersonagemDTO editar(PersonagemPutDTO personagem, Integer idPersonagem) throws NaoEncontradoException, NomeExistenteException {
        verificaPersonagemDoJogadorLogado(idPersonagem);
        verificaNomePersonagem(personagem.getNomePersonagem());
        PersonagemDTO personagemDTO = personagemMapper.fromCreateDTOToDTO(personagem);
        personagemDTO.setId(idPersonagem);
        PersonagemEntity personagemEntity = personagemPorID(idPersonagem);
        personagemEntity.setNomePersonagem(personagem.getNomePersonagem());
        personagemRepository.save(personagemEntity);

        personagemDTO.setIdJogador(personagemEntity.getJogadorEntity().getIdJogador());
        personagemDTO.setClassePersonagem(classePersonagemMapper.fromCreateClasse(personagemEntity.getClassePersonagem()));
        return personagemDTO;
    }

    public void adicionarClasse(PersonagemEntity personagem) throws NaoEncontradoException {
        PersonagemEntity personagemEntity = personagemPorID(personagem.getId());
        personagemEntity.setClassePersonagem(personagem.getClassePersonagem());
        personagemRepository.save(personagemEntity);
    }

    public void remover(Integer id) throws NaoEncontradoException {
        PersonagemEntity personagemEntity = jogadorService.getLoggedUserEntity().getPersonagems().stream()
                .filter(a -> a.getId().equals(id))
                .findFirst()
                .orElseThrow(()-> new NaoEncontradoException("Personagem não encontrado"));
        personagemRepository.delete(personagemEntity);
    }

    public PersonagemDTO findById(Integer id) throws NaoEncontradoException {
        PersonagemEntity personagemRecuperado = personagemRepository.findById(id).orElseThrow(()-> new NaoEncontradoException("Personagem não encontrado"));
        return personagemMapper.toDTO(personagemRecuperado);
    }

    public PersonagemEntity personagemPorID(Integer id) throws NaoEncontradoException {
        return personagemRepository.findById(id).orElseThrow(()-> new NaoEncontradoException("Personagem não encontrado"));
    }

    private void verificaNomePersonagem(String nome) throws NomeExistenteException {
        if (personagemRepository.findByNomePersonagem(nome).isPresent()) {
            throw new NomeExistenteException("já existe um Personagem com esse nome");
        }
    }

    public PageDTO<PersonagemPageDTO> listarPersonagens(Integer pagina, Integer registro){
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<PersonagemEntity> personagensPage = personagemRepository.findAll(pageRequest);
        List<PersonagemPageDTO> personagemDTOS = personagensPage.getContent().stream()
                .map(p ->  {
            try {
                PersonagemPageDTO personagemDTO = personagemMapper.fromCreatePageDTO(p);
                personagemDTO.setClassePersonagemCreateDTO(classePersonagemMapper.fromCreateToEntity(classePersonagemService.retornaClasseEntityPorPersonagem(p.getId())));
                return personagemDTO;
            } catch (NaoEncontradoException e) {
                throw new RuntimeException(e);
            }
        }).collect(Collectors.toList());
        return new PageDTO<>(personagensPage.getTotalElements(),personagensPage.getTotalPages(), pagina, registro, personagemDTOS);
    }

    private void verificaPersonagemDoJogadorLogado(Integer idPersonagem) throws NaoEncontradoException {
        personagemRepository.findAllByIdJogador(jogadorService.getLoggedUser().getIdJogador())
                .stream().filter(personagemEntity -> personagemEntity.getId().equals(idPersonagem)).findFirst()
                .orElseThrow(() -> new NaoEncontradoException("Você não possui este personagem"));
    }
}
