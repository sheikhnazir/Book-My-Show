package com.example.bookmyshowJune.Services;

import com.example.bookmyshowJune.Dtos.RequestDto.MovieEntryDto;
import com.example.bookmyshowJune.Models.Movie;
import com.example.bookmyshowJune.Repository.MovieRepository;
import com.example.bookmyshowJune.Transformers.MovieTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {

    @Autowired
    MovieRepository movieRepository;

    public String addMovie(MovieEntryDto movieEntryDto){

        Movie movie = MovieTransformer.convertDtoToEntity(movieEntryDto);

        movieRepository.save(movie);

        return "Movie added successfully";
    }

    public String movieNamewithTheMaximumNumberOfShows () {

        List<Movie> movieList = movieRepository.findAll();
        Movie ans = movieList.get(0);
        int max = 0;
        for (Movie movie : movieList) {

            if (movie.getShowList().size() > max) {
                ans = movie;
            }
        }
        return ans.getMovieName();
    }
}
