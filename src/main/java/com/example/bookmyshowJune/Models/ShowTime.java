package com.example.bookmyshowJune.Models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShowTime {
    private Movie movie;
    private Theater theater;
    private Date date;
}
