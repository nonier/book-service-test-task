package com.example.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UpdateBookDto {

    @NotBlank
    String title;
    @NotNull
    Long authorId;
}
