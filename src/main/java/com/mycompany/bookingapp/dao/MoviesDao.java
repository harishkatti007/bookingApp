package com.mycompany.bookingapp.dao;

import com.mycompany.bookingapp.domain.Movies;
import com.mycompany.bookingapp.repository.MoviesRepository;
import org.springframework.stereotype.Service;

import javax.inject.Inject;

/**
 * Created by tech on 19/10/17.
 */
@Service
public class MoviesDao {

    @Inject
    private MoviesRepository moviesRepository;

    public Movies getMoviesById(Long movieId) {
        return moviesRepository.findById(movieId);
    }
}
