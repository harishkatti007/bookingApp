package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.ScreensDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Screens and its DTO ScreensDTO.
 */
@Mapper(componentModel = "spring", uses = {VenuesMapper.class, })
public interface ScreensMapper extends EntityMapper <ScreensDTO, Screens> {

    @Mapping(source = "venues.id", target = "venuesId")
    ScreensDTO toDto(Screens screens); 

    @Mapping(source = "venuesId", target = "venues")
    Screens toEntity(ScreensDTO screensDTO); 
    default Screens fromId(Long id) {
        if (id == null) {
            return null;
        }
        Screens screens = new Screens();
        screens.setId(id);
        return screens;
    }
}
