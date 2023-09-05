package com.revature.p1.services;

import com.revature.p1.custom_exceptions.InvalidAuthException;
import com.revature.p1.daos.TicketDAO;
import com.revature.p1.dtos.requests.NewFilterRequest;
import com.revature.p1.dtos.requests.NewTicketRequest;
import com.revature.p1.dtos.requests.NewUpdateTicketStatusRequest;
import com.revature.p1.dtos.requests.NewUpdateTicketRequest;
import com.revature.p1.models.Ticket;


import java.sql.*;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class TicketService {
    private final TicketDAO ticketDAO;

    public TicketService(TicketDAO ticketDAO) {
        this.ticketDAO = ticketDAO;
    }

    public void saveTicket(NewTicketRequest req, String author_id) {




            Ticket createdTicket = new Ticket(UUID.randomUUID().toString(), req.getAmount(), Timestamp.from(Instant.now()), null, req.getDescription(), null, author_id, null, "76635024-7354-11ed-a1eb-0242ac120002", req.getType_id(), req.getPayment_id());
            ticketDAO.save(createdTicket);

        }





    public List<Ticket> getAllTickets() {

        return ticketDAO.findAll();
    }
public List<String> getAllPendingReimb_ids(){
        return ticketDAO.findAllPendingReimb_ids();
}



    public List<Ticket> getAllPendingTickets() {
        return ticketDAO.findAllPendingTickets();
    }

    public void updateTicketStatus(NewUpdateTicketStatusRequest req, String resolver_id) {
      /*  List<String> list = ticketDAO.findAllPendingReimb_ids();
        if (req.getStatus_id().equals("A")) {
            req.setStatus_id("7d273dbc-7354-11ed-a1eb-0242ac120002");
        } else if (req.getStatus_id().equals("D")) {
            req.setStatus_id("81aa1292-7354-11ed-a1eb-0242ac120002");
        } else throw new InvalidAuthException("Must enter a status_id of 'A' for approved or 'D' for denied.");

        if (!list.contains(req.getReimb_id())) {
            throw new InvalidAuthException("Ticket has either been resolved or does not exist.");
        }
*/

        Ticket createdTicket = new Ticket(req.getReimb_id(), null, null, Timestamp.from(Instant.now()), null, null, null, resolver_id, req.getStatus_id(), null, null);
        ticketDAO.updateTicketStatus(createdTicket);
    }

    public List<Ticket> getMAllFilterTickets(NewFilterRequest req) {
        if (req.getStatus_id().equals("A")) {
            req.setStatus_id("7d273dbc-7354-11ed-a1eb-0242ac120002");
        } else if (req.getStatus_id().equals("D")) {
            req.setStatus_id("81aa1292-7354-11ed-a1eb-0242ac120002");
        } else if (req.getStatus_id().equals("P")) {
            req.setStatus_id("76635024-7354-11ed-a1eb-0242ac120002");
        }
        if (req.getType_id().equals("L")) {
            req.setType_id("ec00720a-7357-11ed-a1eb-0242ac120002");
        } else if (req.getType_id().equals("T")) {
            req.setType_id("f0bc9206-7357-11ed-a1eb-0242ac120002");
        } else if (req.getType_id().equals("F")) {
            req.setType_id("f4a38c6c-7357-11ed-a1eb-0242ac120002");
        } else if (req.getType_id().equals("O")) {
            req.setType_id("f83bd9d8-7357-11ed-a1eb-0242ac120002");
        }
        List<Ticket> filterTickets = ticketDAO.getMFilterTickets(req);
     return filterTickets;
}
    public void updateTicketType_id(NewUpdateTicketRequest req) {



            ticketDAO.updateTicketType_id(req);
        }

    public void updateTicketAmount(NewUpdateTicketRequest req){
        ticketDAO.updateTicketAmount(req);
    }
    public void updateTicketDescription(NewUpdateTicketRequest req){
        ticketDAO.updateTicketDescription(req);
    }
    public void updateTicketPayment_id(NewUpdateTicketRequest req){
        ticketDAO.updateTicketPayment_id(req);
    }
    public List<Ticket> getAllFilterTickets(NewFilterRequest req){
        if (req.getStatus_id().equals("A")) {
            req.setStatus_id("7d273dbc-7354-11ed-a1eb-0242ac120002");
        } else if (req.getStatus_id().equals("D")) {
            req.setStatus_id("81aa1292-7354-11ed-a1eb-0242ac120002");
        } else if (req.getStatus_id().equals("P")) {
            req.setStatus_id("76635024-7354-11ed-a1eb-0242ac120002");}
         if (req.getType_id().equals("L")) {
             req.setType_id("ec00720a-7357-11ed-a1eb-0242ac120002");
         } else if (req.getType_id().equals("T")) {
             req.setType_id("f0bc9206-7357-11ed-a1eb-0242ac120002");
         } else if (req.getType_id().equals("F")) {
             req.setType_id("f4a38c6c-7357-11ed-a1eb-0242ac120002");}
         else if (req.getType_id().equals("O")) {
             req.setType_id("f83bd9d8-7357-11ed-a1eb-0242ac120002");}
List<Ticket> filterTickets = ticketDAO.getFilterTickets(req);

        return filterTickets;
    }
    public List<Ticket> getAllUserTickets(String user_id){
    List<Ticket> allUserTickets = ticketDAO.findAllUserTickets(user_id);
        return allUserTickets;
    }
}

