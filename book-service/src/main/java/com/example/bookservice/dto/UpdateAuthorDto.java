package com.example.bookservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateAuthorDto {

    @NotBlank
    private String name;
}
