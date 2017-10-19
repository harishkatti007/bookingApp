package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.Seating;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the Seating entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeatingRepository extends JpaRepository<Seating,Long> {

    List<Seating> findBySeatRows_Id(Long id);

    List<Seating> findByIdIn(List<Long> seatIds);

    @Query(value = "update Seating set booked = 1 where id IN :ids ")
    @Modifying
    int updateBookings(@Param(value = "ids") List<Long> ids);

    @Query(value = "update Seating set booked = 0 where id IN :ids")
    @Modifying
    int cancelBookings(@Param(value = "ids") List<Long> ids);
}
