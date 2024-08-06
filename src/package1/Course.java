package package1;

import javax.swing.*;
import java.sql.*;

public class Course {

    private int idCourse;
    private String name;

    public Course(String name) {
        this.name = name;
    }

    public Course(int idCourse, String name) {
        this.idCourse = idCourse;
        this.name = name;
    }

    public int getIdCourse() {
        return idCourse;
    }

    public void setIdCourse(int idCourse) {
        this.idCourse = idCourse;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public static void deleteCourse(int id, Connection c){
        String sql = "DELETE from RiwiAcademyDB.course WHERE idCourse = ?";
        String sql1 = "SELECT * from RiwiAcademyDB.inscription";
        boolean find = false;
        try(PreparedStatement pstmt1 = c.prepareStatement(sql1)) {
            ResultSet r = pstmt1.executeQuery();
            while (r.next()) {
                int idC = r.getInt("idCourse");
                if (idC == id) {
                    find = true;
                }
            }
            } catch (SQLException e) {
            e.printStackTrace();
        }

        if(!find) {
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                pstmt.setInt(1, id);
                pstmt.executeUpdate();
                JOptionPane.showMessageDialog(null, "Course deleted");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            JOptionPane.showMessageDialog(null, "Course cant be deleted as it has inscriptions");
        }
    }

    public static void addCourse(Course co, Connection c){
        String sql = "INSERT into RiwiAcademyDB.course (name) VALUES (?)";
        try(PreparedStatement pstmt = c.prepareStatement(sql)){
            pstmt.setString(1, co.getName());
            pstmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Course created");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void showCourses(Connection c){
        String sql = "SELECT * from RiwiAcademyDB.course";
        StringBuilder result = new StringBuilder();
        try(PreparedStatement pstmt = c.prepareStatement(sql)) {
            ResultSet r = pstmt.executeQuery();
            while (r.next()) {
                int id = r.getInt("idCourse");
                String name = r.getString("name");
                Course c1 = new Course(id,name);
                result.append(c1);
            }
            JOptionPane.showMessageDialog(null, result.toString());
        } catch (SQLException e){
            e.printStackTrace();
        }
    }

    public static String showName(int[] a, Connection c){
        String names = "";
        for(int i = 0; i < 3; i++) {
            String sql = "SELECT * from RiwiAcademyDB.course WHERE idCourse = ?";
            try (PreparedStatement pstmt = c.prepareStatement(sql)) {
                if(a[i] != -1) {
                    pstmt.setInt(1, a[i]);
                    ResultSet r = pstmt.executeQuery();
                    while (r.next()) {
                        names = names + r.getString("name") + ", " ;
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return names;
    }

    @Override
    public String toString() {
        return "Name= " + name +
                ", Id= " + idCourse + '\n';
    }
}
