package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaListDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.page.PageDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioBatalhaDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.*;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.mapper.BossMapper;
import br.com.dbc.trabalhofinalmodulo2.exceptions.VidaMenorQueZero;
import br.com.dbc.trabalhofinalmodulo2.mapper.BatalhaMapper;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaDTO;
import br.com.dbc.trabalhofinalmodulo2.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BatalhaService {

    private final BatalhaRepository batalhaRepository;

    private final BatalhaMapper batalhaMapper;

    private final BossMapper bossMapper;

    private final BossService bossService;

    private final CenarioService cenarioService;

    private final PersonagemService personagemService;

    private final ClassePersonagemRepository classePersonagemRepository;

    public BatalhaDTO adicionar(BatalhaCreateDTO batalha) throws Exception {
        PersonagemEntity personagem = personagemService.personagemPorID(batalha.getIdPersonagem());

        BossEntity bossEntity = bossService.buscarBoss(batalha.getIdBoss());

        CenarioEntity cenarioEntity = cenarioService.verificarCenario(batalha.getIdCenario());
        BatalhaEntity batalha1 = batalhaMapper.fromCreateDTO(batalha);

        batalha1.setCenarioEntity(cenarioEntity);
        batalha1.setPersonagemEntity(personagem);
        batalha1.setBossEntity(bossEntity);

        BatalhaEntity batalhaAdicionada = batalhaRepository.save(batalha1);
        BatalhaDTO atualizadaDto = batalhaMapper.toBatalhaDTO(batalhaAdicionada);
        atualizadaDto.setIdBatalha(batalhaAdicionada.getIdBatalha());

        return atualizadaDto;
    }

    public void remover(Integer id) throws NaoEncontradoException {
        verificaExistente(id);
        batalhaRepository.deleteById(id);
    }

    public String atacar(int idBatalha) throws Exception {

        BatalhaEntity batalha = batalhaRepository.findById(idBatalha).orElseThrow(() -> new NaoEncontradoException("Batalha não encontrada"));
        BossEntity bossAtacado = bossService.buscarBoss(batalha.getBossEntity().getIdBoss());
        PersonagemEntity personagem = personagemService.personagemPorID(batalha.getPersonagemEntity().getId());


        ClassePersonagemEntity classePersonagem = classePersonagemRepository
                .findById(personagem.getClassePersonagem().getId())
                .orElseThrow(() -> new NaoEncontradoException("Classe não encontrada"));

        verificaVidaBossJogador(bossAtacado, classePersonagem, batalha);

        Double defesaBoss = bossAtacado.getDefesaBoss() * 0.4;
        Double ataqueDoJogador = classePersonagem.getAtaquePersonagem();
        Double danoEfetuado = ataqueDoJogador - defesaBoss;

        if(danoEfetuado <= 0){
            return "Seu ataque é muito fraco, você não causou dano no boss";
        }

        Double vidaNova = bossAtacado.getVidaBoss() - danoEfetuado;
        bossAtacado.setVidaBoss(vidaNova);

        batalha.setRoundBatalha(batalha.getRoundBatalha() + 1);

        batalhaRepository.save(batalha);
        bossService.editar(bossMapper.toCreateDTO(bossAtacado), batalha.getBossEntity().getIdBoss());

        return "Seu dano foi de: " + danoEfetuado + "\nA vida do boss é " + vidaNova;
    }

    public String defender(int idBatalha) throws Exception {
        BatalhaEntity batalha = batalhaRepository.findById(idBatalha).orElseThrow(() -> new NaoEncontradoException("Batalha não encontrado"));
        BossEntity bossAtacado = bossService.buscarBoss(batalha.getBossEntity().getIdBoss());
        Double ataqueBoss = bossAtacado.getAtaqueBoss();
        PersonagemEntity personagem = personagemService.personagemPorID(batalha.getPersonagemEntity().getId());

        ClassePersonagemEntity classePersonagem = classePersonagemRepository.findByPersonagemEntities(personagem); //buscar por classe
        Double defesaJogador = classePersonagem.getDefesaPersonagem() * 0.5;

        Double danoEfetuado = ataqueBoss - defesaJogador;
        if(danoEfetuado <= 0){
            return "Defesa bem sucedida você não levou dano do boss" + "\nSua vida e de: " + classePersonagem.getVidaPersonagem();
        }
        Double vidaNova = classePersonagem.getVidaPersonagem() - danoEfetuado;

        verificaVidaBossJogador(bossAtacado, classePersonagem, batalha);

        classePersonagem.setVidaPersonagem(vidaNova);
        batalha.setRoundBatalha(batalha.getRoundBatalha() + 1);
        batalhaRepository.save(batalha);
        classePersonagemRepository.save(classePersonagem);

        return "Você levou " + danoEfetuado + " de dano do boss" + "\nSua vida e de: " + classePersonagem.getVidaPersonagem();
    }

    public void verificaVidaBossJogador(BossEntity bossAtacado, ClassePersonagemEntity classePersonagem, BatalhaEntity batalha) throws VidaMenorQueZero{
        if (bossAtacado.getVidaBoss() <= 0) {
            batalha.setStatus("Vitoria");
            batalhaRepository.save(batalha);
            throw new VidaMenorQueZero("Vida do boss menor que 0");

        } else if (classePersonagem.getVidaPersonagem() <= 0) {
            batalha.setStatus("Derrota");
            batalhaRepository.save(batalha);
            throw new VidaMenorQueZero("Vida do jogador menor que 0");
        }
    }

    public String fugir(int idBatalha) throws NaoEncontradoException {
        BatalhaEntity batalha = batalhaRepository.findById(idBatalha).orElseThrow(() -> new NaoEncontradoException("Batalha não encontrada"));
        batalha.setStatus("Derrota");
        batalhaRepository.save(batalha);

        return "Você fugiu com sucesso o boss era de mais para você :( ";
    }

    private void verificaExistente(Integer id) throws NaoEncontradoException {
        batalhaRepository.findById(id).orElseThrow(() -> new NaoEncontradoException("Batalha não encontrada"));
    }

    public ResponseEntity<List<RelatorioBatalhaDTO>> relatorioBatalha (Integer idBatalha) throws NaoEncontradoException {
        batalhaRepository.listRelatorioDTO(idBatalha).stream()
                .findAny()
                .orElseThrow(() -> new NaoEncontradoException("Batalha não encontrada"));
        List<RelatorioBatalhaDTO> listRelatorio = batalhaRepository.listRelatorioDTO(idBatalha);

        return new ResponseEntity(listRelatorio, HttpStatus.OK);
    }

    public PageDTO<BatalhaListDTO> listarBatalhas(Integer pagina, Integer registro){
        PageRequest pageRequest = PageRequest.of(pagina, registro);
        Page<BatalhaEntity> batalhaPage = batalhaRepository.findAllBy(pageRequest);
        List<BatalhaListDTO> batalhaDTOS = batalhaPage.getContent().stream()
                .map(batalhaMapper::fromCreateDTO)
                .toList();
        return new PageDTO<>(batalhaPage.getTotalElements(),batalhaPage.getTotalPages(), pagina, registro, batalhaDTOS);
    }
}
