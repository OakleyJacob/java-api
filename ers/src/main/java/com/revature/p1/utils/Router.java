package com.revature.p1.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p1.daos.TicketDAO;
import com.revature.p1.daos.UserDAO;
import com.revature.p1.handlers.AuthHandler;
import com.revature.p1.handlers.TicketHandler;
import com.revature.p1.handlers.UserHandler;
import com.revature.p1.services.TicketService;
import com.revature.p1.services.TokenService;
import com.revature.p1.services.UserService;
import io.javalin.Javalin;

import static io.javalin.apibuilder.ApiBuilder.*;

public class Router {
    public static void router(Javalin app){
        ObjectMapper mapper = new ObjectMapper();
        JwtConfig jwtConfig = new JwtConfig();
        TokenService tokenService = new TokenService(jwtConfig);
        // User dependencies
        UserDAO userDAO = new UserDAO();
        UserService userService = new UserService(userDAO);
        UserHandler userHandler = new UserHandler(userService, mapper, tokenService);
        AuthHandler authHandler = new AuthHandler(userService, tokenService, mapper);
        //ticket dependencies
        TicketDAO ticketDAO = new TicketDAO();
        TicketService ticketService = new TicketService(ticketDAO);
        TicketHandler ticketHandler = new TicketHandler(tokenService, mapper, ticketService);



        app.routes(() ->{
            path("/users", ()  ->  {


                post(context -> userHandler.signup(context));
                post("/newticket", context -> ticketHandler.createNewTicket(context));
                get("/usertickets", ticketHandler::getAllUserTickets);
                get("/filtertickets", ticketHandler::getFilterTickets);
                patch("/updateticket", ticketHandler::updateTicket);
                });

            path("/managers", () -> {
                get(userHandler::getAllUsers);
                get("/alltickets", context -> ticketHandler.getAllTickets(context));
                get("/pendingtickets", ticketHandler::getAllPendingTickets);
                patch("/ticketstatus", ticketHandler::updateTicketStatus);
                get("/name", userHandler::getAllUsersByUserName);


                get("/filtertickets", ticketHandler::managerFilterTickets);
                });
            path("/admins", () ->{
                patch("/userupdate", userHandler::updateUser);
                get("/newusers", userHandler::getAllApplicants);
                patch("/deactivateuser", userHandler::deactivateUser);
                patch("/userpassword", userHandler::changePassword);
                    });
            path("/auth", () -> {
                post(authHandler::authenticateUser);



            });
        });
        }
    }


