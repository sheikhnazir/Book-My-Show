package com.example.bookmyshowJune.Services;

import com.example.bookmyshowJune.Dtos.RequestDto.TicketRequestDto;
import com.example.bookmyshowJune.Dtos.ResponseDto.TicketResponseDto;
import com.example.bookmyshowJune.Exception.NoUserFoundException;
import com.example.bookmyshowJune.Exception.ShowNotFound;
import com.example.bookmyshowJune.Models.*;
import com.example.bookmyshowJune.Repository.MovieRepository;
import com.example.bookmyshowJune.Repository.ShowRepository;
import com.example.bookmyshowJune.Repository.TicketRepository;
import com.example.bookmyshowJune.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ShowRepository showRepository;

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private JavaMailSender emailSender;

    public String cancelTicket(Integer ticketId) throws Exception{

        //Ticket Validation
        Optional<Ticket> ticketOptional = ticketRepository.findById(ticketId);
        if (!ticketOptional.isPresent()) {
            throw new Exception("Ticket not Found...");
        }

        Ticket ticket = ticketOptional.get();
        String bookedSeats = ticket.getBookedSeats();

        Show show1 = ticket.getShow();

        // Step 1: Split the string
        String[] bookedSeatsSplitted = bookedSeats.split(",");
        // Convert the array to a List
        List<String> ticketBookedSeats = Arrays.asList(bookedSeatsSplitted);

        List<ShowSeat> showSeatList = show1.getShowSeatList();
        // Traverse the array and print each element
        for(ShowSeat showSeat : showSeatList){
            String seatNo = showSeat.getSeatNo();
            if(ticketBookedSeats.contains(seatNo)){

                showSeat.setAvailable(true);
            }
        }

        ticketRepository.deleteById(ticketId);
        User user= ticket.getUser();
        List<Ticket> ticketList = user.getTicketList();
        for(Ticket ticket1 : ticketList) {

            if(ticket1.getId() == ticketId) {
                ticketList.remove(ticket1);
                user.setTicketList(ticketList);
            }
        }

        List<Ticket> ticketList1 = show1.getTicketList();
        for(Ticket ticket1 : ticketList1) {

            if(ticket1.getId() == ticketId) {
                ticketList1.remove(ticket1);
                show1.setTicketList(ticketList);
            }
        }
        return "Ticket has been cancelled successfully...";
    }

    public TicketResponseDto bookTicket(TicketRequestDto ticketRequestDto)throws NoUserFoundException, ShowNotFound,Exception {

        //User validation
        int userId = ticketRequestDto.getUserId();
        Optional<User> userOptional = userRepository.findById(userId);
        if(!userOptional.isPresent()){
            throw new NoUserFoundException("User Id is incorrect");
        }

        //Show Validation
        int showId = ticketRequestDto.getShowId();
        Optional<Show> showOptional = showRepository.findById(showId);
        if(!showOptional.isPresent()){
            throw new ShowNotFound("Show is not found");
        }
        Show show = showOptional.get();

        //Validation for the requested Seats are available or not
        boolean isValid = validateShowAvailability(show,ticketRequestDto.getRequestedSeats());

        if(isValid==false){
            throw new Exception("Requested Seats entered are not available");
        }

        Ticket ticket = new Ticket();
        //calculate the total price

        int totalPrice = calculateTotalPrice(show,ticketRequestDto.getRequestedSeats());

        ticket.setTotalTicketsPrice(totalPrice);  // Setting total cost of tickets user is going to book

        Movie movie = show.getMovie();

        movie.setBoxOfficeCollection(movie.getBoxOfficeCollection() + totalPrice);

        movieRepository.save(movie);

        //Convert the list of booked seats into string from list
        String bookedSeats = convertListToString(ticketRequestDto.getRequestedSeats());

        ticket.setBookedSeats(bookedSeats);
        //Do bidirectional mapping

        User user = userOptional.get();

        ticket.setUser(user);
        ticket.setShow(show);

        ticket = ticketRepository.save(ticket);

        user.getTicketList().add(ticket);
        //Saving the relevant repositories

        userRepository.save(user);

        show.getTicketList().add(ticket);

        showRepository.save(show);


        //We can send a mail to the person
        SimpleMailMessage simpleMessageMail = new SimpleMailMessage();

        String body = "Hi "+user.getName()+" ! \n"+
                "You have successfully booked a ticket. Please find the following details"+
                " booked seat No's : "  + bookedSeats
                +" movie Name : " + show.getMovie().getMovieName()
                +" show Date is : "+show.getDate()+
                " And show time is : "+show.getTime()+
                " Enjoy the Show !!!";

        simpleMessageMail.setSubject("Show Ticket Confirmation Mail");
        simpleMessageMail.setFrom("backendtestmail4@gmail.com");
        simpleMessageMail.setText(body);
        simpleMessageMail.setTo(user.getEmail());

        emailSender.send(simpleMessageMail);


        return createTicketReponseDto(show,ticket);
    }

    private boolean validateShowAvailability(Show show, List<String> requestedSeats){

        List<ShowSeat> showSeatList = show.getShowSeatList();

        for(ShowSeat showSeat : showSeatList){
            String seatNo = showSeat.getSeatNo();
            if(requestedSeats.contains(seatNo)){

                if(showSeat.isAvailable()==false)
                    return false;
            }
        }
        return true;

    }

    private int calculateTotalPrice(Show show, List<String> requestedSeats){

        int totalPrice = 0;

        List<ShowSeat> showSeatList = show.getShowSeatList();

        for(ShowSeat showSeat : showSeatList){

            if(requestedSeats.contains(showSeat.getSeatNo())){
                totalPrice = totalPrice + showSeat.getPrice();
                showSeat.setAvailable(false);
            }
        }

        return totalPrice;
    }

    String convertListToString(List<String> seats){

        String result = "";
        for(String seatNo : seats){
            result = result + seatNo+", ";
        }
        return result;
    }

    private TicketResponseDto createTicketReponseDto(Show show,Ticket ticket){

        TicketResponseDto ticketResponseDto = TicketResponseDto.builder()
                .bookedSeats(ticket.getBookedSeats())
                .location(show.getTheater().getLocation())
                .theaterName(show.getTheater().getName())
                .movieName(show.getMovie().getMovieName())
                .showDate(show.getDate())
                .showTime(show.getTime())
                .totalPrice(ticket.getTotalTicketsPrice())
                .build();

        return ticketResponseDto;
    }

}




//        Dear Nazir Ahmad Shiekh your registration for PARVAAZ IAS/KAS is successful.
//        Please note down MY-PRVZCC-21-00337 as your application ID.
//        The information has also been mailed to your registered email address sheikhnazir248@gmail.com.

