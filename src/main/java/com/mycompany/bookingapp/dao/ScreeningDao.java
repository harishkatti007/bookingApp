package com.mycompany.bookingapp.dao;

import com.mycompany.bookingapp.domain.Movies;
import com.mycompany.bookingapp.domain.Screening;
import com.mycompany.bookingapp.domain.Screens;
import com.mycompany.bookingapp.repository.MoviesRepository;
import com.mycompany.bookingapp.repository.ScreeningRepository;
import com.mycompany.bookingapp.repository.ScreensRepository;
import com.mycompany.bookingapp.service.dto.ScreeningDTO;
import com.mycompany.bookingapp.service.dto.SeatMatrixRequest;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;

/**
 * Created by tech on 17/10/17.
 */
@Service
public class ScreeningDao {

    @Inject
    private ScreeningRepository screeningRepository;

    @Inject
    private MoviesRepository moviesRepository;

    @Inject
    private ScreensRepository screensRepository;

    public Screening getScreeningDetails(ScreeningDTO screeningDTO) {
        Screening screening = screeningRepository.findById(screeningDTO.getId());
        return screening;
    }

    public Screening findById(Long screeingId) {
        return screeningRepository.findById(screeingId);
    }
}
