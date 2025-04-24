package com.example.bookservice.dto;

import lombok.Data;

@Data
public class BookDto {

    private Long id;
    private String title;
    private String authorName;
    private Boolean isLending;
}
