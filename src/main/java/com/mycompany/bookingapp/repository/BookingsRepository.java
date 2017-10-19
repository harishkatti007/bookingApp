package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.Bookings;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.math.BigDecimal;
import java.util.List;


/**
 * Spring Data JPA repository for the Bookings entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BookingsRepository extends JpaRepository<Bookings,Long> {

}
