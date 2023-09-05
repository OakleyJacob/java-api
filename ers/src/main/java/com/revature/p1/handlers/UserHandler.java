package com.revature.p1.handlers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.*;
import com.revature.p1.custom_exceptions.InvalidAuthException;
import com.revature.p1.custom_exceptions.InvalidUserException;
import com.revature.p1.daos.UserDAO;
import com.revature.p1.dtos.Principal;
import com.revature.p1.dtos.requests.*;
import com.revature.p1.models.User;
import com.revature.p1.services.TokenService;
import com.revature.p1.services.UserService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class UserHandler {
    private final UserService userService;
    private final ObjectMapper mapper;
    private final static Logger logger = LoggerFactory.getLogger(User.class);
    private final TokenService tokenService;

    ;

    public UserHandler(UserService userService, ObjectMapper mapper, TokenService tokenService) {
        this.userService = userService;
        this.mapper = mapper;
        this.tokenService = tokenService;
    }

    public void signup(Context c) throws IOException {

        NewUserRequest req = mapper.readValue(c.req.getInputStream(), NewUserRequest.class);
        try {
            logger.info("Attempting to signup...");
            if (userService.isValidUsername(req.getUsername())) {
                if (userService.isSamePassword(req.getPassword1(), req.getPassword2())) {
                    if (userService.isValidPassword(req.getPassword1())) {
                        if (!userService.isEmailUnique(req.getEmail())) {
                            if (!userService.isUniqueUsername(req.getUsername())) {
                                userService.saveUser(req);
                                logger.info(req.getUsername() + " has been created.");
                                c.status(201);
                                c.json("You created an account, " + req.getUsername());
                            } else throw new InvalidAuthException("Username is already in use.");
                        } else throw new InvalidAuthException("Email is in use.");

                    } else
                        throw new InvalidAuthException("Password must contain a of minimum eight characters, one letter and one number:");


                } else throw new InvalidAuthException("Passwords do not match.");

            } else
                throw new InvalidAuthException("Username has to be 8-20 characters long, and the characters _ and . may not appear at the beginning, end, or in pairs.");


            ;

        } catch (InvalidAuthException e) {
            c.status(403);
            c.json(e);
            logger.info(req.getUsername() + " attempted to sign up.");
        }


    }

    public void updateUser(Context c) throws IOException {
        NewEmployeeActivation req = mapper.readValue(c.req.getInputStream(), NewEmployeeActivation.class);
        List<String> usernames = userService.findAllUsernames();
        try {
            String token = c.req.getHeader("authorization");

            if (!usernames.contains(req.getUsername()))
                throw new InvalidAuthException("You must enter a valid username.");

            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");

            if (!principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002")) {
                logger.info(principal.getUsername() + " attempted to updated ticket:" + req.getRole_id());
                throw new InvalidAuthException("You are not authorized to do this.");
            }
            List<String> list = userService.findAllApplicantUsernames();

            if ((!list.contains(req.getUsername()))){
                throw new InvalidAuthException("Must enter an applicant's username accurately. The applicant must have an inactive status.");}

            if (req.getRole_id().equals("Deny") || req.getRole_id().equals("Denied") || req.getRole_id().equals("Delete") || req.getRole_id().equals("D")) {
                c.json("You deleted a user, " + principal.getUsername());
                logger.info(principal.getUsername() + "has deleted " + req.getUsername());
                userService.deleteUser(req.getUsername());
            }
                if(!(req.getRole_id().equals("E")||req.getRole_id().equals("M")||req.getRole_id().equals("A")||req.getRole_id().equals("D")||req.getRole_id().equals("Deny"))){ throw new InvalidAuthException("Must enter a role of 'M' for manager, 'A' for admin, 'E' for employee, or one of 'Deny, Denied, or Delete' to deny and delete the record." );

            } else {
                userService.updateUser(req);
                c.json("You updated a user, " + principal.getUsername());
                logger.info(principal.getUsername() + " updated a user.");
            }

        } catch (InvalidAuthException e) {
            c.status(403);
            c.json(e);
        }


    }

    public void getAllUsers(Context c) throws JsonProcessingException {
        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!(principal.getRole_id().equals("089473b8-735c-11ed-a1eb-0242ac120002") || principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002"))) {
                logger.info(principal.getUsername() + " attempted to see all tickets.");
                throw new InvalidAuthException("You are not authorized to do this.");
            }

            List<User> users = userService.getAllUsers();
            logger.info(principal.getUsername() + " has returned a list of users.");
            c.json(users);
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }

    public void getAllApplicants(Context c) throws JsonProcessingException {
        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002")) {
                logger.info(principal.getUsername() + " attempted to see all applicants.");
                throw new InvalidAuthException("You are not authorized to do this.");
            }

            List<User> users = userService.getAllApplicants();
            logger.info(principal.getUsername() + " has returned a list of applicants.");
            c.json(users);
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }

    public void getAllUsersByUserName(Context c) {

        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");

            if (!(principal.getRole_id().equals("089473b8-735c-11ed-a1eb-0242ac120002") || principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002"))) {
                logger.info(principal.getUsername() + " attempted to see all tickets.");
                throw new InvalidAuthException("You are not authorized to do this.");
            }
            String username = c.req.getParameter("username");

            List<User> users = userService.getAllUsersByUsername(username);
            logger.info(principal.getUsername() + " has returned a list of usernames.");
            c.json(users);
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }

    public void deactivateUser(Context c) throws IOException {
        try {
            String token = c.req.getHeader("authorization");
            NewDeactivationRequest req = mapper.readValue(c.req.getInputStream(), NewDeactivationRequest.class);
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002")) {
                logger.info(principal.getUsername() + " attempted to deactivate a user.");
                throw new InvalidAuthException("You are not authorized to do this.");

            }
            List<String> actives = userService.getAllActiveUsernames();
            if(!actives.contains(req.getUsername())) throw new InvalidAuthException("This user is already inactive, or does not exist.");

            userService.deactivateUser(req);
            c.json("You deactivated a user, " + principal.getUsername());
            logger.info(principal.getUsername() + " deactivated a user.");
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }

    public void changePassword(Context c) throws IOException {
        try {
            String token = c.req.getHeader("authorization");
            NewPasswordChange req = mapper.readValue(c.req.getInputStream(), NewPasswordChange.class);
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002")) {
                logger.info(principal.getUsername() + " attempted to change a password.");
                throw new InvalidAuthException("You are not authorized to do this.");

            }
            if (userService.isSamePassword(req.getPassword1(), req.getPassword2())) {
                if (userService.isValidPassword(req.getPassword1())) {
                    userService.updatePassword(req);
                    c.status(202);
                    c.json("You have changed the user's password");
                    logger.info(principal.getUsername()+"has changed a password");

                } else
                    throw new InvalidAuthException("Password must contain a of minimum eight characters, one letter and one number:");
            } else throw new InvalidAuthException("Passwords do not match.");


        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }
}




