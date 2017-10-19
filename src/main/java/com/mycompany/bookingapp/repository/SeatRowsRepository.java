package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.SeatRows;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;

import java.util.List;


/**
 * Spring Data JPA repository for the SeatRows entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SeatRowsRepository extends JpaRepository<SeatRows,Long> {

    List<SeatRows> findByScreening_Id(Long id);
}
