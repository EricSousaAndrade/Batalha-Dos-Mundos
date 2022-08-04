package br.com.dbc.trabalhofinalmodulo2.service;

import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaListDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.page.PageDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.*;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.mapper.BatalhaMapper;
import br.com.dbc.trabalhofinalmodulo2.repository.BatalhaRepository;
import br.com.dbc.trabalhofinalmodulo2.repository.BossRepository;
import br.com.dbc.trabalhofinalmodulo2.repository.ClassePersonagemRepository;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.postgresql.hostchooser.HostRequirement.any;

@ContextConfiguration(classes = {BatalhaService.class})
@ExtendWith(SpringExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class BatalhaServiceTest {

    @InjectMocks
    private BatalhaService batalhaService;
    @Mock
    private BossService bossService;
    @Mock
    private PersonagemService personagemService;
    private ObjectMapper objectMapper = new ObjectMapper();
    @Mock
    private CenarioService cenarioService;

    private BatalhaMapper batalhaMapper = new BatalhaMapper(objectMapper);
    @Mock
    private BatalhaRepository batalhaRepository;
    @Mock
    private ClassePersonagemRepository classePersonagemRepository;
    @Mock
    private BossRepository bossRepository;

    @Before
    public void init() {
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        objectMapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        ReflectionTestUtils.setField(batalhaMapper, "objectMapper", objectMapper);
        ReflectionTestUtils.setField(batalhaService, "batalhaMapper", batalhaMapper);
    }

    @Test
    public void deveTestarListaBatalhaPaginadoComSucesso() {
        List<BatalhaEntity> batalhaEntities = List.of(getBatalhaEntity());
        Page<BatalhaEntity> pageBatalha = new PageImpl<>(batalhaEntities);

        when(batalhaRepository.findAllBy(any(Pageable.class))).thenReturn(pageBatalha);
        PageDTO<BatalhaListDTO> paginasDeBatalhas = batalhaService.listarBatalhas(1, 2);

        Assert.assertNotNull(paginasDeBatalhas);
        Assert.assertEquals(1, paginasDeBatalhas.getTotalElements().intValue());
        Assert.assertEquals(1, paginasDeBatalhas.getContent().size());
        Assert.assertEquals(1, paginasDeBatalhas.getPage().intValue());
        Assert.assertEquals(1, paginasDeBatalhas.getTotalPages().intValue());

    }
    
    
    @Test
    public void deveTestarAdicionarComSucesso() throws Exception {
        BatalhaCreateDTO batalhaCreateDTO = getBatalhaCreateDTO();
        BatalhaEntity batalhaEntity = getBatalhaEntity();
        CenarioEntity cenarioEntity = getCenarioEntity();
        BossEntity bossEntity = getBossEntity();

        batalhaEntity.setIdBatalha(1);

        when(personagemService.personagemPorID(anyInt())).thenReturn(getPersonagemEntity());
        when(bossService.buscarBoss(anyInt())).thenReturn(getBossEntity());
        when(cenarioService.verificarCenario(anyInt())).thenReturn(cenarioEntity);
        when(batalhaRepository.save(any(BatalhaEntity.class))).thenReturn(batalhaEntity);
        BatalhaDTO batalhaDTO = batalhaService.adicionar(batalhaCreateDTO);

        Assert.assertNotNull(batalhaDTO);
        Assert.assertEquals(getBatalhaDTO().getRoundBatalha(), batalhaDTO.getRoundBatalha());
        Assert.assertEquals(getBatalhaDTO().getIdBoss(), batalhaDTO.getIdBoss());
        Assert.assertEquals(getBatalhaDTO().getIdCenario(), batalhaDTO.getIdCenario());
        Assert.assertEquals(getBatalhaDTO().getIdBatalha(), batalhaDTO.getIdBatalha());
        Assert.assertEquals(getBatalhaDTO().getStatus(), batalhaDTO.getStatus());
        Assert.assertEquals(getBatalhaDTO().getIdPersonagem(), batalhaDTO.getIdPersonagem());
    }

    @Test
    public void deveTestarDeleteComSucesso() throws NaoEncontradoException {
        Integer idDeletado = 1;
        BatalhaEntity batalhaEntity = getBatalhaEntity();

        when(batalhaRepository.findById(anyInt())).thenReturn(Optional.of(batalhaEntity));
        doNothing().when(batalhaRepository).deleteById(anyInt());

        batalhaService.remover(idDeletado);
        verify(batalhaRepository, times(1)).deleteById(anyInt());
    }

    @Test(expected = NaoEncontradoException.class)
    public void deveTestarDeleteSemId() throws NaoEncontradoException {

        BatalhaCreateDTO batalhaCreateDTO = getBatalhaCreateDTO();
       when(batalhaRepository.findById(anyInt())).thenReturn(Optional.empty());
       batalhaService.remover(1);
    }

    @Test
    public void deveTestarAtacar() throws Exception {
        BatalhaEntity batalhaEntity = getBatalhaEntity();
        BossEntity bossEntity = getBossEntity();
        PersonagemEntity personagemEntity = getPersonagemEntity();
        ClassePersonagemEntity classePersonagemEntity = getClassePersonagemEntity();
        BossCreateDTO bossCreateDTO = getBossCreateDTO();
        BossDTO bossDTO = getBossDTO();

        when(batalhaRepository.findById(anyInt())).thenReturn(Optional.of(batalhaEntity));
        when(bossService.buscarBoss(anyInt())).thenReturn(bossEntity);
        when(personagemService.personagemPorID(anyInt())).thenReturn(personagemEntity);
        when(classePersonagemRepository.findById(anyInt())).thenReturn(Optional.of(classePersonagemEntity));
        when(bossService.editar(any(BossCreateDTO.class), anyInt())).thenReturn(bossDTO);

        String retorno = batalhaService.atacar(1);
        Boolean vazio = retorno.isEmpty();


        verify(batalhaService, times(1)).atacar(anyInt());
        Assert.assertNotNull(retorno);
        Assert.assertFalse(vazio);
    }



    private static BatalhaEntity getBatalhaEntity() {
        BatalhaEntity batalhaEntity = new BatalhaEntity();
        batalhaEntity.setRoundBatalha(0);
        batalhaEntity.setStatus("Em Andamento");
        batalhaEntity.setIdCenario(1);
        batalhaEntity.setIdPersonagem(1);
        batalhaEntity.setIdBoss(1);
        batalhaEntity.setIdBatalha(1);
        batalhaEntity.setBossEntity(getBossEntity());
        batalhaEntity.setPersonagemEntity(getPersonagemEntity());
        return batalhaEntity;
    }

    private static BatalhaCreateDTO getBatalhaCreateDTO() {
        BatalhaCreateDTO batalhaCreateDTO = new BatalhaCreateDTO();
        batalhaCreateDTO.setRoundBatalha(0);
        batalhaCreateDTO.setStatus("Em Andamento");
        batalhaCreateDTO.setIdCenario(1);
        batalhaCreateDTO.setIdPersonagem(1);
        batalhaCreateDTO.setIdBoss(1);
        return batalhaCreateDTO;
    }


    private static PersonagemEntity getPersonagemEntity(){
        PersonagemEntity personagemEntity = new PersonagemEntity();
        personagemEntity.setId(1);
        personagemEntity.setIdJogador(1);
        personagemEntity.setBatalhas(new HashSet<>());
        personagemEntity.setJogadorEntity(getJogadorEntity());
        return personagemEntity;
    }

    private static CenarioEntity getCenarioEntity(){
        CenarioEntity cenarioEntity = new CenarioEntity();
        cenarioEntity.setIdCenario(1);
        cenarioEntity.setNomeCenario("UMI");
        cenarioEntity.setTipoReino(TipoReino.LUA);
        cenarioEntity.setBatalhas(new HashSet<>());
        return cenarioEntity;
    }

    public static JogadorEntity getJogadorEntity() {
        JogadorEntity jogadorEntity = new JogadorEntity();
        jogadorEntity.setCargos(new HashSet<>());
        jogadorEntity.setEmail("jane.doe@example.org");
        jogadorEntity.setEnable(true);
        jogadorEntity.setIdJogador(1);
        jogadorEntity.setNomeJogador("Nome Jogador");
        jogadorEntity.setPersonagems(new HashSet<>());
        jogadorEntity.setSenha("Senha");
        Optional<JogadorEntity> jogadorOptional = Optional.of(jogadorEntity);
        return jogadorEntity;
    }

    public static ClassePersonagemEntity getClassePersonagemEntity() {
        ClassePersonagemEntity classePersonagemEntity = new ClassePersonagemEntity();
        classePersonagemEntity.setPersonagemEntities(new HashSet<>());
        classePersonagemEntity.setVidaPersonagem(800.0);
        classePersonagemEntity.setTipoClassePersonagem(TipoClassePersonagem.GUERREIRO);
        classePersonagemEntity.setAtaquePersonagem(80.0);
        classePersonagemEntity.setVidaPersonagem(800.0);
        classePersonagemEntity.setDefesaPersonagem(75.0);
        classePersonagemEntity.setId(1);
        return classePersonagemEntity;
    }

    public static BatalhaDTO getBatalhaDTO(){
        BatalhaDTO batalhaDTO = new BatalhaDTO();
        batalhaDTO.setRoundBatalha(0);
        batalhaDTO.setStatus("Em Andamento");
        batalhaDTO.setIdCenario(1);
        batalhaDTO.setIdPersonagem(1);
        batalhaDTO.setIdBoss(1);
        batalhaDTO.setIdBatalha(1);
        return batalhaDTO;
    }

    private static BossEntity getBossEntity(){
        BossEntity bossEntity = new BossEntity();
        bossEntity.setIdBoss(1);
        bossEntity.setVidaBoss(1500.0);
        bossEntity.setAtaqueBoss(80.0);
        bossEntity.setDefesaBoss(75.0);
        bossEntity.setNomeBoss("StarStreamer");
        bossEntity.setBatalhas(new HashSet<>());
        return bossEntity;
    }
    public static BossCreateDTO getBossCreateDTO(){
        BossCreateDTO bossCreateDTO = new BossCreateDTO();
        bossCreateDTO.setAtaqueBoss(80.0);
        bossCreateDTO.setDefesaBoss(75.0);
        bossCreateDTO.setVidaBoss(1500.0);
        bossCreateDTO.setNomeBoss("StarStreamer");
        return bossCreateDTO;
    }

    public static BossDTO getBossDTO(){
        BossDTO bossDTO = new BossDTO();
        bossDTO.setAtaqueBoss(80.0);
        bossDTO.setDefesaBoss(75.0);
        bossDTO.setVidaBoss(1500.0);
        bossDTO.setNomeBoss("StarStreamer");
        bossDTO.setIdBoss(1);
        return bossDTO;
    }
}
