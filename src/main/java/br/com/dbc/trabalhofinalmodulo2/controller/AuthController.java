package br.com.dbc.trabalhofinalmodulo2.controller;

import br.com.dbc.trabalhofinalmodulo2.dto.jogador.LoginCreateDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.jogador.LoginDTO;
import br.com.dbc.trabalhofinalmodulo2.dto.jogador.LoginRetornaDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import br.com.dbc.trabalhofinalmodulo2.exceptions.NaoEncontradoException;
import br.com.dbc.trabalhofinalmodulo2.security.TokenService;
import br.com.dbc.trabalhofinalmodulo2.service.JogadorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@Validated
@Slf4j
@RequiredArgsConstructor
public class AuthController {

    private final JogadorService jogadorService;

    private final AuthenticationManager authenticationManager;

    private final TokenService tokenService;

    @PostMapping
    public String createLogin (LoginDTO loginRetornaDTO) throws NaoEncontradoException {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(
                        loginRetornaDTO.getNomeJogador(),
                        loginRetornaDTO.getSenha()
                );

        try {
            Authentication authentication = authenticationManager
                    .authenticate(usernamePasswordAuthenticationToken);
            log.info("Autenticado com sucesso!");
            Object jogadorLogado = authentication.getPrincipal();
            JogadorEntity jogadorEntity = (JogadorEntity) jogadorLogado;

            return tokenService.getToken(jogadorEntity);
        } catch (Exception ex){
            throw new NaoEncontradoException(ex.getMessage());
        }
    }

    @PostMapping("/cadastro")
    public ResponseEntity<LoginRetornaDTO> cadastrar(@RequestBody @Valid LoginCreateDTO loginDTO){
        return ResponseEntity.ok(jogadorService.criarUsuario(loginDTO));
    }

}
