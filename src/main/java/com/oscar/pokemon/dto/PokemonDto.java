package com.oscar.pokemon.dto;

import com.oscar.pokemon.models.PokemonTypes;
import lombok.Data;

@Data
public class PokemonDto {
    private int id;
    private String name;
    private PokemonTypes type;
}
