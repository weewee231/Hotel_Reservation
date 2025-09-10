package school.gera.reservationsystem;


import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.chrono.ChronoLocalDate;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    private final ReservationRepository repository;

    public ReservationService(ReservationRepository repository) {
        this.repository = repository;

    }

    public Reservation getReservationById(Long id) {


       ReservationEntity reservationEntity = repository.findById(id)
        .orElseThrow(() -> new EntityNotFoundException("Not found reservation by id = " + id));



        return toDamainReservation(reservationEntity);
    }

    public List<Reservation> findAllReservation() {

        List<ReservationEntity> allEntities = repository.findAll(); //возвращает список всех сущностей которые в базе данных

        // для каждой сущности сделали маппинг в резерватион
        // маппинг в бизнес логике


        return allEntities.stream() // для каждой сущности сделали маппинг в резерватион
                .map(this::toDamainReservation).toList();
    }

    public Reservation createReservation(Reservation reservationToCreate) {

        if (reservationToCreate.status() !=null) {
            throw new IllegalArgumentException("Status should be empty");
        }

        if (!reservationToCreate.endDate().isAfter(reservationToCreate.startDate())) {
            throw new IllegalArgumentException("start date must be 1 day earlier than end date");
        }

        var entityToSave = new ReservationEntity(
                        null,
                        reservationToCreate.userId(),
                        reservationToCreate.roomId(),
                reservationToCreate.endDate(),
                reservationToCreate.startDate(),
                        ReservationStatus.PENDING

                );
        var savedEntity = repository.save(entityToSave);
        return toDamainReservation(savedEntity);
    }

    public Reservation updateReservation(Long id, Reservation reservationToUpdate) {

        var reservationEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found reservation by id = " + id));

        if (reservationEntity.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("Cannot modify reservation: status= " + reservationEntity.getStatus());
        }

        var reservationToSave = new ReservationEntity(
                        reservationEntity.getId(),
                        reservationToUpdate.userId(),
                        reservationToUpdate.roomId(),
                reservationToUpdate.endDate(),
                reservationToUpdate.startDate(),
                        ReservationStatus.PENDING

                );

        var updatedReservation = repository.save(reservationToSave);

        return toDamainReservation((ReservationEntity) updatedReservation);
    }


    @Transactional
    public void cancelReservation(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("Not found reservation by id = " + id);
        }

        repository.setStatus(id, ReservationStatus.CANCELLED);
        logger.info("Successfully cancelled: id {}", id);

    }

    public Reservation approveReservation(Long id) {

        var reservationEntity = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Not found reservation by id = " + id));


        if (reservationEntity.getStatus() != ReservationStatus.PENDING) {
            throw new IllegalArgumentException("Cannot approve reservation: status= " + reservationEntity.getStatus());
        }

        var isConflict = isResevationConflict(reservationEntity);

        if (isConflict) {
            throw new IllegalArgumentException("Cannot approve reservation because of conflict");
        }

        reservationEntity.setStatus(ReservationStatus.APPROVED);
        repository.save(reservationEntity);

        return toDamainReservation(reservationEntity);

    }
    private boolean isResevationConflict(ReservationEntity reservation) {

        var allReservations = repository.findAll();

        for(ReservationEntity existingReservation: allReservations) {
            if (reservation.getId().equals(existingReservation.getId())) {
                continue;
            }
            if (!reservation.getRoomId().equals(existingReservation.getRoomId())) {
                continue;
            }
            if (existingReservation.getStatus().equals(ReservationStatus.APPROVED)) {
                continue;
            }
            if (reservation.getStartDate().isBefore(ChronoLocalDate.from(existingReservation.getEndDate()))
                && existingReservation.getStartDate().isBefore(ChronoLocalDate.from(reservation.getEndDate()))) {
                return true;
            }
        }
        return false;
    }

    private Reservation toDamainReservation(ReservationEntity reservation) {
        return new Reservation(
        reservation.getId(),
                reservation.getUserId(),
                reservation.getRoomId(),
                reservation.getStartDate(),
                reservation.getEndDate(),
                reservation.getStatus()
        );
    }
}
