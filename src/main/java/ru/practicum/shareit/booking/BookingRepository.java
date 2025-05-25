package ru.practicum.shareit.booking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.practicum.shareit.booking.model.Booking;
import ru.practicum.shareit.constants.BookingStatus;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b FROM Booking b " +
            "WHERE b.booker.id = :userId")
    List<Booking> findAllByUserId(@Param("userId") Long userId);

    @Query("SELECT MAX(b.end) FROM Booking b " +
            "WHERE b.item.id = :itemId")
    LocalDateTime findLastBookingEndByItemId(@Param("itemId") Long itemId);

    boolean existsByBookerIdAndItemIdAndStatus(Long bookerId, Long itemId, BookingStatus status);
}
