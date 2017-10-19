package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.MovieDimensionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity MovieDimensions and its DTO MovieDimensionsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface MovieDimensionsMapper extends EntityMapper <MovieDimensionsDTO, MovieDimensions> {
    
    
    default MovieDimensions fromId(Long id) {
        if (id == null) {
            return null;
        }
        MovieDimensions movieDimensions = new MovieDimensions();
        movieDimensions.setId(id);
        return movieDimensions;
    }
}
