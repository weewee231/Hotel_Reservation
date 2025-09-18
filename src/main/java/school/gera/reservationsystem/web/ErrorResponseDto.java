package school.gera.reservationsystem.web;

import java.time.LocalDateTime;

public record ErrorResponseDto(
        String messege,

        String detailedMessage,

        LocalDateTime errorTime
) {
}
