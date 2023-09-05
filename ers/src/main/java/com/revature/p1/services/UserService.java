package com.revature.p1.services;

import com.revature.p1.custom_exceptions.InvalidAuthException;
import com.revature.p1.custom_exceptions.InvalidUserException;
import com.revature.p1.daos.UserDAO;
import com.revature.p1.dtos.Principal;
import com.revature.p1.dtos.requests.*;
import com.revature.p1.models.User;

import java.util.List;
import java.util.UUID;

public class UserService {
    private final UserDAO userDAO;

    public UserService(UserDAO userDAO) {
        this.userDAO = userDAO;
    }


    public User saveUser(NewUserRequest req){
        /*List<String> emails=userDAO.findAllEmails();
        List<String> usernames=userDAO.findAllUsernames();
        if(emails.contains(req.getEmail())) throw new InvalidUserException("This email address is already in use.");
        if(!isValidUsername(req.getUsername())) throw new InvalidUserException("Username has to be 8-20 characters long, and the characters _ and . may not appear at the beginning, end, or in pairs.");
        if (usernames.contains(req.getUsername())) throw new InvalidUserException("Username is already taken.");
        if(!isValidPassword(req.getPassword1())) throw new InvalidUserException("Password must contain a of minimum eight characters, one letter and one number:" + req.getPassword1());
        if(!req.getPassword1().equals(req.getPassword2())) throw new InvalidUserException("Passwords do not match.");*/

        User createdUser = new User(UUID.randomUUID().toString(), req.getUsername(), req.getEmail(), req.getPassword1(), req.getGiven_name(), req.getSurname(), false, "046e0a7e-735c-11ed-a1eb-0242ac120002");
        userDAO.save(createdUser);
        return createdUser;
    }
    public void updatePassword(NewPasswordChange req){
        userDAO.updatePassword(req);
    }
    public void deleteUser(String username){
    userDAO.deleteUser(username);
    }
    public List<String> findAllUsernames() {
        return userDAO.findAllUsernames();

    }
     public void updateUser(NewEmployeeActivation req){

        if (req.getRole_id().equals("E"))
            req.setRole_id("046e0a7e-735c-11ed-a1eb-0242ac120002");
         else if (req.getRole_id().equals("M"))
            req.setRole_id("089473b8-735c-11ed-a1eb-0242ac120002");
         else if(req.getRole_id().equals("A"))
                req.setRole_id("0b9d895a-735c-11ed-a1eb-0242ac120002");




        User currentUser = new User(null, req.getUsername(), null, null, null, null, true, req.getRole_id());
        userDAO.updateUser(currentUser);
    }
    public List<User> getAllUsersByUsername(String username) {
        return userDAO.getAllUsersByUsername(username);
    }
    public List<String> getAllActiveUsernames(){
        return userDAO.findAllActiveUsernames();
    }
    public void deactivateUser(NewDeactivationRequest req) {

        userDAO.deactivateUser(req.getUsername());
    }
    public boolean isUserActive(String user_id){
        List<String> activeUsers = userDAO.findAllActives();
        return activeUsers.contains(user_id);
    }

    public Principal login(NewLoginRequest req) {
    User validUser = userDAO.getUserByUserAndPassword(req.getUsername(), req.getPassword());
    if (validUser == null) throw new InvalidAuthException("Invalid username or password");
     return new Principal(validUser.getUser_Id(), validUser.getUsername(), validUser.getRole_id());
    }
    public List<User> getAllUsers(){
        return userDAO.findAll();
    }

public List<User> getAllApplicants(){return userDAO.findAllApplicants();}


    public boolean isValidUsername(String username) {
        return username.matches("^(?=[a-zA-Z0-9._]{8,20}$)(?!.*[_.]{2})[^_.].*[^_.]$");
    }
    public boolean isUniqueUsername(String username) {
        List<String> usernames = userDAO.findAllUsernames();
        return usernames.contains(username);
    }
    public boolean isSamePassword(String password1, String password2){
    return password1.equals(password2);
    }
    public boolean isEmailUnique(String email){
        List<String> emails=userDAO.findAllEmails();
        return emails.contains(email);
    }
    public boolean isValidPassword(String password) {
      return password.matches("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$");
    }
    public List<String> findAllApplicantUsernames(){
        return userDAO.findAllApplicantUsernames();
    }
}