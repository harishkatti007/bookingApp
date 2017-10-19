package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.dao.UserAccountDao;
import com.mycompany.bookingapp.domain.UserAccount;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by tech on 19/10/17.
 */
@Service
public class CustomUserAccountService {

    @Inject
    private UserAccountDao userAccountDao;

    public boolean validateUserAccount(UserAccount account) {
        UserAccount userAccount = userAccountDao.getUserAccountDetails(account);
        if(userAccount != null ) {
            return true;
        }
        return false;
    }
}
