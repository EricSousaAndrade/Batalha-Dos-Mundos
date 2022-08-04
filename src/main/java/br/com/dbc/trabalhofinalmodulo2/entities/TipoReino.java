package br.com.dbc.trabalhofinalmodulo2.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public enum TipoReino {


    LUZ("LUZ", 1),
    SOMBRAS("SOMBRAS", 2),
    LUA("LUA", 3),
    MARTE("MARTE", 4);

    private String tipo;
    private int id;




}
