package school.gera.reservationsystem.reservations.availability;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record CheckAvailabilityResponse(

        String massage,

        AvailabilityStatus status

) {
}
