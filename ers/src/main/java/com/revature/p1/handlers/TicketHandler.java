package com.revature.p1.handlers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.revature.p1.custom_exceptions.InvalidAuthException;
import com.revature.p1.custom_exceptions.InvalidTicketException;
import com.revature.p1.dtos.Principal;
import com.revature.p1.dtos.requests.NewFilterRequest;
import com.revature.p1.dtos.requests.NewTicketRequest;
import com.revature.p1.dtos.requests.NewUpdateTicketStatusRequest;
import com.revature.p1.dtos.requests.NewUpdateTicketRequest;
import com.revature.p1.models.Ticket;
import com.revature.p1.models.User;
import com.revature.p1.services.TicketService;
import com.revature.p1.services.TokenService;
import io.javalin.http.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;


public class TicketHandler {
    private final ObjectMapper mapper;
    private final TicketService ticketService;
    private final TokenService tokenService;
    private final static Logger logger = LoggerFactory.getLogger(User.class);


    public TicketHandler(TokenService tokenService, ObjectMapper mapper, TicketService ticketService) {
        this.tokenService = tokenService;
        this.mapper = mapper;
        this.ticketService = ticketService;
    }


    public void updateTicket(Context c) throws IOException {
        NewUpdateTicketRequest req = mapper.readValue(c.req.getInputStream(), NewUpdateTicketRequest.class);
        try {


            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!ticketService.getAllPendingReimb_ids().contains(req.getReimb_id())) {
                throw new InvalidAuthException("The ticket must be pending to update.");
            }
            if (!req.getReimb_id().equals("")) {ticketService.updateTicketPayment_id(req);}
            if (!req.getType_id().equals("")) { ticketService.updateTicketType_id(req);
            }
            if (!req.getAmount().equals("")) {
               ticketService.updateTicketAmount(req);
            }
            if (!req.getDescription().equals("")) {
                ticketService.updateTicketDescription(req);
            }

            c.json("You updated a ticket, " + principal.getUsername());
            logger.info(principal.getUsername()+" updated a ticket");



        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }

    public void getAllUserTickets(Context c) {

        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            List<Ticket>tickets=ticketService.getAllUserTickets(principal.getUser_id());
            logger.info(principal.getUsername()+" has returned their tickets.");
            c.json(tickets);
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }
    public void managerFilterTickets(Context c) throws IOException {
        NewFilterRequest req = mapper.readValue(c.req.getInputStream(), NewFilterRequest.class);
        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!(principal.getRole_id().equals("089473b8-735c-11ed-a1eb-0242ac120002") || principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002"))){
                logger.info(principal.getUsername()+" attempted to see a list of tickets.");
                throw new InvalidAuthException("You are not authorized to do this.");}

            List<Ticket>tickets=ticketService.getMAllFilterTickets(req);

            logger.info(principal.getUsername()+" has returned a list of tickets.");
            c.json(tickets);
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }
    public void getFilterTickets(Context c) throws IOException {
        NewFilterRequest req = mapper.readValue(c.req.getInputStream(), NewFilterRequest.class);
        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            req.setAuthor_id(principal.getUser_id());
            List<Ticket>tickets=ticketService.getAllFilterTickets(req);
            logger.info(principal.getUsername()+" has returned a list of tickets.");
            c.json(tickets);
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }


    public void getAllTickets(Context c) throws JsonProcessingException {
        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!(principal.getRole_id().equals("089473b8-735c-11ed-a1eb-0242ac120002") || principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002"))){
            logger.info(principal.getUsername()+" attempted to see all tickets.");
                throw new InvalidAuthException("You are not authorized to do this.");}
            logger.info(principal.getUsername()+" returned all tickets");
            List<Ticket> tickets = ticketService.getAllTickets();
            c.json(tickets);
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }
    }

    public void getAllPendingTickets(Context c) {
        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");
            if (!(principal.getRole_id().equals("089473b8-735c-11ed-a1eb-0242ac120002") || principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002"))){
                logger.info(principal.getUsername()+" attempted to view all pending tickets.");
                throw new InvalidAuthException("You are not authorized to do this");}


            List<Ticket> tickets = ticketService.getAllPendingTickets();
            c.json(tickets);
            logger.info(principal.getUsername()+ " returned all pending tickets.");
        } catch (InvalidAuthException e) {
            c.status(401);
            c.json(e);
        }

    }


    public void createNewTicket(Context c) throws IOException {
        NewTicketRequest req = mapper.readValue(c.req.getInputStream(), NewTicketRequest.class);
        try {

            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidTicketException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidTicketException("Invalid token");
            if (req.getAmount() == null || req.getAmount().signum() <= 0) {
                throw new InvalidTicketException("Ticket must have an amount with no dollar sign.");
            }
            if (req.getDescription() == null || req.getDescription() == "")
                throw new InvalidTicketException("Ticket must have a description");
            if (req.getType_id().equals("L")) {
                req.setType_id("ec00720a-7357-11ed-a1eb-0242ac120002");
            } else if (req.getType_id().equals("T")) {
                req.setType_id("f0bc9206-7357-11ed-a1eb-0242ac120002");
            } else if (req.getType_id().equals("O")) {
                req.setType_id("f83bd9d8-7357-11ed-a1eb-0242ac120002");
            } else if (req.getType_id().equals("F")) {
                req.setType_id("f4a38c6c-7357-11ed-a1eb-0242ac120002");
            } else
                throw new InvalidTicketException("Must enter a type_id(case sensitive) of 'L' for lodging,'F' for food, 'T' for travel, or 'O' for other. ");
            ticketService.saveTicket(req, principal.getUser_id());
            c.status(201);
            c.json("You created a ticket, " + principal.getUsername());
            logger.info(principal.getUsername()+" created a ticket");
        } catch (InvalidTicketException e) {
            c.status(403);
            c.json(e);
        }

    }

    public void updateTicketStatus(Context c) throws IOException {
        NewUpdateTicketStatusRequest req = mapper.readValue(c.req.getInputStream(), NewUpdateTicketStatusRequest.class);
        List<String> list = ticketService.getAllPendingReimb_ids();
        try {
            String token = c.req.getHeader("authorization");
            if (token == null || token.isEmpty()) throw new InvalidAuthException("You are not signed in.");

            Principal principal = tokenService.extractRequesterDetails(token);
            if (principal == null) throw new InvalidAuthException("Invalid token");

            if (!(principal.getRole_id().equals("089473b8-735c-11ed-a1eb-0242ac120002") || principal.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002"))){
                logger.info(principal.getUsername()+" attempted to updated ticket:"+req.getReimb_id());
                throw new InvalidAuthException("You are not authorized to do this.");}
            if (req.getStatus_id().equals("A")) {
                req.setStatus_id("7d273dbc-7354-11ed-a1eb-0242ac120002");
            } else if (req.getStatus_id().equals("D")) {
                req.setStatus_id("81aa1292-7354-11ed-a1eb-0242ac120002");
            } else throw new InvalidAuthException("Must enter a status_id of 'A' for approved or 'D' for denied.");

            if (!list.contains(req.getReimb_id())) {
                throw new InvalidAuthException("Ticket has either been resolved or does not exist.");
            }
            ticketService.updateTicketStatus(req, principal.getUser_id());
            logger.info(principal.getUsername()+" resolved a ticket.");
            c.json("You resolved a ticket, "+principal.getUsername());

        }catch (InvalidAuthException e) {
            c.status(403);
            c.json(e);
        }


    }
}




