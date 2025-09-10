package school.gera.reservationsystem;

import java.sql.Time;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record ErrorResponseDto(
        String messege,

        String detailedMessage,

        LocalDateTime errorTime
) {
}
