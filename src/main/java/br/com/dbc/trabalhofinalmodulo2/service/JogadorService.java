package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.dto.jogador.*;
import br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioJogadorDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.CargoEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NomeExistenteException;
import br.com.dbc.trabalhofinalmodulo2.mapper.JogadorMapper;
import br.com.dbc.trabalhofinalmodulo2.repository.JogadorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class JogadorService {

    private final JogadorRepository jogadorRepository;

    private final JogadorMapper jogadorMapper;

    private final EmailService emailService;

    public List<JogadorDTO> listarTodos() {
        return jogadorRepository.findAll().stream()
                .map(jogadorMapper::toDTO)
                .toList();
    }

    public void desativar(Integer idJogador) throws NaoEncontradoException {
        log.info("Jogador Deletado");

        JogadorEntity jogadorRecuperado = buscaJogadorEntity(idJogador);
        jogadorRecuperado.setEnable(false);
        JogadorDTO jogadorDTO = jogadorMapper.toDTO(jogadorRecuperado);

        emailService.sendEmailJogadorExcluido(jogadorDTO);
        log.warn("Enviando E-mail.. " + jogadorDTO.getEmail() + "!");

        jogadorRepository.save(jogadorRecuperado);
    }

    public void ativar(Integer idJogador) throws NaoEncontradoException {
        log.info("Jogador Deletado");

        JogadorEntity jogadorRecuperado = buscaJogadorEntity(idJogador);
        jogadorRecuperado.setEnable(true);
        JogadorDTO jogadorDTO = jogadorMapper.toDTO(jogadorRecuperado);
        emailService.sendEmailJogadorExcluido(jogadorDTO);
        log.warn("Enviando E-mail.. " + jogadorDTO.getEmail() + "!");

        jogadorRepository.save(jogadorRecuperado);
    }

    public JogadorDTO editar(JogadorCreateDTO jogador) throws NaoEncontradoException, NomeExistenteException {
        log.info("Jogador Editado");
        JogadorEntity jogadorEntity = this.getLoggedUserEntity();

        Integer id = jogadorEntity.getIdJogador();
        verificaNomeJogador(jogador.getNomeJogador());

        JogadorDTO jogadorDTO = jogadorMapper.fromCreateDTOToDTO(jogador);

        jogadorDTO.setIdJogador(id);
        JogadorEntity jogadorRecuperado = jogadorMapper.fromCreateDTO(jogadorDTO);
        jogadorRecuperado.setSenha(new SCryptPasswordEncoder().encode(jogador.getSenha()));
        jogadorRecuperado.setCargos(jogadorEntity.getCargos());

        jogadorRepository.save(jogadorRecuperado);

        emailService.sendEmailJogadorEditado(jogadorDTO);
        log.warn("Enviando E-mail.. " + jogadorRecuperado.getEmail() + "!");

        return jogadorDTO;
    }

    public JogadorLogadoDTO trazJogadorLogado() throws NaoEncontradoException {
        Integer id = this.getIdLoggedUser();
        JogadorEntity jogadorRecuperado = jogadorRepository
                .findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Jogador não encontrado"));
        JogadorLogadoDTO jogadorLogadoDTO = jogadorMapper.toLogadoDTO(jogadorRecuperado);
        Set<String> listaNomes = jogadorRecuperado.getCargos().stream()
                .map(CargoEntity::getNome)
                .collect(Collectors.toSet());
        jogadorLogadoDTO.setCargos(listaNomes);
        return jogadorLogadoDTO;
    }

    public JogadorDTO findById(Integer id) throws NaoEncontradoException {
        JogadorEntity jogadorRecuperado = jogadorRepository
                .findById(id)
                .orElseThrow(() -> new NaoEncontradoException("Jogador não encontrado"));
        return jogadorMapper.toDTO(jogadorRecuperado);
    }

    public void verificaNomeJogador(String nome) throws NomeExistenteException {
        if (jogadorRepository.findByNomeJogador(nome).isPresent()) {
            throw new NomeExistenteException("já existe um Jogador com esse nome");
        }

    }

    public List<RelatorioJogadorDTO> relatorioJogadores(Integer id) {
        return jogadorRepository.listJogadorRelatorio(id);
    }

    public Optional<JogadorEntity> findByLogin(String login) {
        return jogadorRepository.findByNomeJogador(login);
    }

    public LoginRetornaDTO criarUsuario(LoginCreateDTO loginCreateDTO) {
        SCryptPasswordEncoder sCryptPasswordEncoder = new SCryptPasswordEncoder();
        String senhaUsuario = loginCreateDTO.getSenha();

        loginCreateDTO.setSenha(sCryptPasswordEncoder.encode(senhaUsuario));

        CargoEntity cargo = new CargoEntity();
        cargo.setIdCargo(2);

        JogadorEntity jogadorEntity = jogadorMapper.fromCreateLoginDTO(loginCreateDTO);
        jogadorEntity.setEnable(true);
        jogadorEntity.setCargos(Set.of(cargo));

        jogadorRepository.save(jogadorEntity);
        return jogadorMapper.toLoginDTO(jogadorEntity);
    }

    public Integer getIdLoggedUser() {
        return (Integer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }

    public LoginRetornaDTO getLoggedUser() throws NaoEncontradoException {
        return findLoginById(getIdLoggedUser());
    }

    public JogadorEntity getLoggedUserEntity() throws NaoEncontradoException {
        return jogadorRepository.findById(getIdLoggedUser())
                .orElseThrow(() ->
                        new NaoEncontradoException("Usuário não encontrado"));
    }

    public LoginRetornaDTO findLoginById(Integer idUsuario) throws NaoEncontradoException {
        JogadorEntity jogadorEntity = jogadorRepository.findById(idUsuario)
                .orElseThrow(() ->
                        new NaoEncontradoException("Usuário não encontrado"));
        return jogadorMapper.toLoginDTO(jogadorEntity);
    }

    public void removerLoggedUser() throws NaoEncontradoException {
        LoginRetornaDTO jogador = getLoggedUser();
        JogadorEntity jogadorRecuperado = buscaJogadorEntity(jogador.getIdJogador());
        JogadorDTO jogadorDTO = jogadorMapper.toDTO(jogadorRecuperado);

        emailService.sendEmailJogadorExcluido(jogadorDTO);
        log.warn("Enviando E-mail.. " + jogadorDTO.getEmail() + "!");

        jogadorRepository.delete(jogadorRecuperado);
    }

    public JogadorEntity buscaJogadorEntity(Integer idJogador) throws NaoEncontradoException {
        return jogadorRepository.findById(idJogador).orElseThrow(() ->
                new NaoEncontradoException("Usuário não encontrado"));
    }

}
