package br.com.dbc.trabalhofinalmodulo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity(name = "boss")
public class BossEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "boss_seq")
    @SequenceGenerator(name = "boss_seq", sequenceName = "seq_boss", allocationSize = 1)
    @Column(name = "id_boss", nullable = false)
    private Integer idBoss;

    @Column(name = "nome_boss", nullable = false)
    private String nomeBoss;

    @Column(name = "vida_boss", nullable = false)
    private Double vidaBoss;

    @Column(name = "defesa_boss", nullable = false)
    private Double defesaBoss;

    @Column(name = "ataque_boss", nullable = false)
    private Double ataqueBoss;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "bossEntity",
            cascade = CascadeType.ALL)
    private Set<BatalhaEntity> batalhas;

}
