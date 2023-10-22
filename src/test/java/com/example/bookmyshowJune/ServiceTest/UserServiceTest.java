package com.example.bookmyshowJune.ServiceTest;

import com.example.bookmyshowJune.Dtos.RequestDto.AddUserDto;
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
    public void testGetOldestUserNoUserFound() {
        // Mock the UserRepository to return an empty list
        Mockito.when(userRepository.findAll()).thenReturn(new ArrayList<>());

        // Call the getOldestUser method and expect a NoUserFoundException
        assertThrows(NoUserFoundException.class, () -> userService.getOldestUser());
    }
}
