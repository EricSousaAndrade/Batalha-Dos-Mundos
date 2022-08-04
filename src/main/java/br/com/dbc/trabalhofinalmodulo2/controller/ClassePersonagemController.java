package br.com.dbc.trabalhofinalmodulo2.controller;

import br.com.dbc.trabalhofinalmodulo2.dto.classe.ClassePersonagemDTO;
import br.com.dbc.trabalhofinalmodulo2.service.ClassePersonagemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/classePersonagem")
@RequiredArgsConstructor
public class ClassePersonagemController {

    private final ClassePersonagemService classePersonagemService;

    @Operation(summary = "Lista as classes dos personagens", description = "Lista todos as classes cadastradas")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "200", description = "Classe de personagens listadas com sucesso"),
                    @ApiResponse(responseCode = "403", description = "Você não tem permissão para acessar este recurso"),
                    @ApiResponse(responseCode = "500", description = "Foi gerada uma exceção")
            }
    )
    @GetMapping
    public List<ClassePersonagemDTO> listar() {
        return classePersonagemService.listarClassePersonagem();
    }
}
