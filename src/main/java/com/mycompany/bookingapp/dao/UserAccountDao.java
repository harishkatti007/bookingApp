package com.mycompany.bookingapp.dao;

import com.mycompany.bookingapp.domain.UserAccount;
import com.mycompany.bookingapp.repository.UserAccountRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by tech on 19/10/17.
 */
@Service
public class UserAccountDao {

    @Inject
    private UserAccountRepository userAccountRepository;

    public UserAccount getUserAccountDetails(UserAccount userAccount) {
        return userAccountRepository.findByEmailId(userAccount.getEmailId());
    }
}
