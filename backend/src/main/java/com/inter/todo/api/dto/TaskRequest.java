package com.inter.todo.api.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskRequest(
        @NotBlank(message = "title is required")
        @Size(max = 255, message = "title must have at most 255 characters")
        String title,
        @Size(max = 4000, message = "description must have at most 4000 characters")
        String description
) {
}
