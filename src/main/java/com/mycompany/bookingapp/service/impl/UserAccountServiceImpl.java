package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.UserAccountService;
import com.mycompany.bookingapp.domain.UserAccount;
import com.mycompany.bookingapp.repository.UserAccountRepository;
import com.mycompany.bookingapp.service.dto.UserAccountDTO;
import com.mycompany.bookingapp.service.mapper.UserAccountMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserAccount.
 */
@Service
@Transactional
public class UserAccountServiceImpl implements UserAccountService{

    private final Logger log = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private final UserAccountRepository userAccountRepository;

    private final UserAccountMapper userAccountMapper;

    public UserAccountServiceImpl(UserAccountRepository userAccountRepository, UserAccountMapper userAccountMapper) {
        this.userAccountRepository = userAccountRepository;
        this.userAccountMapper = userAccountMapper;
    }

    /**
     * Save a userAccount.
     *
     * @param userAccountDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserAccountDTO save(UserAccountDTO userAccountDTO) {
        log.debug("Request to save UserAccount : {}", userAccountDTO);
        UserAccount userAccount = userAccountMapper.toEntity(userAccountDTO);
        userAccount = userAccountRepository.save(userAccount);
        return userAccountMapper.toDto(userAccount);
    }

    /**
     *  Get all the userAccounts.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserAccountDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserAccounts");
        return userAccountRepository.findAll(pageable)
            .map(userAccountMapper::toDto);
    }

    /**
     *  Get one userAccount by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserAccountDTO findOne(Long id) {
        log.debug("Request to get UserAccount : {}", id);
        UserAccount userAccount = userAccountRepository.findOne(id);
        return userAccountMapper.toDto(userAccount);
    }

    /**
     *  Delete the  userAccount by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserAccount : {}", id);
        userAccountRepository.delete(id);
    }
}
