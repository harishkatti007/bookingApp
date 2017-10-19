package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.UserAccountDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity UserAccount and its DTO UserAccountDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface UserAccountMapper extends EntityMapper <UserAccountDTO, UserAccount> {
    
    
    default UserAccount fromId(Long id) {
        if (id == null) {
            return null;
        }
        UserAccount userAccount = new UserAccount();
        userAccount.setId(id);
        return userAccount;
    }
}
