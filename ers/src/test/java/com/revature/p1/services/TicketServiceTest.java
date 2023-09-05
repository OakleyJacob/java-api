package com.revature.p1.services;

import com.revature.p1.daos.TicketDAO;
import com.revature.p1.daos.UserDAO;
import com.revature.p1.dtos.requests.NewTicketRequest;
import com.revature.p1.dtos.requests.NewUserRequest;
import com.revature.p1.models.Ticket;
import com.revature.p1.models.User;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TicketServiceTest {
    private TicketService sut;
    private final TicketDAO mockTicketDAO = Mockito.mock(TicketDAO.class);
}

