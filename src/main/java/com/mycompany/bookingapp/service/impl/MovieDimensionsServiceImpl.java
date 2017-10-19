package com.mycompany.bookingapp.service.impl;

import com.mycompany.bookingapp.service.MovieDimensionsService;
import com.mycompany.bookingapp.domain.MovieDimensions;
import com.mycompany.bookingapp.repository.MovieDimensionsRepository;
import com.mycompany.bookingapp.service.dto.MovieDimensionsDTO;
import com.mycompany.bookingapp.service.mapper.MovieDimensionsMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing MovieDimensions.
 */
@Service
@Transactional
public class MovieDimensionsServiceImpl implements MovieDimensionsService{

    private final Logger log = LoggerFactory.getLogger(MovieDimensionsServiceImpl.class);

    private final MovieDimensionsRepository movieDimensionsRepository;

    private final MovieDimensionsMapper movieDimensionsMapper;

    public MovieDimensionsServiceImpl(MovieDimensionsRepository movieDimensionsRepository, MovieDimensionsMapper movieDimensionsMapper) {
        this.movieDimensionsRepository = movieDimensionsRepository;
        this.movieDimensionsMapper = movieDimensionsMapper;
    }

    /**
     * Save a movieDimensions.
     *
     * @param movieDimensionsDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MovieDimensionsDTO save(MovieDimensionsDTO movieDimensionsDTO) {
        log.debug("Request to save MovieDimensions : {}", movieDimensionsDTO);
        MovieDimensions movieDimensions = movieDimensionsMapper.toEntity(movieDimensionsDTO);
        movieDimensions = movieDimensionsRepository.save(movieDimensions);
        return movieDimensionsMapper.toDto(movieDimensions);
    }

    /**
     *  Get all the movieDimensions.
     *
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MovieDimensionsDTO> findAll(Pageable pageable) {
        log.debug("Request to get all MovieDimensions");
        return movieDimensionsRepository.findAll(pageable)
            .map(movieDimensionsMapper::toDto);
    }

    /**
     *  Get one movieDimensions by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MovieDimensionsDTO findOne(Long id) {
        log.debug("Request to get MovieDimensions : {}", id);
        MovieDimensions movieDimensions = movieDimensionsRepository.findOne(id);
        return movieDimensionsMapper.toDto(movieDimensions);
    }

    /**
     *  Delete the  movieDimensions by id.
     *
     *  @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete MovieDimensions : {}", id);
        movieDimensionsRepository.delete(id);
    }
}
