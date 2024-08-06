package package1;

import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static package1.Course.*;
import static package1.Student.*;
import static package1.Grade.*;
import static package1.Inscription.*;

public class Main {

    public static Connection openConnection() {

        String url = "jdbc:mysql://localhost:3306/RiwiAcademyDB";
        String user = "root";
        String password = "tupassword";

        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            return connection;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) throws SQLException {
        JOptionPane.showMessageDialog(null, "Welcome Administrator");
        while (true) {
            String[] options = {"Manage Students", "Manage Courses", "Manage Inscriptions", "Manage Grades", "Exit"};
            int choice = JOptionPane.showOptionDialog(null,
                    "=== Main Menu ===",
                    "Main Menu",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);
            Connection c = openConnection();

            switch (choice) {
                case 0:
                    manageStudents(c);
                    break;
                case 1:
                    manageCourses(c);
                    break;
                case 2:
                    manageInscriptions(c);
                    break;
                case 3:
                    manageGrades(c);
                    break;
                case 4:
                    JOptionPane.showMessageDialog(null, "Exiting...");
                    assert c != null;
                    c.close();
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        }
    }

    private static void manageStudents(Connection c) {
        while (true) {
            String[] options = {"Add Student", "View Students", "Update Student", "Delete Student", "Main Menu"};
            int choice = JOptionPane.showOptionDialog(null,
                    "=== Manage Students ===",
                    "Manage Students",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Creating a Student");
                    String name = JOptionPane.showInputDialog("Enter the name");
                    String email = JOptionPane.showInputDialog("Enter the email");
                    Student s1 = new Student(name,email);
                    addStudent(s1, c);
                    break;
                case 1:
                    viewStudents(c);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Updating a Student");
                    int idE = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Student you want to update"));
                    int active = Integer.parseInt(JOptionPane.showInputDialog("Enter the state od the Student (1 for active, 0 for inactive)"));
                    String nameE = JOptionPane.showInputDialog("Enter the name of the Student");
                    String emailE = JOptionPane.showInputDialog("Enter the email of the Student");
                    editStudent(idE, active, nameE, emailE, c);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Deleting a Student");
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Student you want to delete"));
                    deleteStudent(id, c);
                    break;
                case 4:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        }
    }

    private static void manageCourses(Connection c) {
        while (true) {
            String[] options = {"Add Course", "View Courses", "Delete Course", "Main Menu"};
            int choice = JOptionPane.showOptionDialog(null,
                    "=== Manage Courses ===",
                    "Manage Courses",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Creating a Course");
                    String name = JOptionPane.showInputDialog("Enter the name");
                    Course c1 = new Course(name);
                    addCourse(c1, c);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Viewing all the Courses");
                    showCourses(c);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Deleting a Course");
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Course you want to delete"));
                    deleteCourse(id, c);
                    break;
                case 3:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        }
    }

    private static void manageInscriptions(Connection c) {
        while (true) {
            String[] options = {"Add Inscription", "View Inscriptions", "Delete Inscription", "Back to Main Menu"};
            int choice = JOptionPane.showOptionDialog(null,
                    "=== Manage Inscriptions ===",
                    "Manage Inscriptions",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Creating an Inscription");
                    int idC = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Course"));
                    int idS = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Student"));
                    if(searchInscriptionByStudent(idS, c)<3|| searchPreviousInscriptions(idC, idS,c)) {
                        Inscription i = new Inscription(idC, idS);
                        addInscription(i, c);
                    } else {
                        JOptionPane.showMessageDialog(null, "Student has reached the maximum number of inscriptions");
                    }
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "View all the Inscriptions");
                    showInscriptions(c);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Delete an Inscription");
                    if(JOptionPane.showConfirmDialog(null, "This will also erase all Grades of the Student on the Course, are you sure?") == 0){
                        int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Inscription you want to delete"));
                        deleteInscription(id, c);
                    }
                    break;
                case 3:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        }
    }

    private static void manageGrades(Connection c) {
        while (true) {
            String[] options = {"Add Grade", "View Grades", "Update Grade", "Delete Grade", "Back to Main Menu"};
            int choice = JOptionPane.showOptionDialog(null,
                    "=== Manage Grades ===",
                    "Manage Grades",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Creating a Grade");
                    int idC = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Course"));
                    int idS = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Student"));
                    int value = Integer.parseInt(JOptionPane.showInputDialog("Enter the value (0-100)"));
                    String description = JOptionPane.showInputDialog("Enter the description");
                    if(value < 0 || value > 100){
                        JOptionPane.showMessageDialog(null, "Invalid value");
                        break;
                    }
                    Grade g = new Grade(idS, idC, value, description);
                    addGrade(g, c);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "View all the Grades");
                    showGrades(c);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Updating a Grade");
                    String descriptionE = JOptionPane.showInputDialog("Enter the new description");
                    int valueE = Integer.parseInt(JOptionPane.showInputDialog("Enter the value (0-100)"));
                    int idCE = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Course"));
                    int idSE = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Student"));
                    if(valueE < 0 || valueE > 100){
                        JOptionPane.showMessageDialog(null, "Invalid value");
                        break;
                    }
                    updateGrade(descriptionE, valueE, idCE, idSE, c);
                    break;
                case 3:
                    JOptionPane.showMessageDialog(null, "Deleting a Grade");
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the id of the Grade you want to delete"));
                    deleteGrade(id, c);
                    break;
                case 4:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        }

    }

    public static void viewStudents(Connection c){
        while (true) {
            String[] options = {"View all Students", "Search Student by Id", "Search Student by email", "Go back to Students Menu"};
            int choice = JOptionPane.showOptionDialog(null,
                    "=== Student Searches ===",
                    "Student Searches",
                    JOptionPane.DEFAULT_OPTION,
                    JOptionPane.INFORMATION_MESSAGE,
                    null,
                    options,
                    options[0]);

            switch (choice) {
                case 0:
                    JOptionPane.showMessageDialog(null, "Viewing all the Students");
                    showStudents(c);
                    break;
                case 1:
                    JOptionPane.showMessageDialog(null, "Searching Student by Id");
                    int id = Integer.parseInt(JOptionPane.showInputDialog("Enter the Id of the Student you want to search for"));
                    searchStudentById(id, c);
                    break;
                case 2:
                    JOptionPane.showMessageDialog(null, "Searching Student by Id");
                    String email = JOptionPane.showInputDialog("Enter the email of the Student you want to search for");
                    searchStudentByEmail(email, c);
                    break;
                case 3:
                    return;
                default:
                    JOptionPane.showMessageDialog(null, "Invalid choice. Please try again.");
            }
        }
    }

}