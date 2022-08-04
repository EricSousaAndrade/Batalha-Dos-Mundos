package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.dto.jogador.*;
import br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioJogadorDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.CargoEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NomeExistenteException;
import br.com.dbc.trabalhofinalmodulo2.mapper.JogadorMapper;
import br.com.dbc.trabalhofinalmodulo2.repository.JogadorRepository;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ContextConfiguration(classes = {JogadorService.class})
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class JogadorServiceTest {

    @Mock
    private JogadorRepository jogadorRepository;
    @Mock
    private JogadorMapper jogadorMapper;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private JogadorService jogadorService;

    @Test
    public void deveTestarListarTodos() {
        List<JogadorEntity> list = new ArrayList<>();

        when(jogadorRepository.findAll()).thenReturn(list);

        assertTrue(jogadorService.listarTodos().isEmpty());
    }

    @Test
    public void deveTestarDesativarComSucesso() throws NaoEncontradoException {
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> jogadorOptional = Optional.of(jogadorEntity);

        when(this.jogadorRepository.save(any())).thenReturn(jogadorEntity);
        when(this.jogadorRepository.findById(any())).thenReturn(jogadorOptional);
        when(this.jogadorMapper.toDTO(any())).thenReturn(new JogadorDTO());
        doNothing().when(this.emailService).sendEmailJogadorExcluido(any());

        jogadorService.desativar(1);
        assertFalse(jogadorEntity.getEnable());
    }

    @Test(expected = NaoEncontradoException.class)
    public void deveTestarDesativarSemId() throws NaoEncontradoException {

        when(this.jogadorRepository.findById(anyInt())).thenReturn(Optional.empty());

        jogadorService.desativar(1);
    }

    @Test
    public void deveTestarAtivarComSucesso() throws NaoEncontradoException {
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> jogadorOptional = Optional.of(jogadorEntity);

        when(this.jogadorRepository.save(any())).thenReturn(jogadorEntity);
        when(this.jogadorRepository.findById(any())).thenReturn(jogadorOptional);
        when(this.jogadorMapper.toDTO(any())).thenReturn(getJogadorDTO());
        doNothing().when(this.emailService).sendEmailJogadorExcluido(any());

        jogadorService.ativar(1);

        assertTrue(jogadorEntity.getEnable());
    }

    @Test(expected = NaoEncontradoException.class)
    public void deveTestarAtivarSemId() throws NaoEncontradoException {
        when(this.jogadorRepository.findById(any())).thenReturn(Optional.empty());

        jogadorService.ativar(1);

        assertThrows(NaoEncontradoException.class, () -> jogadorService.ativar(1));
    }

    @Test
    public void deveTestarEditar() throws NaoEncontradoException, NomeExistenteException {
        JogadorCreateDTO jogadorCreateDTO = getJogadorCreateDTO();

        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        JogadorDTO jogadorDTO = getJogadorDTO();
        setAuthentication();

        when(jogadorMapper.fromCreateDTO(jogadorDTO)).thenReturn(jogadorEntity);
        when(jogadorMapper.fromCreateDTOToDTO(any())).thenReturn(jogadorDTO);
        when(jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);
        when(jogadorService.getLoggedUser()).thenReturn(getLoginRetornaDTO());

        JogadorDTO jogadorEditado = jogadorService.editar(jogadorCreateDTO);

        assertNotNull(jogadorEditado);
        assertEquals(jogadorEditado.getNomeJogador(), jogadorDTO.getNomeJogador());
        assertEquals(jogadorEditado.getEmail(), jogadorDTO.getEmail());
        assertEquals(jogadorEditado.getIdJogador(), jogadorEntity.getIdJogador());
    }

    @Test
    public void deveTestarTrazJogadorLogado() throws NaoEncontradoException {
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        JogadorLogadoDTO jogadorLogadoDTO1 = getJogadorLogadoDTO();

        when(jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);
        when(jogadorMapper.toLogadoDTO(optionalJogadorEntity.get())).thenReturn(jogadorLogadoDTO1);

        JogadorLogadoDTO jogadorLogadoDTO = jogadorService.trazJogadorLogado();

        assertEquals(jogadorLogadoDTO1, jogadorLogadoDTO);
        assertNotNull(jogadorLogadoDTO);
        assertEquals(Optional.of(1), Optional.ofNullable(jogadorLogadoDTO.getIdJogador()));
    }

    @Test
    public void deveTestarFindByIdComSucesso() throws NaoEncontradoException {
        JogadorEntity jogadorEntity = getJogadorEntity();

        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        when(this.jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);
        JogadorDTO jogadorDTO = getJogadorDTO();

        when(this.jogadorMapper.toDTO(any())).thenReturn(jogadorDTO);

        assertEquals(jogadorDTO, jogadorService.findById(1));
    }

    @Test(expected = NaoEncontradoException.class)
    public void deveTestarFindByIdInvalido() throws NaoEncontradoException {

        when(this.jogadorRepository.findById(any())).thenReturn(Optional.empty());

        jogadorService.findById(1);
    }

    @Test
    public void deveTestarRelatorioJogadores() {
        List<RelatorioJogadorDTO> relatorioJogadorDTOList = List.of(new RelatorioJogadorDTO());

        when(jogadorRepository.listJogadorRelatorio(anyInt())).thenReturn(relatorioJogadorDTOList);

        List<RelatorioJogadorDTO> relatorioJogadorDTOList1 = jogadorService.relatorioJogadores(anyInt());

        assertNotNull(relatorioJogadorDTOList1);
        assertFalse(relatorioJogadorDTOList1.isEmpty());
    }

    @Test
    public void deveTestarFindByLogin() {
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        when(jogadorRepository.findByNomeJogador(anyString())).thenReturn(optionalJogadorEntity);

        Optional<JogadorEntity> procuraJogadorByLogin = jogadorService.findByLogin("Login");

        assertEquals(optionalJogadorEntity, procuraJogadorByLogin);
    }

    @Test
    public void deveTestarCriarUsuario() {
        LoginCreateDTO loginCreateDTO = getLoginCreateDTO();
        JogadorEntity jogadorEntity = getJogadorEntity();
        CargoEntity cargo = new CargoEntity();

        LoginRetornaDTO loginRetornaDTO = getLoginRetornaDTO();

        cargo.setIdCargo(1);
        jogadorEntity.setEnable(true);
        jogadorEntity.setCargos(Set.of(cargo));


        String senhaUsuario = loginCreateDTO.getSenha();

        when(jogadorMapper.fromCreateLoginDTO(loginCreateDTO)).thenReturn(jogadorEntity);
        when(jogadorRepository.save(jogadorEntity)).thenReturn(jogadorEntity);
        when(jogadorMapper.toLoginDTO(jogadorEntity)).thenReturn(loginRetornaDTO);

        LoginRetornaDTO loginRetornaDTO2 = jogadorService.criarUsuario(loginCreateDTO);

        assertEquals(loginRetornaDTO2.getSenha(), senhaUsuario);
        assertEquals(loginRetornaDTO2.getNomeJogador(), loginCreateDTO.getNomeJogador());
        assertEquals(loginRetornaDTO2.getEmail(), loginCreateDTO.getEmail());
        assertEquals(loginRetornaDTO2.getIdJogador(), loginRetornaDTO.getIdJogador());
        assertNotNull(loginRetornaDTO2);
    }

    @Test
    public void deveTestarGettIdLoggedUser() {
        setAuthentication();

        Integer idLoggedUser = jogadorService.getIdLoggedUser();
        assertEquals(Optional.of(123), Optional.ofNullable(idLoggedUser));
    }

    @Test
    public void deveTestarGettLoggedUser() throws NaoEncontradoException {
        LoginRetornaDTO loginRetornaDTO = getLoginRetornaDTO();

        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);
        setAuthentication();

        when(jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);
        when(jogadorService.findLoginById(anyInt())).thenReturn(loginRetornaDTO);

        LoginRetornaDTO loggedUser = jogadorService.getLoggedUser();

        assertEquals(loggedUser, loginRetornaDTO);
        assertNotNull(loggedUser);
    }

    @Test
    public void deveTestarGettLoggedUserEntity() throws NaoEncontradoException {
        LoginRetornaDTO loginRetornaDTO = getLoginRetornaDTO();

        setAuthentication();
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        when(jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);
        when(jogadorService.findLoginById(anyInt())).thenReturn(loginRetornaDTO);

        LoginRetornaDTO loggedUser = jogadorService.getLoggedUser();

        assertEquals(loggedUser, loginRetornaDTO);
        assertNotNull(loggedUser);
    }

    @Test
    public void deveTestarFindLoginById() throws NaoEncontradoException {

        JogadorEntity jogadorEntity = getJogadorEntity();

        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        when(this.jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);

        LoginRetornaDTO loginRetornaDTO = getLoginRetornaDTO();

        when(this.jogadorMapper.toLoginDTO(any())).thenReturn(loginRetornaDTO);

        assertEquals(loginRetornaDTO, jogadorService.findLoginById(1));
    }

    @Test(expected = NaoEncontradoException.class)
    public void deveTestarFindLoginByIdInvalido() throws NaoEncontradoException {

        when(this.jogadorRepository.findById(anyInt())).thenReturn(Optional.empty());

        this.jogadorService.findLoginById(1);
    }

    @Test
    public void deveTestarRemoverLoggedUser() throws NaoEncontradoException {
        LoginRetornaDTO loginRetornaDTO = getLoginRetornaDTO();
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);
        JogadorDTO jogadorDTO = getJogadorDTO();
        setAuthentication();

        when(jogadorMapper.toLoginDTO(jogadorEntity)).thenReturn(loginRetornaDTO);
        when(jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);
        when(jogadorService.getLoggedUser()).thenReturn(loginRetornaDTO);
        when(jogadorMapper.toDTO(jogadorEntity)).thenReturn(jogadorDTO);
        doNothing().when(emailService).sendEmailJogadorExcluido(any());
        doNothing().when(jogadorRepository).delete(any());

        jogadorService.removerLoggedUser();

        verify(jogadorRepository, times(1)).delete(any(JogadorEntity.class));
    }

    @Test
    public void deveTestarBuscaJogadorEntity() throws NaoEncontradoException {
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        when(this.jogadorRepository.findById(anyInt())).thenReturn(optionalJogadorEntity);

        assertEquals(jogadorEntity, this.jogadorService.buscaJogadorEntity(1));
    }

    @Test(expected = NaoEncontradoException.class)
    public void deveTestarBuscaJogadorEntityInvalido() throws NaoEncontradoException {

        when(this.jogadorRepository.findById(anyInt())).thenReturn(Optional.empty());

        jogadorService.buscaJogadorEntity(1);
    }

    @Test(expected = NomeExistenteException.class)
    public void deveTestarVerificaNomeJogador() throws NomeExistenteException {
        JogadorEntity jogadorEntity = getJogadorEntity();
        Optional<JogadorEntity> optionalJogadorEntity = Optional.of(jogadorEntity);

        when(jogadorRepository.findByNomeJogador(anyString())).thenReturn(optionalJogadorEntity);

        jogadorService.verificaNomeJogador("Nome");
    }

    @Test(expected = NaoEncontradoException.class)
    public void deveTestarGetLoggedUserEntity() throws NaoEncontradoException {
        setAuthentication();

        when(jogadorRepository.findById(anyInt())).thenReturn(Optional.empty());

        jogadorService.getLoggedUserEntity();
    }

    private static JogadorCreateDTO getJogadorCreateDTO() {
        JogadorCreateDTO jogadorCreateDTO = new JogadorCreateDTO();
        jogadorCreateDTO.setNomeJogador("Luiz");
        jogadorCreateDTO.setEmail("Luiz@gmail.com");
        jogadorCreateDTO.setSenha("senha");
        return jogadorCreateDTO;
    }

    private static JogadorEntity getJogadorEntity() {
        JogadorEntity jogadorEntity = new JogadorEntity();
        jogadorEntity.setCargos(new HashSet<>());
        jogadorEntity.setEmail("jane.doe@example.org");
        jogadorEntity.setEnable(true);
        jogadorEntity.setIdJogador(1);
        jogadorEntity.setNomeJogador("Nome Jogador");
        jogadorEntity.setPersonagems(new HashSet<>());
        jogadorEntity.setSenha("Senha");
        return jogadorEntity;
    }

    private static LoginRetornaDTO getLoginRetornaDTO() {
        LoginRetornaDTO loginRetornaDTO = new LoginRetornaDTO();
        loginRetornaDTO.setIdJogador(2);
        loginRetornaDTO.setSenha("Teste");
        loginRetornaDTO.setNomeJogador("Joao");
        loginRetornaDTO.setEmail("joao@a.com");
        return loginRetornaDTO;
    }

    private static LoginCreateDTO getLoginCreateDTO() {
        LoginCreateDTO loginCreateDTO = new LoginCreateDTO();
        loginCreateDTO.setSenha("Teste");
        loginCreateDTO.setNomeJogador("Joao");
        loginCreateDTO.setEmail("joao@a.com");
        return loginCreateDTO;
    }

    private static JogadorLogadoDTO getJogadorLogadoDTO() {
        JogadorLogadoDTO jogadorLogadoDTO = new JogadorLogadoDTO();
        jogadorLogadoDTO.setIdJogador(1);
        jogadorLogadoDTO.setNomeJogador("Lucas");
        jogadorLogadoDTO.setEmail("Luiz@gmail.com");
        return jogadorLogadoDTO;
    }

    private static JogadorDTO getJogadorDTO() {
        JogadorDTO jogadorDTO = new JogadorDTO();
        jogadorDTO.setIdJogador(1);
        jogadorDTO.setNomeJogador("Lucas");
        jogadorDTO.setEmail("Luiz@gmail.com");
        setAuthentication();
        return jogadorDTO;
    }

    private static void setAuthentication() {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        123,
                        "senha"
                );
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
    }
}
