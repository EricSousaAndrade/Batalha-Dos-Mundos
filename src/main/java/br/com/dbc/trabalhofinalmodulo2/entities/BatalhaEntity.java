package br.com.dbc.trabalhofinalmodulo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity(name = "batalha")
@Table(name = "batalha", schema = "vem_ser")
public class BatalhaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "BATALHA_SEQ")
    @SequenceGenerator(name = "BATALHA_SEQ", sequenceName = "seq_batalha", allocationSize = 1)
    @Column(name = "id_batalha", nullable = false)
    private Integer idBatalha;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_cenario", referencedColumnName = "id_cenario")
    private CenarioEntity cenarioEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_personagem", referencedColumnName = "id_personagem")
    private PersonagemEntity personagemEntity;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_boss", referencedColumnName = "ID_BOSS")
    private BossEntity bossEntity;

    @Column(name = "id_boss",  updatable = false, insertable = false)
    private Integer idBoss;

    @Column(name = "id_cenario",  updatable = false, insertable = false)
    private Integer idCenario;

    @Column(name = "id_personagem",  updatable = false, insertable = false)
    private Integer idPersonagem;

    @Column(name = "round_batalha", nullable = false)
    private Integer roundBatalha;

    @Column(name = "status", nullable = false)
    private String status;


}
