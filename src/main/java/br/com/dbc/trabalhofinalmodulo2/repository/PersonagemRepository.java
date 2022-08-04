package br.com.dbc.trabalhofinalmodulo2.repository;

import br.com.dbc.trabalhofinalmodulo2.entities.PersonagemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PersonagemRepository extends JpaRepository<PersonagemEntity, Integer> {

    Optional<PersonagemEntity> findByNomePersonagem (String nome);

    @Query("SELECT p FROM personagem p WHERE p.idJogador = ?1")
    List<PersonagemEntity> findAllByIdJogador(Integer idJogador);

    Page<PersonagemEntity> findAll(Pageable pageable);

}
