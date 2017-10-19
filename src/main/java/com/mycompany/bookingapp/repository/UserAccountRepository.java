package com.mycompany.bookingapp.repository;

import com.mycompany.bookingapp.domain.UserAccount;
import org.springframework.stereotype.Repository;

import org.springframework.data.jpa.repository.*;


/**
 * Spring Data JPA repository for the UserAccount entity.
 */
@SuppressWarnings("unused")
@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount,Long> {

    UserAccount findByEmailId(String emailId);
}
