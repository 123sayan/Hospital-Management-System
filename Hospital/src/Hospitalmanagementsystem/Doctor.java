package Hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

public class Doctor {
    Connection con;

    public Doctor(Connection con) {
        this.con = con;
    }

    public void viewdoctor() {
        String query = "Select *from doctors";
        try {
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet resultSet = ps.executeQuery();
            System.out.println("Doctors : ");
            System.out.println("+------------+--------------------+--------------------+");
            System.out.println("| Doctor Id  | Name               | Specialization     |");
            System.out.println("+------------+--------------------+--------------------+");
            while (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String name = resultSet.getString("Name");
                String spec = resultSet.getString("Specialization");
                System.out.printf("| %-12s|%-20s|%-20s|\n", id, name, spec);
                System.out.println("+------------+--------------------+--------------------+");
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public boolean getdoctorbuyid(int id) {
        String query = "Select *from doctors = ?";
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
