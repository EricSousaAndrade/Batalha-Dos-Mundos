package br.com.dbc.trabalhofinalmodulo2.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;


@Setter
@Getter
@Entity(name = "cenario")
public class CenarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "cenario_seq")
    @SequenceGenerator(name = "cenario_seq", sequenceName = "seq_cenario", allocationSize = 1)
    @Column(name = "id_cenario", nullable = false)
    private Integer idCenario;

    @Column(name = "nome_cenario", nullable = false)
    private String nomeCenario;

    @Column(name = "horario", nullable = false)
    private Date horario;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_reino", nullable = false)
    private TipoReino tipoReino;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY,
            mappedBy = "cenarioEntity",
            cascade = CascadeType.ALL)
    private Set<BatalhaEntity> batalhas;

}
