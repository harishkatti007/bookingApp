package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.Regions;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the Regions entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RegionsRepository extends JpaRepository<Regions,Long> {
    
}
