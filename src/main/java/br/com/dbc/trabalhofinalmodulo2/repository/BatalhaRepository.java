package br.com.dbc.trabalhofinalmodulo2.repository;


import br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioBatalhaDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.BatalhaEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BatalhaRepository extends JpaRepository<BatalhaEntity, Integer> {

    Page<BatalhaEntity> findAllBy(Pageable pageable);

    @Query(value = " select new br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioBatalhaDTO (" +
            "       bat.idBatalha, " +
            "       bat.roundBatalha, " +
            "       bos.nomeBoss," +
            "       bos.vidaBoss," +
            "       cen.tipoReino," +
            "       per.nomePersonagem," +
            "       clas.tipoClassePersonagem," +
            "       clas.vidaPersonagem " +
            " )" +
            "    from batalha bat" +
            "    left join bat.cenarioEntity cen" +
            "    left join bat.bossEntity bos" +
            "    left join bat.personagemEntity per" +
            "    left join per.classePersonagem clas" +
            "    where (:idBatalha is null OR bat.idBatalha = :idBatalha)")
    List<RelatorioBatalhaDTO> listRelatorioDTO(@Param("idBatalha") Integer idBatalha);
}






