package com.example.bookmyshowJune.Dtos.RequestDto;

import com.example.bookmyshowJune.Enums.Genre;
import com.example.bookmyshowJune.Enums.Language;
import lombok.Data;

import java.util.Date;

@Data
public class MovieEntryDto {

    private String movieName;
    private double duration;
    private double rating;
    private Date releaseDate;
    private Genre genre;
    private Language language;
}
