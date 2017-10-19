package com.mycompany.bookingapp.service.mapper;

import com.mycompany.bookingapp.domain.*;
import com.mycompany.bookingapp.service.dto.BookingsDTO;

import org.mapstruct.*;

/**
 * Mapper for the entity Bookings and its DTO BookingsDTO.
 */
@Mapper(componentModel = "spring", uses = {UserAccountMapper.class, MoviesMapper.class, ScreeningMapper.class, })
public interface BookingsMapper extends EntityMapper <BookingsDTO, Bookings> {

    @Mapping(source = "userAccount.id", target = "userAccountId")

    @Mapping(source = "movies.id", target = "moviesId")

    @Mapping(source = "screening.id", target = "screeningId")
    BookingsDTO toDto(Bookings bookings); 

    @Mapping(source = "userAccountId", target = "userAccount")

    @Mapping(source = "moviesId", target = "movies")

    @Mapping(source = "screeningId", target = "screening")
    Bookings toEntity(BookingsDTO bookingsDTO); 
    default Bookings fromId(Long id) {
        if (id == null) {
            return null;
        }
        Bookings bookings = new Bookings();
        bookings.setId(id);
        return bookings;
    }
}
