package Hospitalmanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Hospitalmanagement {
    private static final String url = "jdbc:mysql://localhost:3306/hospital";
    private static final String username = "root";
    private static final String password = "Sayan";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(url, username, password);
            Paitent patient = new Paitent(con, scanner);
            Doctor doctor = new Doctor(con);
            while (true) {
                System.out.println("+-------------+--------------+--------------+");
                System.out.println("HOSPITAL MANAGEMENT SYSTEM ");
                System.out.println("+-------------+--------------+--------------+");
                System.out.println("1. Add Patient");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice :");
                System.out.println("+-------------+--------------+--------------+");
                int choice = scanner.nextInt();
                switch (choice) {
                    case 1:
                        // add patient
                        patient.addpaitent();
                        System.out.println();
                        break;
                    case 2:
                        // view patient
                        patient.viewpatient();
                        System.out.println();
                        break;
                    case 3:
                        // view doctor
                        doctor.viewdoctor();
                        System.out.println();
                        break;
                    case 4:
                        // book appointment
                        appointment(patient,doctor,con,scanner);
                        break;
                    case 5:
                        System.out.println("Thenk You For Using Hospital Management System");
                        return;
                    default:
                        System.out.println("Enter valid choice!!");
                }
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        }
    }

    public static void appointment(Paitent paitent, Doctor doctor, Connection con, Scanner sc) {
        System.out.println("Enter Patient Id :");
        int pid = sc.nextInt();
        System.out.println("Enter Doctor Id :");
        int did = sc.nextInt();
        System.out.println("Enter appointment date(YYYY-MM-DD)");
        String appointmentdate = sc.next();
        if (paitent.getpatientbyid(pid) && doctor.getdoctorbuyid(did)) {
            if (checkdoctoravailable(con,did, appointmentdate)) {
                String query = "Insert into appointments (Patient_Id,,Doctor_Id,Appointment_Date) values(?,?,?)";
                try {
                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setInt(1, pid);
                    ps.setInt(2, did);
                    ps.setString(3, appointmentdate);
                    int rows = ps.executeUpdate();
                    if (rows > 0) {
                        System.out.println("Appointment Booked Sucessfully");
                    } else {
                        System.out.println("Failed to Book your Appopintment");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Doctor not availableon this date");
            }
        } else {
            System.out.println("Either patient or doctor doesn't exist");
        }
    }
    public static boolean checkdoctoravailable(Connection con,int did,String appointmentdate)
    {
        String query="select count (*) from appointments where Doctor_Id=? and Appointment_Date=?";
        try {
            PreparedStatement ps=con.prepareStatement(query);
            ps.setInt(1, did);
            ps.setString(2, appointmentdate);
            ResultSet rs=ps.executeQuery();
            if(rs.next())
            {
                int count=rs.getInt(1);
                if(count==0)
                {
                    return true;
                }
                else
                {
                    return true;
                }
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
