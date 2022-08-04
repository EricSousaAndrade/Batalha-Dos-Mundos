package br.com.dbc.trabalhofinalmodulo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;

@Getter
@Setter
@Entity(name = "jogador")
public class JogadorEntity implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "jogador_seq")
    @SequenceGenerator(name = "jogador_seq", sequenceName = "seq_jogador", allocationSize = 1)
    @Column(name = "id_jogador", nullable = false)
    private Integer idJogador;

    @Column(name = "nome_jogador", nullable = false)
    private String nomeJogador;

    @Column(name = "senha", nullable = false)
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "jogadorEntity", fetch = FetchType.LAZY)
    private Set<PersonagemEntity> personagems;

    @Column(name = "email", nullable = false)
    private String email;

    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "jogador_cargo",
            joinColumns = @JoinColumn(name = "id_jogador"),
            inverseJoinColumns = @JoinColumn(name = "id_cargo"))
    private Set<CargoEntity> cargos;

    @Column(name = "enable")
    private Boolean enable;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return cargos;
    }

    @Override
    public String getPassword() {
        return senha;
    }

    @Override
    public String getUsername() {
        return nomeJogador;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        if(enable == null){
            return false;
        }
        return enable;
    }
}
