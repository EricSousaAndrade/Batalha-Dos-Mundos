package br.com.dbc.trabalhofinalmodulo2.security;

import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import br.com.dbc.trabalhofinalmodulo2.service.JogadorService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class AuthenticationService implements UserDetailsService {
    private final JogadorService jogadorService;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<JogadorEntity> usuarioOptional = jogadorService.findByLogin(username);
        return usuarioOptional
                .orElseThrow(() -> new UsernameNotFoundException("Usuario inválido"));
    }
}
