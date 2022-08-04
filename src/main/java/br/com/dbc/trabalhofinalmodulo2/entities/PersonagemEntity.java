package br.com.dbc.trabalhofinalmodulo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Setter
@Getter
@Entity(name = "personagem")
public class PersonagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSONAGEM_SEQ")
    @SequenceGenerator(name = "PERSONAGEM_SEQ", sequenceName = "seq_personagem", allocationSize = 1)
    @Column(name = "id_personagem", nullable = false)
    private Integer id;

    @Column(name = "id_jogador",  updatable = false, insertable = false)
    private Integer idJogador;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_jogador", nullable = false)
    private JogadorEntity jogadorEntity;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "personagemEntity", cascade = CascadeType.ALL)
    private Set<BatalhaEntity> batalhas;

    @Column(name = "nome_personagem", nullable = false)
    private String nomePersonagem;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_classe_personagem")
    private ClassePersonagemEntity classePersonagem;



}
