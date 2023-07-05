package com.example.bookmyshowJune.Controllers;


import com.example.bookmyshowJune.Dtos.RequestDto.MovieEntryDto;
import com.example.bookmyshowJune.Services.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/movie")
public class MovieController {

    @Autowired
    MovieService movieService;

    @PostMapping("/add")
    public String addMovie(@RequestBody MovieEntryDto movieEntryDto){

        return movieService.addMovie(movieEntryDto);
    }

    @GetMapping("/movieNamewithTheMaximumNumberOfShows")
    public String movieNamewithTheMaximumNumberOfShows() {
        return movieService.movieNamewithTheMaximumNumberOfShows();
    }

}
