package school.gera.reservationsystem.reservations;


import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

import java.time.LocalDate;

public record Reservation(
        @Null
        Long id,

        @NotNull
        Long userId,

        @NotNull
        Long roomId,

        @FutureOrPresent
        @NotNull
        LocalDate startDate,

        @FutureOrPresent
        LocalDate endDate,  // МЕНЯЕМ на LocalDate

        ReservationStatus status
) {}