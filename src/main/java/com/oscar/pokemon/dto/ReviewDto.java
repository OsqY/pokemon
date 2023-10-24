package com.oscar.pokemon.dto;

import lombok.Data;

@Data
public class ReviewDto {
    private int id;
    private String title;
    private String content;
    private int rating;

}
