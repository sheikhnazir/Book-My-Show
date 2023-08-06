package com.example.bookmyshowJune.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="shows")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Show {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalTime time;

    private Date date;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Movie movie;

    @ManyToOne
    @JoinColumn
    @JsonBackReference
    private Theater theater;

    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    @JsonBackReference // To overcome the recursion problem also we can use JsonIgnore
    private List<ShowSeat> showSeatList = new ArrayList<>();

    @OneToMany(mappedBy = "show",cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Ticket> ticketList = new ArrayList<>();

}

