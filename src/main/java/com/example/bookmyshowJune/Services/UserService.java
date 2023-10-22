package com.example.bookmyshowJune.Services;

import com.example.bookmyshowJune.Dtos.RequestDto.AddUserDto;
import com.example.bookmyshowJune.Dtos.ResponseDto.UserResponseDto;
import com.example.bookmyshowJune.Exception.NoUserFoundException;
import com.example.bookmyshowJune.Models.Ticket;
import com.example.bookmyshowJune.Models.User;
import com.example.bookmyshowJune.Repository.TicketRepository;
import com.example.bookmyshowJune.Repository.UserRepository;
import com.example.bookmyshowJune.Transformers.UserTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    TicketRepository ticketRepository;

    public String addUser(AddUserDto userDto){

//        User user = UserTransformer.convertDtoToEntity(userDto);
        User user = new User();
        user.setMobNo(userDto.getMobNo());
        user.setEmail(userDto.getEmailId());
        user.setName(userDto.getName());
        user.setAge(userDto.getAge());

        userRepository.save(user);

        return "User has been added successfully ";
    }

    public UserResponseDto getOldestUser()throws NoUserFoundException {
        //Prevent you from exposing the PK
        //Prevents Infinite recursion incase it occurs
        List<User> users = userRepository.findAll();
        Integer maxAge = 0;

        User userAns = null;

        for(User user: users)
        {
            if(user.getAge()>maxAge){
                maxAge = user.getAge();
                userAns = user;
            }
        }

        if(userAns==null){
            throw new NoUserFoundException("No user Found");
        }

        //We need to transform the UserEntity to the userResponse
        UserResponseDto userResponseDto = UserTransformer.convertEntityToDto(userAns);

        return userResponseDto;
    }

    public List<User> getAllUserGreaterThan(Integer age){

        List<User> users = userRepository.findUserWithAgeGreater(age);
        return users;

    }

    public List<Ticket> getAllTicketsBookedByPerson(Integer userId) throws NoUserFoundException {

        Optional<User> optionalUser = userRepository.findById(userId);

        if(!optionalUser.isPresent()) {
            throw new NoUserFoundException("Invalid userId, please enter correct userId");
        }

//        User user = optionalUser.get();
        List<Ticket> ticketList = ticketRepository.findByUserId(userId);

//        List<Ticket> ticketList = user.getTicketList();

        return ticketList;
    }
}