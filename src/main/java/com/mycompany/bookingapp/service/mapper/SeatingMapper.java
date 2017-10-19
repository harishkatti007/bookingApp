package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.SeatingDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Seating and its DTO SeatingDTO.
 */
@Mapper(componentModel = "spring", uses = {SeatRowsMapper.class, SeatTypeMapper.class, })
public interface SeatingMapper extends EntityMapper <SeatingDTO, Seating> {

    @Mapping(source = "seatRows.id", target = "seatRowsId")

    @Mapping(source = "seatType.id", target = "seatTypeId")
    SeatingDTO toDto(Seating seating); 

    @Mapping(source = "seatRowsId", target = "seatRows")

    @Mapping(source = "seatTypeId", target = "seatType")
    Seating toEntity(SeatingDTO seatingDTO); 
    default Seating fromId(Long id) {
        if (id == null) {
            return null;
        }
        Seating seating = new Seating();
        seating.setId(id);
        return seating;
    }
}
