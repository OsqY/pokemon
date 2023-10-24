package com.oscar.pokemon.controllers;

import com.oscar.pokemon.dto.ReviewDto;
import com.oscar.pokemon.services.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
public class ReviewController {

    ReviewService reviewService;

    @Autowired
    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping("pokemon/{pokemonId}/reviews")
    public ResponseEntity<ReviewDto> createReview(@PathVariable(value = "pokemonId") int pokemonId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.createReview(pokemonId, reviewDto), HttpStatus.CREATED);
    }

    @GetMapping("pokemon/{pokemonid}/reviews")
    public List<ReviewDto> getReviewsByPokemonId(@PathVariable(value = "pokemonid") int pokemonId) {
        return reviewService.getReviewByPokemonId(pokemonId);
    }

    @GetMapping("pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> getReviewById(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "reviewId") int reviewId) {
        return new ResponseEntity<>(reviewService.getReviewById(reviewId, pokemonId), HttpStatus.OK);
    }

    @PutMapping("pokemon/{pokemonId}/reviews/{reviewId}")
    public ResponseEntity<ReviewDto> updateReview(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "reviewId") int reviewId, @RequestBody ReviewDto reviewDto) {
        return new ResponseEntity<>(reviewService.updateReview(reviewId, pokemonId, reviewDto), HttpStatus.OK);
    }
    @DeleteMapping("pokemon/{pokemonId}/delete/{reviewId}")
    public ResponseEntity<String> deleteReview(@PathVariable(value = "pokemonId") int pokemonId, @PathVariable(value = "reviewId") int reviewId) {
        reviewService.deleteReview(reviewId, pokemonId);
        return new ResponseEntity<>("Review deleted successfully", HttpStatus.OK);
    }
}
