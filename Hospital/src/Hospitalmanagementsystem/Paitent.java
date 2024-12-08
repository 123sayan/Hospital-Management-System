package Hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class Paitent {
    private Connection con;
    private Scanner sc;

    public Paitent(Connection con, Scanner sc) {
        this.con = con;
        this.sc = sc;
    }

    public void addpaitent() {
        System.out.println("Enter Patient Name :");
        String name = sc.next();
        System.out.println("Enter Patient Age :");
        int age = sc.nextInt();
        System.out.println("Enter Patient Gender :");
        String gender = sc.next();
        try {
            String query = "Insert into patients (Name,Age, Gender) values(?,?,?)";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, gender);
            int result = ps.executeUpdate();
            if (result > 0) {
                System.out.println("Patient Added Successfully");
            } else {
                System.out.println("Failed to add Patient");
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public void viewpatient() {
        String query = "Select *from Patients";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("patients : ");
            System.out.println("+------------+--------------------+------------+------------+");
            System.out.println("| Patient Id | Name               | Age        | Gender     |");
            System.out.println("+------------+--------------------+------------+------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                int age = resultSet.getInt("Age");
                String gender = resultSet.getString("Gender");
                System.out.printf("| %-12s|%-20s|%-12s|%-12s|\n", id, name, age, gender);
                System.out.println("+------------+--------------------+------------+------------+");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean getpatientbyid(int id) {
        String query = "Select *from patients where id = ?";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
        return false;
    }
}
