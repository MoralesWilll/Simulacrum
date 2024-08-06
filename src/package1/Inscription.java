package package1;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import static package1.Grade.*;

public class Inscription {

    private int idInscription;
    private int idCourse;
    private int idStundent;

    public Inscription(int idCourse, int idStundent) {
        this.idCourse = idCourse;
        this.idStundent = idStundent;
    }

    public Inscription(int idInscription, int idCourse, int idStundent) {
        this.idInscription = idInscription;
        this.idCourse = idCourse;
        this.idStundent = idStundent;
    }

    public int getIdInscription() {
        return idInscription;
    }

    public void setIdInscription(int idInscription) {
        this.idInscription = idInscription;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public int getIdStundent() {
        return idStundent;
    }

    public void setIdStundent(int idStundent) {
        this.idStundent = idStundent;
    }

    public static void deleteInscription(int id, Connection c){
        String sql = "DELETE from RiwiAcademyDB.inscription WHERE idInscription = ?";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet r = pstmt.executeQuery();
            while(r.next()){
                int idC = r.getInt("idCourse");
                int idS = r.getInt("idStudent");
                deleteGradesByIds(idC, idS, c);
            }
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inscription deleted");
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static void addInscription(Inscription i, Connection c){
        String sql = "INSERT into RiwiAcademyDB.inscription (idCourse, idStudent) VALUES (?, ?)";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, i.getIdCourse());
            pstmt.setInt(2, i.getIdStundent());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Inscription created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showInscriptions(Connection c){
        String sql = "SELECT * from RiwiAcademyDB.inscription";
        StringBuilder result = new StringBuilder();
        try(PreparedStatement pstmt = c.prepareStatement(sql)) {
            ResultSet r = pstmt.executeQuery();
            while (r.next()) {
                int id = r.getInt("idInscription");
                int idC = r.getInt("idCourse");
                int idS = r.getInt("idStudent");
                Inscription i = new Inscription(id, idC, idS);
                result.append(i);
            }
            JOptionPane.showMessageDialog(null, result.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static boolean searchPreviousInscriptions(int idC, int idS, Connection c) {
        String sql = "SELECT * from RiwiAcademyDB.grade WHERE idCourse = ? AND idStudent = ?";
        try (PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, idC);
            pstmt.setInt(2, idS);
            ResultSet r = pstmt.executeQuery();
            if (r.next()) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static int searchInscriptionByStudent(int id, Connection c){
        String sql = "SELECT * from RiwiAcademyDB.student WHERE idStudent = ?";
        int count = 0;
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setInt(1, id);
            ResultSet r = pstmt.executeQuery();
            while (r.next()) {
                count ++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static int[] searchByStudent(int id, Connection c){
        String sql = "SELECT * from RiwiAcademyDB.inscription WHERE idStudent = ?";
        int[] courses = {-1, -1, -1};
        int count = 0;
        try(PreparedStatement pstmt = c.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet r = pstmt.executeQuery();
            while (r.next()) {
                courses[count] = r.getInt("idCourse");
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return courses;
    }

    @Override
    public String toString() {
        return "Id= " + idInscription +
                ", idCourse= " + idCourse +
                ", idStundent= " + idStundent + '\n';
    }
}
