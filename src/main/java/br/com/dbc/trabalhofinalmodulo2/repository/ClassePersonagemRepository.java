package br.com.dbc.trabalhofinalmodulo2.repository;

import br.com.dbc.trabalhofinalmodulo2.entities.ClassePersonagemEntity;
import br.com.dbc.trabalhofinalmodulo2.entities.PersonagemEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassePersonagemRepository extends JpaRepository<ClassePersonagemEntity, Integer > {

    ClassePersonagemEntity findByPersonagemEntities(PersonagemEntity personagem);



}

