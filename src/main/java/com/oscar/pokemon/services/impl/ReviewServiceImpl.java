package com.oscar.pokemon.services.impl;

import com.oscar.pokemon.dto.ReviewDto;
import com.oscar.pokemon.exceptions.PokemonNotFoundException;
import com.oscar.pokemon.exceptions.ReviewNotFoundException;
import com.oscar.pokemon.models.Pokemon;
import com.oscar.pokemon.models.Review;
import com.oscar.pokemon.repositories.PokemonRepository;
import com.oscar.pokemon.repositories.ReviewRepository;
import com.oscar.pokemon.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    private final ReviewRepository reviewRepository;
    private final PokemonRepository pokemonRepository;

    @Autowired
    public ReviewServiceImpl(ReviewRepository reviewRepository, PokemonRepository pokemonRepository) {
        this.reviewRepository = reviewRepository;
        this.pokemonRepository = pokemonRepository;
    }

    @Override
    public ReviewDto createReview(int pokemonId, ReviewDto reviewDto) {
        Review review = mapToEntity(reviewDto);

        Pokemon pokemon = pokemonRepository.findById(pokemonId).
                orElseThrow(() -> new RuntimeException("Pokemon not found"));

        review.setPokemon(pokemon);

        Review savedReview = reviewRepository.save(review);
        return mapToDto(savedReview);
    }

    @Override
    public List<ReviewDto> getReviewByPokemonId(int pokemonId) {
        List<Review> reviews = reviewRepository.findByPokemonId(pokemonId);

        return reviews.stream().map(this::mapToDto).toList();
    }

    @Override
    public ReviewDto getReviewById(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new RuntimeException("Pokemon not found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found to the associated pokemon"));
        if (review.getPokemon().getId() != pokemonId) {
            throw new ReviewNotFoundException("Review doesn't belong to a pokemon");
        }
        return mapToDto(review);
    }

    @Override
    public ReviewDto updateReview(int reviewId, int pokemonId, ReviewDto reviewDto) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found to the associated pokemon"));

        if (review.getPokemon().getId() != pokemonId) {
            throw new ReviewNotFoundException("Review doesn't belong to a pokemon");
        }
        review.setContent(reviewDto.getContent());
        review.setTitle(reviewDto.getTitle());
        review.setRating(reviewDto.getRating());
        Review updatedReview = reviewRepository.save(review);
        return mapToDto(updatedReview);
    }

    @Override
    public void deleteReview(int reviewId, int pokemonId) {
        Pokemon pokemon = pokemonRepository.findById(pokemonId)
                .orElseThrow(() -> new PokemonNotFoundException("Pokemon not found"));
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(() -> new ReviewNotFoundException("Review not found"));
        if (pokemon.getId() != pokemonId) {
            throw new ReviewNotFoundException("Pokemon doesn't belongs to review");
        }
        reviewRepository.delete(review);

    }

    private ReviewDto mapToDto(Review review) {
        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(review.getId());
        reviewDto.setTitle(review.getTitle());
        reviewDto.setContent(review.getContent());
        reviewDto.setRating(review.getRating());

        return reviewDto;
    }

    private Review mapToEntity(ReviewDto reviewDto) {
        Review review = new Review();
        review.setTitle(reviewDto.getTitle());
        review.setContent(reviewDto.getContent());
        review.setRating(reviewDto.getRating());

        return review;
    }


}
