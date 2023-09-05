package com.revature.p1.daos;

import com.revature.p1.dtos.requests.NewFilterRequest;
import com.revature.p1.dtos.requests.NewUpdateTicketRequest;
import com.revature.p1.models.Ticket;
import com.revature.p1.utils.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TicketDAO implements CrudDAO<Ticket> {

    @Override
    public void save(Ticket obj) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement ps = con.prepareStatement("INSERT INTO myschema.tickets(amount, submitted, resolved, description, receipt, reimb_id, resolver_id, status_id, type_id, author_id, payment_id) VALUES (?,?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setBigDecimal(1, obj.getAmount());
            ps.setTimestamp(2, obj.getSubmitted());
            ps.setTimestamp(3, obj.getResolved());
            ps.setString(4, obj.getDescription());
            ps.setBytes(5, obj.getReceipt());
            ps.setString(6, obj.getReimb_id());
            ps.setString(7, obj.getResolver_id());
            ps.setString(8, obj.getStatus_id());
            ps.setString(9, obj.getType_id());
            ps.setString(10, obj.getAuthor_id());
            ps.setString(11, obj.getPayment_id());


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Ticket> findAll() {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets");
            ResultSet rs = ps.executeQuery();
//public Ticket(String reimb_id, BigDecimal amount, Timestamp submitted, Timestamp resolved, String description, byte[] receipt, String author_id, String resolver_id, String status_id, String type_id, String payment_id)
            while (rs.next()) {
                Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                tickets.add(currentTicket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    public List<Ticket> findAllPendingTickets() {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE status_id ='76635024-7354-11ed-a1eb-0242ac120002'");

            ResultSet rs = ps.executeQuery();

//public Ticket(String reimb_id, BigDecimal amount, Timestamp submitted, Timestamp resolved, String description, byte[] receipt, String author_id, String resolver_id, String status_id, String type_id, String payment_id)
            while (rs.next()) {
                Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                tickets.add(currentTicket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }

    @Override
    public void delete(Ticket obj) {

    }

    @Override
    public void update(Ticket obj) {

    }

    public void updateTicketStatus(Ticket obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            if (obj.getStatus_id().equals("7d273dbc-7354-11ed-a1eb-0242ac120002")) {
                PreparedStatement ps = con.prepareStatement("update myschema.tickets set status_id  = '7d273dbc-7354-11ed-a1eb-0242ac120002', resolver_id = '" + obj.getResolver_id() + "',resolved = '" + obj.getResolved() + "' where reimb_id ='" + obj.getReimb_id() + "';");
                ps.executeUpdate();
            }
            if (obj.getStatus_id().equals("81aa1292-7354-11ed-a1eb-0242ac120002")) {
                PreparedStatement ps = con.prepareStatement("update myschema.tickets set status_id  = '81aa1292-7354-11ed-a1eb-0242ac120002', resolver_id = '" + obj.getResolver_id() + "',resolved = '" + obj.getResolved() + "' where reimb_id ='" + obj.getReimb_id() + "';");
                ps.executeUpdate();

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public List<String> findAllPendingReimb_ids() {
        List<String> reimb_id = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (reimb_id) from myschema.tickets WHERE status_id LIKE '76635024-7354-11ed-a1eb-0242ac120002'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentreimb_id = rs.getString("reimb_id");
                reimb_id.add(currentreimb_id);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return reimb_id;
    }

    public List<Ticket> findAllUserTickets(String user_id) {
        List<Ticket> tickets = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + user_id + "'");

            ResultSet rs = ps.executeQuery();


            while (rs.next()) {
                Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                        rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                tickets.add(currentTicket);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tickets;
    }


    @Override
    public Ticket findById() {
        return null;
    }

    public List<Ticket> getFilterTickets(NewFilterRequest req) {
        List<Ticket> tickets = new ArrayList<>();
        {

            try (Connection con = ConnectionFactory.getInstance().getConnection()) {
                if (req.getType_id().equals("") && req.getStatus_id().equals("")) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }
                }


                if (!req.getType_id().equals("") && (req.getStatus_id().equals(""))) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'AND type_id = '" + req.getType_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }


                }
                if (!req.getStatus_id().equals("") && (req.getType_id().equals(""))) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'AND status_id = '" + req.getStatus_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }

                } else {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'AND status_id = '" + req.getStatus_id() + "'AND type_id = '" + req.getType_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return tickets;

        }
    }

    public List<Ticket> getMFilterTickets(NewFilterRequest req) {
        List<Ticket> tickets = new ArrayList<>();

        if (!req.getAuthor_id().equals("")) {
            try (Connection con = ConnectionFactory.getInstance().getConnection()) {
                if (req.getType_id().equals("") && req.getStatus_id().equals("")) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }
                }


                if (!req.getType_id().equals("") && (req.getStatus_id().equals(""))) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'AND type_id = '" + req.getType_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }


                }
                if (!req.getStatus_id().equals("") && (req.getType_id().equals(""))) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'AND status_id = '" + req.getStatus_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }

                } else {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE author_id ='" + req.getAuthor_id() + "'AND status_id = '" + req.getStatus_id() + "'AND type_id = '" + req.getType_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


            return tickets;
        } else if (req.getAuthor_id().equals("")) {
            try (Connection con = ConnectionFactory.getInstance().getConnection()) {

                if (req.getType_id().equals("") && req.getStatus_id().equals("")) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }
                }


                if (!req.getType_id().equals("") && (req.getStatus_id().equals(""))) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE type_id = '" + req.getType_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }


                }
                if (!req.getStatus_id().equals("") && (req.getType_id().equals(""))) {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE status_id = '" + req.getStatus_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }

                } else {
                    PreparedStatement ps = con.prepareStatement("SELECT * from myschema.tickets WHERE status_id = '" + req.getStatus_id() + "'AND type_id = '" + req.getType_id() + "'");

                    ResultSet rs = ps.executeQuery();
                    while (rs.next()) {
                        Ticket currentTicket = new Ticket(rs.getString("reimb_id"), rs.getBigDecimal("amount"), rs.getTimestamp("submitted"), rs.getTimestamp("resolved"), rs.getString("description"), rs.getBytes("receipt"),
                                rs.getString("author_id"), rs.getString("resolver_id"), rs.getString("status_id"), rs.getString("type_id"), rs.getString("payment_id"));
                        tickets.add(currentTicket);
                    }

                }


            } catch (SQLException e) {
                throw new RuntimeException(e);
            }


        }
        return tickets;
    }

    public void updateTicketType_id(NewUpdateTicketRequest req) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            if (req.getType_id().equals("L")) {
                PreparedStatement ps = con.prepareStatement("update myschema.tickets set type_id  = 'ec00720a-7357-11ed-a1eb-0242ac120002' where reimb_id ='" + req.getReimb_id() + "';");
                ps.executeUpdate();
            }
            if (req.getType_id().equals("T")) {
                PreparedStatement ps = con.prepareStatement("update myschema.tickets set type_id  = 'f0bc9206-7357-11ed-a1eb-0242ac120002' where reimb_id ='" + req.getReimb_id() + "';");
                ps.executeUpdate();
            }
            if (req.getType_id().equals("F")) {
                PreparedStatement ps = con.prepareStatement("update myschema.tickets set type_id  = 'f4a38c6c-7357-11ed-a1eb-0242ac120002' where reimb_id ='" + req.getReimb_id() + "';");
                ps.executeUpdate();
            }
            if (req.getType_id().equals("O")) {
                PreparedStatement ps = con.prepareStatement("update myschema.tickets set type_id  = 'f83bd9d8-7357-11ed-a1eb-0242ac120002' where reimb_id ='" + req.getReimb_id() + "';");
                ps.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }

    public void updateTicketAmount(NewUpdateTicketRequest req) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("update myschema.tickets set amount  = '"+req.getAmount()+"' where reimb_id ='" + req.getReimb_id() + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTicketDescription(NewUpdateTicketRequest req) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("update myschema.tickets set description  = '"+req.getDescription()+"' where reimb_id ='" + req.getReimb_id() + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    public void updateTicketPayment_id(NewUpdateTicketRequest req) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("update myschema.tickets set payment_id  = '"+req.getPayment_id()+"' where reimb_id ='" + req.getReimb_id() + "';");
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}



