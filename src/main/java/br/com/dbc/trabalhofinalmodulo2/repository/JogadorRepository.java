package br.com.dbc.trabalhofinalmodulo2.repository;

import br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioJogadorDTO;
import br.com.dbc.trabalhofinalmodulo2.entities.JogadorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JogadorRepository extends JpaRepository<JogadorEntity, Integer> {

    Optional<JogadorEntity> findByNomeJogador (String nome);

    @Query(value = " select new br.com.dbc.trabalhofinalmodulo2.dto.relatorios.RelatorioJogadorDTO (" +
            "       j.idJogador, " +
            "       j.nomeJogador, " +
            "       j.email," +
            "       j.personagems.size," +
            "       jp.classePersonagem.tipoClassePersonagem" +
            " )" +
            "    from jogador j" +
            "    left join j.personagems jp" +
            "    where (:idJogador is null OR j.idJogador = :idJogador)")
    List<RelatorioJogadorDTO> listJogadorRelatorio(@Param("idJogador") Integer idJogador);

}
