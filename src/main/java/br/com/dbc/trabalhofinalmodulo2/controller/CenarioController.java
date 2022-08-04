package br.com.dbc.trabalhofinalmodulo2.controller;

import br.com.dbc.trabalhofinalmodulo2.entities.TipoReino;
import br.com.dbc.trabalhofinalmodulo2.exceptions.BancoDeDadosException;
import br.com.dbc.trabalhofinalmodulo2.dto.cenario.CenarioCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.cenario.CenarioDTO;
import br.com.dbc.trabalhofinalmodulo2.service.CenarioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.sql.SQLException;
import java.util.List;


@RestController
@RequestMapping("/cenario")
@RequiredArgsConstructor
public class CenarioController {

    private final CenarioService cenarioService;


    //=================================================================================================================

    @Operation(summary = "Listar os cenários",
               description = "Lista os cenários cadastrados")

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cenários retornados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )

    @GetMapping("/listar")
    public List<CenarioDTO> list() throws BancoDeDadosException, SQLException {
        return cenarioService.listar();
    }

    //=================================================================================================================

    @Operation(summary = "Inserir novo cenário",
               description = "Insere novo cenário")

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Novo cenário inserido com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @PostMapping
    public ResponseEntity<CenarioCreateDTO> post(@Valid @RequestBody CenarioCreateDTO cenarioDTO) throws BancoDeDadosException {

        return ResponseEntity.ok(cenarioService.adicionar(cenarioDTO));
    }

    //=================================================================================================================


    @PutMapping("/editarCenario")
    public CenarioDTO editar(@Valid @RequestBody CenarioCreateDTO cenarioDTO, @RequestParam int id) throws BancoDeDadosException, Exception {
        return cenarioService.editar(cenarioDTO, id);
    }

    //=================================================================================================================

    @Operation(summary = "Excluir cenário",
            description = "Exclui cenário existente")

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cenário excluído com sucesso!"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @DeleteMapping("/{idCenario}")
    public void remover(@PathVariable("idCenario") Integer id) throws BancoDeDadosException, Exception {
        cenarioService.remover(id);
    }
    //=================================================================================================================

    @Operation(summary = "Listar os cenários pelo tipo de reino",
            description = "Lista os cenários cadastrados pelo tipo de reino")

    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Cenários retornados com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping("/porReino")
    public ResponseEntity<List<CenarioDTO>> getCenarioPorTipoReino(TipoReino tipoReino) {
        List<CenarioDTO> cenarioDTOS = cenarioService.listCenarioPorReino(tipoReino);
        return new ResponseEntity<>(cenarioDTOS, HttpStatus.OK);
    }
}



