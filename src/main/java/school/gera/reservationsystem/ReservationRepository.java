package school.gera.reservationsystem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

public interface ReservationRepository extends JpaRepository<ReservationEntity, Long> {

  //  @Query(value = "select * from reservation r where r.status = :status", nativeQuery = true)
   // List<ReservationEntity> findByStatusIs(@Param("status") ReservationStatus status);

   // @Query(value = "select * from reservation r where r.room_id = :roomId", nativeQuery = true)
   // List<ReservationEntity> findByRoomId(@Param("roomId") Long roomId);

   // @Transactional
   // @Modifying
   // @Query("""
  //  update ReservationEntity r
   // set r.userId = :userId,
   // r.roomId = :roomId,
   // r.startDate = :startDate,
   // r.endDate = :endDate,
   // r.status = :status
   // where r.id = :id""")
   // int updateAllFields(
 //           @Param("id") Long id,
  //          @Param("userId") Long userId,
  //          @Param("roomId") Long roomId,
 //           @Param("startDate") LocalDate startDate,
  //          @Param("endDate") LocalDate endDate,
  //          @Param("status") ReservationStatus status
  //  );

    @Modifying
    @Query("""
    update ReservationEntity r 
    set r.status = :status 
    where r.id = :id
    """)
    void setStatus(@Param("id") Long id,
                   @Param("status") ReservationStatus reservationStatus);
}




