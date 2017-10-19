package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.SeatRowsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity SeatRows and its DTO SeatRowsDTO.
 */
@Mapper(componentModel = "spring", uses = {ScreeningMapper.class, })
public interface SeatRowsMapper extends EntityMapper <SeatRowsDTO, SeatRows> {

    @Mapping(source = "screening.id", target = "screeningId")
    SeatRowsDTO toDto(SeatRows seatRows); 

    @Mapping(source = "screeningId", target = "screening")
    SeatRows toEntity(SeatRowsDTO seatRowsDTO); 
    default SeatRows fromId(Long id) {
        if (id == null) {
            return null;
        }
        SeatRows seatRows = new SeatRows();
        seatRows.setId(id);
        return seatRows;
    }
}
