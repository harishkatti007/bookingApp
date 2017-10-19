package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.SeatType;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the SeatType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeatTypeRepository extends JpaRepository<SeatType,Long> {
    
}
