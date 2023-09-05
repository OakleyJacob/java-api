package com.revature.p1.daos;

import com.revature.p1.dtos.requests.NewPasswordChange;
import com.revature.p1.models.User;
import com.revature.p1.utils.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDAO implements CrudDAO<User> {

    @Override
    public void save(User obj) {

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement ps = con.prepareStatement("INSERT INTO myschema.users(user_id, username, email, password, given_name, surname, is_active, role_id) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            ps.setString(1, obj.getUser_Id());
            ps.setString(2, obj.getUsername());
            ps.setString(3, obj.getEmail());
            ps.setString(4, obj.getPassword());
            ps.setString(5, obj.getGiven_name());
            ps.setString(6, obj.getSurname());
            ps.setBoolean(7, obj.getIs_active());
            ps.setString(8, obj.getRole_id());


            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(User obj) {

    }

    @Override
    public void update(User obj) {

    }

    public void deleteUser(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("DELETE FROM myschema.users WHERE username LIKE ?");
            ps.setString(1, username);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> findAll() {
        List<User> users = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from myschema.users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User currentUser = new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("given_name"),
                        rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
                users.add(currentUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public User findById() {
        return null;
    }

    public void updateUser(User obj) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            if (obj.getRole_id().equals("046e0a7e-735c-11ed-a1eb-0242ac120002")) {
                PreparedStatement ps = con.prepareStatement("update myschema.users set role_id  = '046e0a7e-735c-11ed-a1eb-0242ac120002', is_active = true where username ='" + obj.getUsername() + "';");
                ps.executeUpdate();
            }
            if (obj.getRole_id().equals("089473b8-735c-11ed-a1eb-0242ac120002")) {
                PreparedStatement ps = con.prepareStatement("update myschema.users set role_id  = '089473b8-735c-11ed-a1eb-0242ac120002', is_active = true where username ='" + obj.getUsername() + "';");
                ps.executeUpdate();
            }
            if (obj.getRole_id().equals("0b9d895a-735c-11ed-a1eb-0242ac120002")) {
                PreparedStatement ps = con.prepareStatement("update myschema.users set role_id  = '0b9d895a-735c-11ed-a1eb-0242ac120002', is_active = true where username ='" + obj.getUsername() + "';");
                ps.executeUpdate();
            }


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<String> findAllUsernames() {
        List<String> usernames = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (username) from myschema.users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentUsername = rs.getString("username");
                usernames.add(currentUsername);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    public List<String> findAllActives() {
        List<String> activeUsers = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (user_id) from myschema.users WHERE is_active = 'true'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentuser = rs.getString("user_id");
                activeUsers.add(currentuser);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return activeUsers;
    }

    public List<User> findAllApplicants() {
        List<User> inactiveUsers = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * from myschema.users WHERE is_active = 'false'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User currentUser = new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("given_name"),
                        rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
                inactiveUsers.add(currentUser);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return inactiveUsers;
    }

    public List<String> findAllEmails() {
        List<String> emails = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (email) from myschema.users");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentemail = rs.getString("email");
                emails.add(currentemail);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return emails;
    }

    public User getUserByUserAndPassword(String username, String password) {
        User user = null;
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM myschema.users WHERE username = ? AND password = ?");
            ps.setString(1, username);
            ps.setString(2, password);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                user = new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("given_name"),
                        rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> getAllUsersByUsername(String username) {
        List<User> users = new ArrayList<>();
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT * FROM myschema.users WHERE username LIKE ?");
            ps.setString(1, username + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User user = new User(rs.getString("user_id"), rs.getString("username"), rs.getString("email"), rs.getString("password"), rs.getString("given_name"),
                        rs.getString("surname"), rs.getBoolean("is_active"), rs.getString("role_id"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public List<String> findAllApplicantUsernames() {
        List<String> usernames = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (username) from myschema.users WHERE is_active = 'false'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentusername = rs.getString("username");
                usernames.add(currentusername);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }

    public void deactivateUser(String username) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {

            PreparedStatement ps = con.prepareStatement("update myschema.users set is_active  = false where username ='" + username + "';");
            ps.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updatePassword(NewPasswordChange req) {
        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("UPDATE myschema.users set password = '" + req.getPassword1() + "'WHERE username ='" + req.getUsername()+"'");
            ps.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public List<String> findAllActiveUsernames() {
        List<String> usernames = new ArrayList<>();

        try (Connection con = ConnectionFactory.getInstance().getConnection()) {
            PreparedStatement ps = con.prepareStatement("SELECT (username) from myschema.users WHERE is_active = 'true'");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String currentusername = rs.getString("username");
                usernames.add(currentusername);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return usernames;
    }
}




