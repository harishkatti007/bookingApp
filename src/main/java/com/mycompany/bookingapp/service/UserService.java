package com.mycompany.bookingapp.service;

import com.mycompany.bookingapp.service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing User.
 */
public interface UserService {

    /**
     * Save a user.
     *
     * @param userDTO the entity to save
     * @return the persisted entity
     */
    UserDTO save(UserDTO userDTO);

    /**
     *  Get all the users.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    Page<UserDTO> findAll(Pageable pageable);

    /**
     *  Get the "id" user.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    UserDTO findOne(Long id);

    /**
     *  Delete the "id" user.
     *
     *  @param id the id of the entity
     */
    void delete(Long id);
}
