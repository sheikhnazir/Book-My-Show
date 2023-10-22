package com.example.bookmyshowJune.ServiceTest;

import com.example.bookmyshowJune.Dtos.RequestDto.AddUserDto;
import com.example.bookmyshowJune.Dtos.ResponseDto.UserResponseDto;
import com.example.bookmyshowJune.Exception.NoUserFoundException;
import com.example.bookmyshowJune.Models.User;
import com.example.bookmyshowJune.Repository.UserRepository;
import com.example.bookmyshowJune.Services.UserService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
public class UserServiceTest {

    @InjectMocks    //@InjectMocks is used to inject the UserService instance.
    private UserService userService;

    @Mock           // @Mock is used to mock the UserRepository.
    private UserRepository userRepository;

    @BeforeEach
    public void init() {
        MockitoAnnotations.initMocks(this);    // MockitoAnnotations.initMocks(this) is used to initialize the mocks.
    }

    @Test
    @Transactional
    public void testAddUser() {
        // Create a sample AddUserDto
        AddUserDto addUserDto = new AddUserDto();
        addUserDto.setName("JohnDoe");
        addUserDto.setAge(30);
        addUserDto.setEmailId("john.doe@example.com");
        addUserDto.setMobNo("4467583982");

        // Create a User object from AddUserDto
        User user = new User();
        user.setName(addUserDto.getName());
        user.setAge(addUserDto.getAge());
        user.setEmail(addUserDto.getEmailId());
        user.setMobNo(addUserDto.getMobNo());

        // Mock the UserRepository to return a saved User object
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);
        System.out.println(user);

        // Call the addUser method
        String result = userService.addUser(addUserDto);

        // Verify that the userRepository.save method was called
        Mockito.verify(userRepository).save(Mockito.any(User.class));

        // Assert the result message
        assert(result.equals("User has been added successfully "));
    }

    @Test
    @Transactional
    public void testGetOldestUser() throws NoUserFoundException {
        // Create a list of sample users
        List<User> users = new ArrayList<>();
        User user1 = new User();
        user1.setId(1);
        user1.setName("User1");
        user1.setAge(25);
        user1.setEmail("user1@gmail.com");
        user1.setMobNo("1772883349");

        User user2 = new User();
        user2.setId(2);
        user2.setName("User2");
        user2.setAge(30);
        user1.setEmail("user2@gmail.com");
        user1.setMobNo("7733628359");

        User user3 = new User();
        user3.setId(3);
        user3.setName("User3");
        user3.setAge(35);
        user1.setEmail("user3@gmail.com");
        user1.setMobNo("9982758372");

        users.add(user1);
        users.add(user2);
        users.add(user3);

        // Mock the UserRepository to return the list of users
        Mockito.when(userRepository.findAll()).thenReturn(users);

        // Call the getOldestUser method
        UserResponseDto userResponseDto = userService.getOldestUser();

        // Verify that the userRepository.findAll method was called
        Mockito.verify(userRepository).findAll();

        // Check if the response contains the correct user data
        assertEquals("User3", userResponseDto.getName()); // The oldest user is User3 with age 35
    }

}
