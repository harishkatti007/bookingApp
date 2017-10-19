package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.SeatTypeDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SeatType and its DTO SeatTypeDTO.
 */
@Mapper(componentModel = "spring", uses = {})
public interface SeatTypeMapper extends EntityMapper <SeatTypeDTO, SeatType> {
    
    
    default SeatType fromId(Long id) {
        if (id == null) {
            return null;
        }
        SeatType seatType = new SeatType();
        seatType.setId(id);
        return seatType;
    }
}
