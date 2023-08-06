package com.example.bookmyshowJune.Repository;

import com.example.bookmyshowJune.Models.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TicketRepository extends JpaRepository<Ticket,Integer> {
    List<Ticket> findByUserId(Integer userId);
}
