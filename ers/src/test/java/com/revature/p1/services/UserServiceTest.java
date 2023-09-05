package com.revature.p1.services;

import com.revature.p1.custom_exceptions.InvalidUserException;
import com.revature.p1.daos.UserDAO;
import com.revature.p1.dtos.requests.NewUserRequest;
import com.revature.p1.models.User;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class UserServiceTest {
    private UserService sut;
    private final UserDAO mockUserDAO = Mockito.mock(UserDAO.class);


    @Before
    public void inti() {
        sut = new UserService(mockUserDAO);
    }

    @Test
    public void test_isValidUsername_givenUniqueUsername() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String uniqueUsername = "bduong0929";
        List<String> stubbedUsernames = Arrays.asList("tester001", "tester002", "tester003");

        // controlled env
        Mockito.when(mockUserDAO.findAllUsernames()).thenReturn(stubbedUsernames);

        // Act
        boolean condition = spySut.isUniqueUsername(uniqueUsername);

        // Assert
        assertFalse(condition);
    }

    @Test
    public void test_isValidSignup_persistUserGivenUsernameAndPassword() {
        // Arrange
        UserService spySut = Mockito.spy(sut);
        String validUsername = "michael007";
        String validPassword1 = "passw0rd";
        String validPassword2 = "passw0rd";
        String email = "someemail@email.com";
        String given_name = "given_name";
        String surname = "surname";
        NewUserRequest stubbedReq = new NewUserRequest(validUsername, email, given_name, surname, validPassword1, validPassword2);

        // Act
        User createdUser = spySut.saveUser(stubbedReq);

        // Assert
        assertNotNull(createdUser);
        assertNotNull(createdUser.getUser_Id());
        assertNotNull(createdUser.getUsername());
        assertNotNull(createdUser.getPassword());
        assertNotEquals("", createdUser.getUsername());
        assertEquals("046e0a7e-735c-11ed-a1eb-0242ac120002", createdUser.getRole_id());
        Mockito.verify(mockUserDAO, Mockito.times(1)).save(createdUser);
    }


}