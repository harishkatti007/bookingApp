package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.User;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the User entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserRepository extends JpaRepository<User,Long> {
    
}
