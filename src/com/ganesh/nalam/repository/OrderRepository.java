package com.ganesh.nalam.repository;

import com.ganesh.nalam.db.NalamDB;
import com.ganesh.nalam.model.Order;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderRepository {
    private static OrderRepository orderRepository;
    private OrderRepository(){}
    public static OrderRepository getInstance(){
        if(orderRepository==null) orderRepository=new OrderRepository();
        return orderRepository;
    }

    public boolean addOrder(long userId, String medicineName, int requestedQuantity,long currentDate) {
        String selectQuery = "select medicineId, price, expiryDate, quantity from medicines where medicineName=?";
        String insertQuery = "insert into orders (userId, medicineId, medicineName, price, quantity, expiryDate, totalAmount, orderDate) values (?, ?, ?, ?, ?, ?, ?, ?)";
        String updateQuery = "update medicines set quantity = quantity - ? where medicineId = ?";

        Connection connection = null;
        try {
            connection = NalamDB.getConnection();
            connection.setAutoCommit(false); // Start transaction

            long medicineId = -1;
            double price = 0;
            long expiryDate = 0;
            long availableQuantity = 0;
            double totalAmount = 0;

            // Step 1: Fetch medicine details and check stock
            try (PreparedStatement selectStmt = connection.prepareStatement(selectQuery)) {
                selectStmt.setString(1, medicineName);
                try (ResultSet rs = selectStmt.executeQuery()) {
                    if (rs.next()) {
                        medicineId = rs.getLong("medicineId");
                        price = rs.getDouble("price");
                        expiryDate = rs.getLong("expiryDate");
                        availableQuantity = rs.getLong("quantity");
                    } else {
                        return false; // Medicine not found
                    }
                }
            }

            // Validate stock
            if (availableQuantity < requestedQuantity) {
                System.out.println("Error: Insufficient stock for " + medicineName);
                return false;
            }

            // Step 2: Insert the order
            try (PreparedStatement insertStmt = connection.prepareStatement(insertQuery)) {
                totalAmount = price * requestedQuantity;
                insertStmt.setLong(1, userId);
                insertStmt.setLong(2, medicineId);
                insertStmt.setString(3, medicineName);
                insertStmt.setDouble(4, price);
                insertStmt.setInt(5, requestedQuantity);
                insertStmt.setLong(6, expiryDate);
                insertStmt.setDouble(7, totalAmount);
                insertStmt.setLong(8, currentDate);
                insertStmt.executeUpdate();
            }

            // Step 3: Update medicine stock
            try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                updateStmt.setInt(1, requestedQuantity);
                updateStmt.setLong(2, medicineId);
                updateStmt.executeUpdate();
            }

            connection.commit(); // Success!
            return true;

        } catch (Exception e) {
            if (connection != null) {
                try {
                    connection.rollback(); // Undo everything on error
                } catch (Exception rollbackEx) {
                    rollbackEx.printStackTrace();
                }
            }
            e.printStackTrace();
            return false;
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception closeEx) {
                    closeEx.printStackTrace();
                }
            }
        }
    }

    public List<Order> viewOrder(long id) {
        String query = "select * from orders where userId=?";
        List<Order> listMedicine = new ArrayList<>();
        try (Connection connection = NalamDB.getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setLong(1, id);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    long medicineId = rs.getLong("medicineId");
                    long orderId = rs.getLong("orderId");
                    long orderDate= rs.getLong("orderDate");
                    long quantity = rs.getLong("quantity");
                    double totalAmount= rs.getDouble("totalAmount");

                    Order order = new Order(medicineId,orderId,orderDate,quantity,totalAmount);

                    listMedicine.add(order);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listMedicine;
    }

    public List<Order> viewOrders() {
        List<Order> orderList=new ArrayList<>();
        String query="select * from orders";
        try(Connection connection=NalamDB.getConnection(); Statement statement=connection.createStatement();ResultSet rs=statement.executeQuery(query)){
            while (rs.next()){
                long medicineId= rs.getLong("medicineId");
                long orderId= rs.getLong("orderId");
                long orderDate= rs.getLong("orderDate");
                long quantity=rs.getLong("quantity");
                double totalAmount=rs.getDouble("totalAmount");
                long customerId=rs.getLong("userId");

                Order order=new Order(medicineId,orderId,orderDate,quantity,totalAmount,customerId);
                orderList.add(order);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return orderList;
    }
}
