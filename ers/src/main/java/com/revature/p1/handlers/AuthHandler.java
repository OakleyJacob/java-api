package com.revature.p1.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p1.custom_exceptions.InvalidAuthException;
import com.revature.p1.custom_exceptions.InvalidUserException;
import com.revature.p1.dtos.Principal;
import com.revature.p1.dtos.requests.NewLoginRequest;
import com.revature.p1.models.User;
import com.revature.p1.services.TokenService;
import com.revature.p1.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.javalin.http.Context;

import java.io.IOException;


public class AuthHandler {
    private final UserService userService;
    private final TokenService tokenService;
    private final ObjectMapper mapper;
    private static final Logger logger = LoggerFactory.getLogger(User.class);

    public AuthHandler(UserService userService, TokenService tokenService, ObjectMapper mapper) {
        this.userService = userService;
        this.tokenService = tokenService;
        this.mapper = mapper;
    }

    public void authenticateUser(Context c) throws IOException {
        NewLoginRequest req = mapper.readValue(c.req.getInputStream(), NewLoginRequest.class);
        logger.info("Attempting to login...");
        try {
           Principal principal = userService.login(req);
           if(!userService.isUserActive(principal.getUser_id())){ throw new InvalidAuthException("Your account must be activated before you may log in.");
           }

           String token = tokenService.generateToken(principal);
           c.res.setHeader("authorization", token);
           c.json(principal);
           c.status(202);
           logger.info("Login successful...");

        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }


    }
}
