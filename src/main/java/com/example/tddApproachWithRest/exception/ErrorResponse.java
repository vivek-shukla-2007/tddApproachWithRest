package com.example.tddApproachWithRest.exception;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorResponse (
        LocalDateTime timestamp,
        int status,
        String error,
        String path,
        Map<String, String> details
) {
}
