package com.zsgs.nalam.repository;

import com.zsgs.nalam.db.NalamDB;
import com.zsgs.nalam.model.Medicine;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class MedicineRepository {

    public List<Medicine> allMedicines(long pharmacistId) {
        String viewQuery="select * from medicines where pharmacistId=?";
        List<Medicine> medicineList=new ArrayList<>();
        try (Connection connection = NalamDB.getConnection();
             PreparedStatement statement = connection.prepareStatement(viewQuery)) {
            statement.setLong(1, pharmacistId);
            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    String name = rs.getString("medicineName");
                    String desc = rs.getString("description");
                    double price = rs.getDouble("price");
                    long expiry = rs.getLong("expiryDate");
                    long quantity = rs.getLong("quantity");

                    Medicine medicine = new Medicine(name, expiry, price, desc, quantity);

                    medicineList.add(medicine);

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return medicineList;
    }

    public boolean checkMedicineAlreadyExist(String name, long quantity) {
        String query = "select medicineId from medicines where medicineName=?";
        try (Connection connection = NalamDB.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, name);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    long medicineId = rs.getLong("medicineId");
                    String updateQuery = "update medicines set quantity = quantity + ? where medicineId = ?";
                    try (PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
                        updateStmt.setLong(1, quantity);
                        updateStmt.setLong(2, medicineId);
                        int affected = updateStmt.executeUpdate();
                        return affected == 1;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean saveMedicine(Medicine medicine, long pharmacistId) {
        System.out.println(pharmacistId);
        String insertQuery = "insert into medicines(medicineName,description,price,expiryDate,quantity,pharmacistId) values(?,?,?,?,?,?)";
        try (Connection connection = NalamDB.getConnection(); PreparedStatement statement = connection.prepareStatement(insertQuery)) {
            statement.setString(1, medicine.getName());
            statement.setString(2, medicine.getDescription());
            statement.setDouble(3, medicine.getPrice());
            statement.setLong(4, medicine.getExpDate());
            statement.setLong(5, medicine.getQuantity());
            statement.setLong(6, pharmacistId);

            int checkInsert = statement.executeUpdate();// returns either 1 or 0 based on either the query is done or not
            if (checkInsert == 1) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean medicineRemove(String medicineName) {
        String query = "select medicineId from medicines where medicineName=?";
        try (Connection connection = NalamDB.getConnection(); 
             PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, medicineName);
            try (ResultSet rs = statement.executeQuery()) {
                if (rs.next()) {
                    long medicineId = rs.getLong("medicineId");
                    String deleteQuery = "delete from medicines where medicineId=?";
                    try (PreparedStatement deleteStmt = connection.prepareStatement(deleteQuery)) {
                        deleteStmt.setLong(1, medicineId);
                        int affected = deleteStmt.executeUpdate();
                        return affected == 1;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean updateQuantity(String medicineName, int updateQuantity, long pharmacistId) {
        String updateQuery = "update medicines set quantity = quantity + ? where medicineName = ? and pharmacistId = ?";
        try (Connection connection = NalamDB.getConnection();
             PreparedStatement updateStmt = connection.prepareStatement(updateQuery)) {
            updateStmt.setInt(1, updateQuantity);
            updateStmt.setString(2, medicineName);
            updateStmt.setLong(3, pharmacistId);
            int affected = updateStmt.executeUpdate();
            return affected == 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}