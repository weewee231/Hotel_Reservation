package school.gera.reservationsystem;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Table(name = "reservations")
@Entity
public class ReservationEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name= "user_id")
    private Long userId;

    @Column(name= "room_id")
    private Long roomId;

    @Column(name= "start_date")
    private LocalDate startDate;

    @Column(name= "end_date")
    private LocalDateTime endDate;

    @Enumerated(EnumType.STRING)
    @Column(name= "status")
    private ReservationStatus status;

    public ReservationEntity() {
    }

    public ReservationEntity(Long id, Long userId, Long roomId, LocalDateTime endDate, LocalDate startDate, ReservationStatus status) {
        this.id = id;
        this.userId = userId;
        this.roomId = roomId;
        this.endDate = endDate;
        this.startDate = startDate;
        this.status = status;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public ReservationStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationStatus status) {
        this.status = status;
    }
}
