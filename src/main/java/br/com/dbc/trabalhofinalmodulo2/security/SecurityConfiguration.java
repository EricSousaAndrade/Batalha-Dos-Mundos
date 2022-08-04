package br.com.dbc.trabalhofinalmodulo2.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.scrypt.SCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final TokenService tokenService;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable().and()
                .cors().and()
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                        authz.antMatchers("/", "/auth", "/auth/cadastro").permitAll()
                                .antMatchers("/batalha/atacar").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/batalha/iniciar").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/batalha/defender").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/batalha/fugir").hasAnyRole("PLAYER","STAFF")

                                .antMatchers("/jogador/jogadorLogado").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/jogador/create").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/jogador/atualiza").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/jogador/delete").hasAnyRole("PLAYER","STAFF")

                                .antMatchers("/personagem/adicionarPersonagem").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/personagem/novoPersonagemClasse").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/personagem/list").hasAnyRole("PLAYER","STAFF")
                                .antMatchers("/personagem/editar").hasAnyRole("PLAYER","STAFF")

                                .antMatchers("/**").hasAnyRole("STAFF")
                                .anyRequest().authenticated()

                );
        http.addFilterBefore(new TokenAuthenticationFilter(tokenService), UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().antMatchers("/v3/api-docs",
                "/v3/api-docs/**",
                "/swagger-resources/**",
                "/swagger-ui/**");
    }

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedMethods("*")
                        .exposedHeaders("Authorization");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new SCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
            throws Exception {
        return config.getAuthenticationManager();
    }
}
