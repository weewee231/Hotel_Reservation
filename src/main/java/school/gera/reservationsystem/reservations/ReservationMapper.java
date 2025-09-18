package school.gera.reservationsystem.reservations;

import org.springframework.stereotype.Component;

@Component
public class ReservationMapper {
    public Reservation toDamain(ReservationEntity reservation) {
        return new Reservation(
                reservation.getId(),
                reservation.getUserId(),
                reservation.getRoomId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getStatus()
        );
    }

    public ReservationEntity toEntity(Reservation reservation) {
        return new ReservationEntity(
                reservation.id(),
                reservation.userId(),
                reservation.roomId(),
                reservation.endDate(),
                reservation.startDate(),
                reservation.status()
        );
    }
}