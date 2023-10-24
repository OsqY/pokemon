package com.oscar.pokemon.services.impl;

import com.oscar.pokemon.dto.PokemonDto;
import com.oscar.pokemon.dto.PokemonResponse;
import com.oscar.pokemon.exceptions.PokemonNotFoundException;
import com.oscar.pokemon.models.Pokemon;
import com.oscar.pokemon.repositories.PokemonRepository;
import com.oscar.pokemon.services.PokemonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PokemonServiceImpl implements PokemonService {

    private final PokemonRepository pokemonRepository;

    @Autowired
    public PokemonServiceImpl(PokemonRepository pokemonRepository) {
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public PokemonDto createPokemon(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon newPokemon = pokemonRepository.save(pokemon);

        PokemonDto pokemonResponse = new PokemonDto();
        pokemonResponse.setId(newPokemon.getId());
        pokemonResponse.setName(newPokemon.getName());
        pokemonResponse.setType(newPokemon.getType());

        return pokemonResponse;
    }

    @Override
    public PokemonResponse getAllPokemons(int pageNo, int pageSize) {
        PageRequest pageable = PageRequest.of(pageSize, pageSize);
        Page<Pokemon> pokemons = pokemonRepository.findAll(pageable);
        List<Pokemon> pokemonList = pokemons.getContent();
        List<PokemonDto> content = pokemonList.stream().map(this::mapToDto).collect(Collectors.toList());

        PokemonResponse pokemonResponse = new PokemonResponse();
        createPokemonResponse(pokemonResponse, content, pokemons);
        return pokemonResponse;
    }

    private static void createPokemonResponse(PokemonResponse pokemonResponse,
                                              List<PokemonDto> content, Page<Pokemon> pokemons) {
        pokemonResponse.setContent(content);
        pokemonResponse.setPageNo(pokemons.getNumber());
        pokemonResponse.setPageSize(pokemons.getSize());
        pokemonResponse.setTotalElements(pokemons.getTotalElements());
        pokemonResponse.setTotalPages(pokemons.getTotalPages());
        pokemonResponse.setLast(pokemons.isLast());

    }

    @Override
    public PokemonDto getPokemonById(int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).
                orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        return mapToDto(pokemon);
    }

    @Override
    public PokemonDto updatePokemon(PokemonDto pokemonDto, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon couldn't be updated"));

        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        Pokemon updatedPokemon = pokemonRepository.save(pokemon);

        return mapToDto(updatedPokemon);
    }

    @Override
    public void deletePokemon(int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId).orElseThrow(
                () -> new PokemonNotFoundException("Pokemon not found"));
        pokemonRepository.delete(pokemon);
    }


    private PokemonDto mapToDto(Pokemon pokemon) {
        PokemonDto pokemonDto = new PokemonDto();
        pokemonDto.setId(pokemon.getId());
        pokemonDto.setName(pokemon.getName());
        pokemonDto.setType(pokemon.getType());

        return pokemonDto;
    }

    private Pokemon mapToEntity(PokemonDto pokemonDto) {
        Pokemon pokemon = new Pokemon();
        pokemon.setName(pokemonDto.getName());
        pokemon.setType(pokemonDto.getType());

        return pokemon;
    }
}
