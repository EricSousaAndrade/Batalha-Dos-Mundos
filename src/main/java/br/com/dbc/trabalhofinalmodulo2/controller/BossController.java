package br.com.dbc.trabalhofinalmodulo2.controller;

import br.com.dbc.trabalhofinalmodulo2.exceptions.BancoDeDadosException;
import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.boss.BossDTO;
import br.com.dbc.trabalhofinalmodulo2.service.BossService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;

@RestController
@RequestMapping("/boss")
@RequiredArgsConstructor
public class BossController {

    private final BossService bossService;

    @Operation(summary = "Cria um boss", description = "Cria um novo boss")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Boss criado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping("/adiciona")
    public ResponseEntity<BossDTO> create(@Valid @RequestBody BossCreateDTO bossCreateDTO) throws BancoDeDadosException, SQLException {
        return ResponseEntity.ok(bossService.adicionar(bossCreateDTO));
    }

    @Operation(summary = "Lista os Bosses", description = "Lista todos os Bosses")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Lista com todos os bosses"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/listar")
    public List<BossDTO> list() throws BancoDeDadosException, SQLException {
        return bossService.listar();
    }

    @Operation(summary = "Edita um boss", description = "Edita um boss ja existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Boss editado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PutMapping("/editar")
    public BossDTO editar(@Valid @RequestBody BossCreateDTO bossCreateDTO, @RequestParam int id) throws BancoDeDadosException, Exception {
        return bossService.editar(bossCreateDTO, id);
    }

    @Operation(summary = "Deleta um boss", description = "Deleta um boss ja existente")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Boss deletado com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/deletar")
    public void editar(@RequestParam int id) throws BancoDeDadosException, Exception {
        bossService.remover(id);
    }

}
