package br.com.dbc.trabalhofinalmodulo2.controller;

import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaListDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.page.PageDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioBatalhaDTO;
import br.com.dbc.trabalhofinalmodulo2.exceptions.BancoDeDadosException;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.batalha.BatalhaDTO;
import br.com.dbc.trabalhofinalmodulo2.service.BatalhaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/batalha")
@Validated
@RequiredArgsConstructor
public class BatalhaController {

    private final BatalhaService batalhaService;

    @Operation(summary = "Inicia Batalha", description = "Inicia uma batalha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Batalha iniciada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/iniciar")
    public BatalhaDTO iniciar(@Valid @RequestBody BatalhaCreateDTO batalhaCreateDTO) throws BancoDeDadosException, Exception {
        return batalhaService.adicionar(batalhaCreateDTO);
    }

    @Operation(summary = "Deletar a Batalha pelo id", description = "Deleta uma batalha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Batalha deletada com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar")
    public void deletar(@RequestParam Integer id) throws BancoDeDadosException, NaoEncontradoException {
        batalhaService.remover(id);
    }

    @Operation(summary = "Ataca o boss", description = "Jogador faz um ataque no boss")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso no ataque"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/atacar")
    public String atacar(@RequestParam int idBatalha) throws Exception {
        return batalhaService.atacar(idBatalha);
    }

    @Operation(summary = "Defender ataque", description = "Jogador faz uma defesa e toma menos dano")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso na defesa"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/defender")
    public String defender(@RequestParam int idBatalha) throws Exception {
        return batalhaService.defender(idBatalha);
    }

    @Operation(summary = "Fugir da batalha", description = "Jogador foge da batalha")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Sucesso na fuga"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/fugir")
    public String fugir(@RequestParam int idBatalha) throws NaoEncontradoException {
        return batalhaService.fugir(idBatalha);
    }

    @Operation(summary = "Lista a batalha com dados personalizados", description = "Lista todas a batalha com informações" +
            " da batalha, do boss, do cenário, personagem e classe")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/relatorio-batalha")
    public ResponseEntity<List<RelatorioBatalhaDTO>> relatorioPersonalizadoBatalha(@RequestParam(required = false) Integer idBatalha) throws NaoEncontradoException {
        return new ResponseEntity(batalhaService.relatorioBatalha(idBatalha), HttpStatus.OK);
    }


    @Operation(summary = "Listar todas batalhas", description = "Listar com paginação todas batalhas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Listado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listarTodos")
    public PageDTO<BatalhaListDTO> listarComPagina(@RequestParam Integer paginas,
                                                   @RequestParam Integer registros){
        return batalhaService.listarBatalhas(paginas,registros);
    }

}
