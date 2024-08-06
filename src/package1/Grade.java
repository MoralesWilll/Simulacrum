package package1;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Grade {

    private int idGrade;
    private String description;
    private int value;
    private int idCourse;
    private int idStudent;

    public Grade(int idStudent, int idCourse, int value, String description) {
        this.idStudent = idStudent;
        this.idCourse = idCourse;
        this.value = value;
        this.description = description;
    }

    public Grade(int idGrade, String description, int value, int idCourse, int idStudent) {
        this.idGrade = idGrade;
        this.description = description;
        this.value = value;
        this.idCourse = idCourse;
        this.idStudent = idStudent;
    }

    public int getIdGrade() {
        return idGrade;
    }

    public void setIdGrade(int idGrade) {
        this.idGrade = idGrade;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public int getIdStudent() {
        return idStudent;
    }

    public void setIdStudent(int idStudent) {
        this.idStudent = idStudent;
    }

    public static void deleteGrade(int id, Connection c){
        String sql = "DELETE from RiwiAcademyDB.grade WHERE idGrade = ?";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, id);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Grade deleted");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void deleteGradesByIds(int idC, int idS, Connection c){
        String sql = "DELETE * from RiwiAcademyDB.grade WHERE idCourse = ? AND idStudent = ?";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, idC);
            pstmt.setInt(1, idS);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addGrade(Grade g, Connection c){
        String sql = "INSERT into RiwiAcademyDB.grade (description, value, idCourse, idStudent) VALUES (?, ?, ?, ?)";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setString(1, g.getDescription());
            pstmt.setInt(2, g.getValue());
            pstmt.setInt(3, g.getIdCourse());
            pstmt.setInt(4, g.getIdStudent());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Grade created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void updateGrade(String description, int value, int idC, int idS, Connection c){
        String sql = "UPDATE RiwiAcademyDB.grade SET description = ?, value = ?, idCourse = ?, idStudent = ?, idGrade = ?";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setString(1, description);
            pstmt.setInt(2, value);
            pstmt.setInt(3, idC);
            pstmt.setInt(4, idS);
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Grade updated");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showGrades(Connection c){
        String sql = "SELECT * from RiwiAcademyDB.grade";
        StringBuilder result = new StringBuilder();
        try(PreparedStatement pstmt = c.prepareStatement(sql)) {
            ResultSet r = pstmt.executeQuery();
            while (r.next()) {
                int id = r.getInt("idGrade");
                String description = r.getString("description");
                int value = r.getInt("value");
                int idC = r.getInt("idCourse");
                int idS = r.getInt("idStudent");
                Grade g = new Grade(id, description, value, idC, idS);
                result.append(g);
            }
            JOptionPane.showMessageDialog(null, result.toString());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return  "Description= " + description +
                ", idGrade= " + idGrade +
                ", value= " + value +
                ", idCourse= " + idCourse +
                ", idStudent= " + idStudent +
                '\n';
    }
}
