package com.example.bookmyshowJune.Controllers;

import com.example.bookmyshowJune.Dtos.RequestDto.AddShowDto;
import com.example.bookmyshowJune.Dtos.RequestDto.ShowSeatsDto;
import com.example.bookmyshowJune.Models.Movie;
import com.example.bookmyshowJune.Models.Theater;
import com.example.bookmyshowJune.Services.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/show")
public class ShowController {

    @Autowired
    private ShowService showService;

    @PostMapping("/add")
    public String addShow(@RequestBody AddShowDto addShowDto){
        try{
            return showService.addShow(addShowDto);
        }catch (Exception e){
            return e.getMessage();
        }

    }

    @PostMapping("/associate-seats")
    public String associateSeats(@RequestBody ShowSeatsDto showSeatsDto){

            try{
                return showService.associateShowSeats(showSeatsDto);
            }catch (Exception e){
                return e.getMessage();
            }

    }

    @GetMapping("/most-recommended-movie-name")
    public String getMovieName(AddShowDto addShowDto){

        return showService.getMovieName(addShowDto);
    }

    @GetMapping("/{theaterId}/{movieId}")
    public LocalTime GetShowtime(@PathVariable Integer movieId, @PathVariable Integer theaterId) {

        return showService.getShowtime(movieId, theaterId);
    }

}
