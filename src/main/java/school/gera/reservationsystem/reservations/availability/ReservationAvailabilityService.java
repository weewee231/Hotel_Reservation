package school.gera.reservationsystem.reservations.availability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import school.gera.reservationsystem.reservations.ReservationRepository;
import school.gera.reservationsystem.reservations.ReservationStatus;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReservationAvailabilityService {


    private static final Logger logger = LoggerFactory.getLogger(ReservationAvailabilityService.class);
    private ReservationRepository repository;

    public ReservationAvailabilityService(ReservationRepository repository) {
        this.repository = repository;
    }

    public boolean isResevationAvailable(
            long roomId,
            LocalDate startDate,
            LocalDate endDate
    ) {
        if (!endDate.isAfter(startDate)) {
        throw new IllegalArgumentException("Start date must be 1 day earlier than end date");
        }


        List<Long> conflictingIds = repository.findConflictReservationIds(
                roomId,
                startDate,
                endDate,
                ReservationStatus.APPROVED
        );

        if (conflictingIds.isEmpty()) {
            return true;
        }
        logger.info("Conflict with ids = {}", conflictingIds);
        return false;
    }

}
