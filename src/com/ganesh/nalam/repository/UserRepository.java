package com.ganesh.nalam.repository;

import com.ganesh.nalam.db.NalamDB;
import com.ganesh.nalam.model.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserRepository {
    private static UserRepository userRepository;
    private UserRepository(){}
    public static UserRepository getInstance(){
    if(userRepository==null) userRepository=new UserRepository();
    return userRepository;
    }

    public boolean saved(User user) {
        String insertQuery = "insert into users(name,email,password,role) values(?,?,?,?)";
        try (Connection connection = NalamDB.getConnection(); PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getEmailId());
            statement.setString(3, user.getPassword());
            statement.setString(4, String.valueOf(user.getRole()));

            int checkInsert = statement.executeUpdate();// returns either 1 or 0 based on either the query is done or not
            if (checkInsert == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isUserAlreadyExist(String email) {
        String query = "select count(*) from users where email=?";
        try (Connection connection = NalamDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, email);
            ResultSet rs = statement.executeQuery();//return the no of rows after executing the query from DB
            while (rs.next()) {
                if (rs.getInt(1) > 0) {
                    return true;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean isValidPassword(String emailId, String password) {
        String query = "select password from users where email=?";
        try (Connection connection = NalamDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, emailId);
            ResultSet rs = statement.executeQuery();//return the no of rows after executing the query from DB
            while (rs.next()) {
                if (rs.getString("password").equals(password)) {
                    return true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public User getUserDetails(String emailId) {
        User user = new User();
        String query = "select * from users where email=?";
        try (Connection connection = NalamDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, emailId);
            ResultSet rs = statement.executeQuery();//return the no of rows after executing the query from DB
            while (rs.next()) {
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setEmailId(rs.getString("email"));
                user.setRole(User.Role.valueOf(rs.getString("role")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public List<User> retreiveUsers() {
        List<User> userList=new ArrayList<>();
        String query="select * from users where role='CUSTOMER'";
        try(Connection connection=NalamDB.getConnection(); Statement statement=connection.createStatement();ResultSet rs=statement.executeQuery(query)){
            while (rs.next()){
             long id= rs.getLong("id");
             String name= rs.getString("name");
             String email=rs.getString("email");

             User user=new User(id,email,name);
             userList.add(user);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userList;
    }
}

