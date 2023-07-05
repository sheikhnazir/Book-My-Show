package com.example.bookmyshowJune.Dtos.RequestDto;


import lombok.Data;

import java.util.List;

@Data
public class TicketRequestDto {

    private int showId;

    private int userId;

    private List<String> requestedSeats;

    //add IsFoodAdded variable
}
