package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Screening and its DTO ScreeningDTO.
 */
@Mapper(componentModel = "spring", uses = {ScreensMapper.class, MoviesMapper.class, })
public interface ScreeningMapper extends EntityMapper <ScreeningDTO, Screening> {

    @Mapping(source = "screens.id", target = "screensId")

    @Mapping(source = "movies.id", target = "moviesId")
    ScreeningDTO toDto(Screening screening); 

    @Mapping(source = "screensId", target = "screens")

    @Mapping(source = "moviesId", target = "movies")
    Screening toEntity(ScreeningDTO screeningDTO); 
    default Screening fromId(Long id) {
        if (id == null) {
            return null;
        }
        Screening screening = new Screening();
        screening.setId(id);
        return screening;
    }
}
