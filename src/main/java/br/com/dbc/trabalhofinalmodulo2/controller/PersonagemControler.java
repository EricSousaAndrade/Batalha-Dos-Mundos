package br.com.dbc.trabalhofinalmodulo2.controller;

import br.com.dbc.trabalhofinalmodulo2.dto.page.PageDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.personagem.*;
import br.com.dbc.trabalhofinalmodulo2.entities.TipoClassePersonagem;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NomeExistenteException;
import br.com.dbc.trabalhofinalmodulo2.exceptions.PersonagemPossuiClasseException;
import br.com.dbc.trabalhofinalmodulo2.service.PersonagemClasseService;
import br.com.dbc.trabalhofinalmodulo2.service.PersonagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping ("/personagem")
@RequiredArgsConstructor
public class PersonagemControler {

    private final PersonagemService personagemService;

    private final PersonagemClasseService personagemClasseService;

    @Operation(summary = "Lista todos personagens", description = "Lista todos personagens")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Personagens listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/list")
    public List<PersonagemDTO> listar(){
        return personagemService.listarTodos();
    }

    @Operation(summary = "Adiciona personagem", description = "Adiciona um personagem a um jogador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Personagem adicionado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/adicionarPersonagem")
    public PersonagemDTO post(@Valid @RequestBody PersonagemCreateDTO personagemCreateDTO
                             ) throws NaoEncontradoException, NomeExistenteException {
        return personagemService.adicionar(personagemCreateDTO);
    }

    @Operation(summary = "Adiciona uma classe em um personagem", description = "Adiciona uma classe a um personagem existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Classe adicionada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/novoPersonagemClasse")
    public PersonagemDTO criarPersonLigadoJogador(@RequestParam Integer idPersonagem, @RequestParam TipoClassePersonagem tipoClassePersonagem) throws NaoEncontradoException, PersonagemPossuiClasseException {
        return personagemClasseService.adicionarPersonagemComClasse(idPersonagem, tipoClassePersonagem);
    }

    @Operation(summary = "Edita um Personagem", description = "Edita um Personagem ja existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Personagem editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
   @PutMapping("/editar")
    public PersonagemDTO put(@RequestParam Integer idPersonagem, @Valid @RequestBody PersonagemPutDTO personagemDTO) throws NaoEncontradoException, NomeExistenteException {
         return personagemService.editar(personagemDTO, idPersonagem);
    }

    @Operation(summary = "Deleta um Personagem", description = "Deleta um Personagem ja existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Personagem deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar")
    public void delete(@RequestParam Integer idPersonagem) throws NaoEncontradoException {
        personagemService.remover(idPersonagem);
    }

    @Operation(summary = "Listar todos personagens", description = "Listar com paginação todos personagens")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listarTodos")
    public PageDTO<PersonagemPageDTO> listarComPagina(@RequestParam Integer paginas,
                                                      @RequestParam Integer registros){
        return personagemService.listarPersonagens(paginas,registros);
    }
}
