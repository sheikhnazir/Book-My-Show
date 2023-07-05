package com.example.bookmyshowJune.Models;


import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Table(name = "tickets")
@Data
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private int totalTicketsPrice;

    private String bookedSeats;

    @CreationTimestamp
    private Date bookedAt;


    @ManyToOne
    @JoinColumn
    private Show show;


    @ManyToOne
    @JoinColumn
    private User user;

}
