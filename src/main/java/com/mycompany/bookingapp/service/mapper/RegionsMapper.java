package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.RegionsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Regions and its DTO RegionsDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface RegionsMapper extends EntityMapper <RegionsDTO, Regions> {
    
    
    default Regions fromId(Long id) {
        if (id == null) {
            return null;
        }
        Regions regions = new Regions();
        regions.setId(id);
        return regions;
    }
}
