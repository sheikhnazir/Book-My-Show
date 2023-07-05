package com.example.bookmyshowJune.Dtos.RequestDto;

import com.fasterxml.jackson.databind.DatabindException;
import lombok.Data;

import java.time.LocalTime;
import java.util.Date;
@Data
public class AddShowDto {

    private LocalTime showStartTime;

    private Date showDate;

    private int theaterId;

    private int movieId;
}
