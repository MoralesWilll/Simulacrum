package package1;

import javax.swing.*;
import java.sql.*;

import static package1.Course.*;
import static package1.Inscription.*;

public class Student {

    private int idStudent;
    private boolean active = true;
    private String name;
    private String email;

    public Student(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public Student(int idStudent, boolean active, String name, String email) {
        this.idStudent = idStudent;
        this.active = active;
        this.name = name;
        this.email = email;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public boolean getActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public static void deleteStudent(int id, Connection c){
        String sql = "DELETE from RiwiAcademyDB.student WHERE idStudent = ?";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student deleted");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addStudent(Student s, Connection c){
        String sql = "INSERT into RiwiAcademyDB.student (active, name, email) VALUES (?, ?, ?)";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setBoolean(1, s.getActive());
            pstmt.setString(2, s.getName());
            pstmt.setString(3, s.getEmail());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showStudents(Connection c){
        String sql = "SELECT * from RiwiAcademyDB.student";
        StringBuilder result = new StringBuilder();
        try {
            Statement s = c.createStatement();
            ResultSet r = s.executeQuery(sql);
            while (r.next()) {
                int id = r.getInt("idStudent");
                String name = r.getString("name");
                String email = r.getString("email");
                boolean active;
                if (r.getInt("active") == 1) {
                    active = true;
                } else {
                    active = false;
                }
                Student s1 = new Student(id, active ,name, email);
                result.append(s1);
            }
            JOptionPane.showMessageDialog(null, result.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void searchStudentById(int id, Connection c){
        String sql = "SELECT * from RiwiAcademyDB.student WHERE idStudent = ?";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet r = pstmt.executeQuery();
            while (r.next()) {
                int id1 = r.getInt("idStudent");
                String name = r.getString("name");
                String email = r.getString("email");
                boolean active;
                if (r.getInt("active") == 1) {
                    active = true;
                } else {
                    active = false;
                }
                Student s1 = new Student(id1, active, name, email);
                JOptionPane.showMessageDialog(null, s1 + "Is in= " + showName(searchByStudent(id, c), c));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void searchStudentByEmail(String emailS, Connection c){
        String sql = "SELECT * from RiwiAcademyDB.student WHERE email = ?";
        int id = 0;
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setString(1, emailS);
            ResultSet r = pstmt.executeQuery();
            while(r.next()) {
                int id1 = r.getInt("idStudent");
                id = id1;
                String name = r.getString("name");
                String email = r.getString("email");
                boolean active;
                if (r.getInt("active") == 1) {
                    active = true;
                } else {
                    active = false;
                }
                Student s1 = new Student(id1, active, name, email);
                JOptionPane.showMessageDialog(null, s1 + "Is in= " + showName(searchByStudent(id, c), c));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void editStudent(int id, int active, String name, String email, Connection c){
        String sql = "UPDATE RiwiAcademyDB.student SET active = ?, name = ?, email = ? WHERE idStudent = ?";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, active);
            pstmt.setString(2, name);
            pstmt.setString(3, email);
            pstmt.setInt(4, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Student updated");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return  "Name= " + name +
                ", Active= " + active +
                ", Id= " + idStudent +
                ", Email= " + email +
                '\n';
    }
}
