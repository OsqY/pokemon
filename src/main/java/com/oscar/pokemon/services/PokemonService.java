package com.oscar.pokemon.services;

import com.oscar.pokemon.dto.PokemonDto;
import com.oscar.pokemon.dto.PokemonResponse;


public interface PokemonService {
    PokemonDto createPokemon(PokemonDto pokemonDto);
    PokemonResponse getAllPokemons(int pageNo, int pageSize);
    PokemonDto getPokemonById(int pokemonId);
    PokemonDto updatePokemon(PokemonDto pokemonDto, int pokemonId);
    void deletePokemon(int pokemonId);
}
