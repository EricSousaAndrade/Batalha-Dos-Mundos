package br.com.dbc.trabalhofinalmodulo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "classe_personagem")
public class ClassePersonagemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "CLASSE_PERSONAGEM_SEQ")
    @SequenceGenerator(name = "CLASSE_PERSONAGEM_SEQ", sequenceName = "SEQ_CLASSE_PERSONAGEM", allocationSize = 1)
    @Column(name = "id_classe_personagem", nullable = false)
    private Integer id;

    @JsonIgnore
    @OneToMany(mappedBy = "classePersonagem",
            fetch = FetchType.LAZY,
            cascade = CascadeType.ALL,
            orphanRemoval = true)
    private Set<PersonagemEntity> personagemEntities;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_classe_personagem", nullable = false)
    private TipoClassePersonagem tipoClassePersonagem;

    @Column(name = "vida_personagem", nullable = false)
    private Double vidaPersonagem;

    @Column(name = "defesa_personagem", nullable = false)
    private Double defesaPersonagem;

    @Column(name = "ataque_personagem", nullable = false)
    private Double ataquePersonagem;

}
