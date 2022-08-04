package br.com.dbc.trabalhofinalmodulo2.security;

import br.com.dbc.trabalhofinalmodulo2.entities.CargoEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private String expiration;

    private static final String KEY_CARGOS = "roles";

    public String getToken(JogadorEntity jogadorEntity) {
        Date now = new Date();
        Date exp = new Date(now.getTime() + Long.parseLong(expiration));

        List<String> listaDeCargos = jogadorEntity.getCargos().stream()
                .map(CargoEntity::getNome)
                .toList();

        String token = Jwts.builder()
                .setIssuer("battle-of-world")
                .claim(Claims.ID, jogadorEntity.getIdJogador())
                .claim(KEY_CARGOS, listaDeCargos)
                .setIssuedAt(now)
                .setExpiration(exp)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

        return TokenAuthenticationFilter.BEARER + token;
    }

    public UsernamePasswordAuthenticationToken isValid(String token) {
        if (token == null) {
            return null;
        }

        Claims body = Jwts.parser()
                .setSigningKey(secret)
                .parseClaimsJws(token)
                .getBody();

        Integer idUsuario = body.get(Claims.ID, Integer.class);
        // consulta no banco

        if (idUsuario != null){
            List<String> cargos = body.get(KEY_CARGOS, List.class);

            List<SimpleGrantedAuthority> cargosGrantedAuthority = cargos.stream()
                    .map(SimpleGrantedAuthority::new)
                    .toList();

            return new UsernamePasswordAuthenticationToken(idUsuario, null, cargosGrantedAuthority);
        }
        return null;
    }

}
