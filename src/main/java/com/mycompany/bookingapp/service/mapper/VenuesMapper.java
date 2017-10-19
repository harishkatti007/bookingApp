package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.VenuesDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Venues and its DTO VenuesDTO.
 */
@Mapper(componentModel = "spring", uses = {RegionsMapper.class, })
public interface VenuesMapper extends EntityMapper <VenuesDTO, Venues> {

    @Mapping(source = "regions.id", target = "regionsId")
    VenuesDTO toDto(Venues venues); 

    @Mapping(source = "regionsId", target = "regions")
    Venues toEntity(VenuesDTO venuesDTO); 
    default Venues fromId(Long id) {
        if (id == null) {
            return null;
        }
        Venues venues = new Venues();
        venues.setId(id);
        return venues;
    }
}
