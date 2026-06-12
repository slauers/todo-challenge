package com.inter.todo.api.dto;

import java.time.Instant;
import java.util.List;

/** Corpo padrão de erro da API. */
public record ApiError(
        Instant timestamp,
        int status,
        String error,
        String message,
        String path,
        List<String> details
) {

    public static ApiError of(int status, String error, String message, String path) {
        return new ApiError(Instant.now(), status, error, message, path, List.of());
    }

    public static ApiError of(int status, String error, String message, String path, List<String> details) {
        return new ApiError(Instant.now(), status, error, message, path, details);
    }
}
