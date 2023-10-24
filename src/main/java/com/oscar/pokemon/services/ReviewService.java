package com.oscar.pokemon.services;

import com.oscar.pokemon.dto.ReviewDto;

import java.util.List;

public interface ReviewService {

    ReviewDto createReview(int pokemonId, ReviewDto review);
    List<ReviewDto> getReviewByPokemonId(int pokemonId);
    ReviewDto getReviewById(int reviewId, int pokemonId);
    ReviewDto updateReview(int reviewId, int pokemonId, ReviewDto review);
    void deleteReview(int reviewId, int pokemonId);
}
