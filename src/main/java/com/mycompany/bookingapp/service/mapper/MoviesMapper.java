package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.MoviesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Movies and its DTO MoviesDTO.
 */
@Mapper(componentModel = "spring", uses = {MovieDimensionsMapper.class, })
public interface MoviesMapper extends EntityMapper <MoviesDTO, Movies> {

    @Mapping(source = "movieDimensions.id", target = "movieDimensionsId")
    MoviesDTO toDto(Movies movies); 

    @Mapping(source = "movieDimensionsId", target = "movieDimensions")
    Movies toEntity(MoviesDTO moviesDTO); 
    default Movies fromId(Long id) {
        if (id == null) {
            return null;
        }
        Movies movies = new Movies();
        movies.setId(id);
        return movies;
    }
}
