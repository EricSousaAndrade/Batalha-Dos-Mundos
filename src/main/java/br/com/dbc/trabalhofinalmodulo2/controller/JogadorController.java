package br.com.dbc.trabalhofinalmodulo2.controller;

import br.com.dbc.trabalhofinalmodulo2.dto.jogador.JogadorLogadoDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioJogadorDTO;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.dto.jogador.JogadorCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.jogador.JogadorDTO;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NomeExistenteException;
import br.com.dbc.trabalhofinalmodulo2.service.JogadorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/jogador")
@Validated
@RequiredArgsConstructor
public class JogadorController {

    private final JogadorService jogadorService;

    @Operation(summary = "Lista todos jogadores", description = "Lista todos jogadores")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogadores listados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listarTodos")
    public List<JogadorDTO> listar() {
        return jogadorService.listarTodos();
    }
    @Operation(summary = "Relatorio de jogadores", description = "Traz uma lista com o relatorio de jogadores")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Relatorio listado"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/relatorio")
    public List<RelatorioJogadorDTO> listar(@RequestParam(required = false) Integer id) {
        return jogadorService.relatorioJogadores(id);
    }

    @Operation(summary = "Retornar um jogador", description = "Retorna um jogador pelo seu id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogador retornado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/jogadorLogado")
    public JogadorLogadoDTO buscar() throws NaoEncontradoException {
        return jogadorService.trazJogadorLogado();
    }

    @Operation(summary = "Edita um jogador", description = "Edita um jogador ja existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogador editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/atualiza")
    public JogadorDTO put(@Valid @RequestBody JogadorCreateDTO jogadorDTO) throws NaoEncontradoException, NomeExistenteException {
        return jogadorService.editar(jogadorDTO);
    }

    @Operation(summary = "Deleta jogador logado", description = "Deleta jogador logado")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogador deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/delete")
    public void delete() throws NaoEncontradoException {
        jogadorService.removerLoggedUser();
    }

    @Operation(summary = "desativar jogador", description = " desativa um jogador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogador desativado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/desativar/{id}")
    public void desativar(@PathVariable Integer id) throws NaoEncontradoException {
        jogadorService.desativar(id);
    }

    @Operation(summary = "ativar jogador", description = " ativa um jogador")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Jogador reativado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/ativar/{id}")
    public void ativar(@PathVariable Integer id) throws NaoEncontradoException {
        jogadorService.ativar(id);
    }
}
