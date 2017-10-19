package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.MoviesService;
import com.mycompany.bookingapp.domain.Movies;
import com.mycompany.bookingapp.repository.MoviesRepository;
import com.mycompany.bookingapp.service.dto.MoviesDTO;
import com.mycompany.bookingapp.service.mapper.MoviesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Movies.
 */
@Service
@Transactional
public class MoviesServiceImpl implements MoviesService{

    private final Logger log = LoggerFactory.getLogger(MoviesServiceImpl.class);

    private final MoviesRepository moviesRepository;

    private final MoviesMapper moviesMapper;

    public MoviesServiceImpl(MoviesRepository moviesRepository, MoviesMapper moviesMapper) {
        this.moviesRepository = moviesRepository;
        this.moviesMapper = moviesMapper;
    }

    /**
     * Save a movies.
     *
     * @param moviesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MoviesDTO save(MoviesDTO moviesDTO) {
        log.debug("Request to save Movies : {}", moviesDTO);
        Movies movies = moviesMapper.toEntity(moviesDTO);
        movies = moviesRepository.save(movies);
        return moviesMapper.toDto(movies);
    }

    /**
     *  Get all the movies.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MoviesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Movies");
        return moviesRepository.findAll(pageable)
            .map(moviesMapper::toDto);
    }

    /**
     *  Get one movies by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MoviesDTO findOne(Long id) {
        log.debug("Request to get Movies : {}", id);
        Movies movies = moviesRepository.findOne(id);
        return moviesMapper.toDto(movies);
    }

    /**
     *  Delete the  movies by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Movies : {}", id);
        moviesRepository.delete(id);
    }
}
