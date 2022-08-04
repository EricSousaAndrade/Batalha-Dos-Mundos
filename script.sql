
--Tabela JOGADOR
create table jogador  (
                         id_jogador numeric not null,
                         nome_jogador text not null,
                         senha text not null,
                         email text not null,
                         primary key (id_jogador)
);

--Sequence JOGADOR
create sequence seq_jogador
    start 1
     	increment 1;

--Tabela CLASSE_PERSONAGEM
create table classe_personagem (
                                 id_classe_personagem numeric not null,
                                 tipo_classe_personagem text not null,
                                 vida_personagem numeric not null,
                                 defesa_personagem numeric not null,
                               	 ataque_personagem numeric not null,
                                 primary key (id_classe_personagem)
);


--Tabela PERSONAGEM
create table personagem (
                           id_personagem numeric not null,
                           id_jogador numeric not null,
                           id_classe_personagem numeric not null,
                           nome_personagem text not null,
                           primary key (id_personagem)
);


--FK na tabela PERSONAGEM -> JOGADOR
ALTER TABLE personagem
    ADD CONSTRAINT fk_jogador FOREIGN KEY (id_jogador)
        REFERENCES jogador(id_jogador) on DELETE cascade;

--FK na tabela PERSONAGEM -> CLASSE_PERSONAGEM
ALTER TABLE personagem
    ADD CONSTRAINT fk_classe_personagem FOREIGN KEY (id_classe_personagem)
        REFERENCES classe_personagem(id_classe_personagem) ON DELETE CASCADE;

--Sequence PERSONAGEM

CREATE SEQUENCE seq_personagem
    START  1
     	INCREMENT 1;


--Sequence CLASSE_PERSONAGEM
CREATE SEQUENCE  seq_classe_personagem
    START WITH     1
    INCREMENT BY   1;




--Tabela CENARIO
create table cenario (
                        id_cenario numeric not null,
                        nome_cenario text not null,
                        horario date not null,
                        tipo_reino text not null,
                       	primary key (id_cenario)
);
--Sequence CENARIO
CREATE SEQUENCE seq_cenario
    START WITH     1
    INCREMENT BY   1;




--Tabela BOSS
create table boss(
                     id_boss numeric not null,
                     nome_boss text not null,
                     vida_boss numeric not null,
                     defesa_boss numeric not null,
                     ataque_boss numeric not null,
                     primary key (id_boss)
);

--Sequence BOSS
CREATE SEQUENCE seq_boss
    START 1
     	INCREMENT 1;

--Tabela BATALHA
create table batalha (
                        id_batalha numeric not null,
                        id_cenario numeric not null,
                        id_personagem numeric not null,
                        id_boss numeric not null,
                        round_batalha numeric not null,
                        status text not null,
                        PRIMARY KEY (id_batalha)
);

--FKS na tabela BATALHA

ALTER TABLE batalha
    ADD CONSTRAINT fk_cenario FOREIGN KEY (id_cenario)
        REFERENCES cenario(id_cenario) on delete CASCADE;

ALTER TABLE batalha
    ADD CONSTRAINT fk_boss FOREIGN KEY (id_boss)
        REFERENCES boss(id_boss) on delete CASCADE;

ALTER TABLE batalha
    ADD CONSTRAINT fk_personagem FOREIGN KEY (id_personagem)
        REFERENCES personagem(id_personagem) on delete CASCADE;

--Sequence BATALHA
CREATE SEQUENCE seq_batalha
    START 1
     	INCREMENT 1;

INSERT INTO jogador  (id_jogador, nome_jogador  ,senha , email)
VALUES (nextval('seq_jogador'), 'Joao', 'Senha', 'aaa@gmail.com');

INSERT INTO classe_personagem  (id_classe_personagem, tipo_classe_personagem , vida_personagem , defesa_personagem , ataque_personagem)
VALUES (nextval('seq_classe_personagem'), 'Mago', 150, 30, 40);

INSERT INTO personagem  (id_personagem, id_jogador, id_classe_personagem, nome_personagem)
VALUES (nextval('seq_personagem'), 1, 1, 'PersonagemJoao');

INSERT INTO boss (id_boss, nome_boss , vida_boss , defesa_boss , ataque_boss)
VALUES (nextval('seq_boss'), 'BossSombrio', 300, 80, 40);

INSERT INTO cenario  (id_cenario, nome_cenario , horario , tipo_reino)
VALUES (nextval('seq_cenario'), 'ReinoSombrio',  CURRENT_DATE, '1');

INSERT INTO batalha  (id_batalha, id_cenario  , id_personagem  , id_boss  , round_batalha , status)
VALUES (nextval('seq_batalha'), 1, 1, 1, 1, 'Vitoria');