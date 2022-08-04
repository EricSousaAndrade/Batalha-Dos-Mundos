package br.com.dbc.trabalhofinalmodulo2.repository;

import br.com.dbc.trabalhofinalmodulo2.entities.CenarioEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.TipoReino;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface CenarioRepository extends JpaRepository<CenarioEntity, Integer> {


    @Query("    select c" +
        "       from cenario c" +
        "       where (:tipoReino is null OR c.tipoReino = :tipoReino)")
    List<CenarioEntity> getCenarioEntitiesByTipoReino(@Param("tipoReino")TipoReino tipoReino);
}
