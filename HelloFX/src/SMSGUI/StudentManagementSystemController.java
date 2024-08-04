package SMSGUI;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.util.Map;
import java.util.HashMap;

public class StudentManagementSystemController {
    // FXML annotated fields for UI components
    @FXML private TextField nameField;
    @FXML private TextField idField;
    @FXML private TableView<Student> studentTable;
    @FXML private TableColumn<Student, String> nameColumn;
    @FXML private TableColumn<Student, String> idColumn;
    @FXML private ComboBox<String> courseComboBox;
    @FXML private ComboBox<String> gradeCourseComboBox;
    @FXML private TextField gradeField;
    @FXML private TableView<CourseGrade> courseGradeTable;
    @FXML private TableColumn<CourseGrade, String> courseColumn;
    @FXML private TableColumn<CourseGrade, String> gradeColumn;
    @FXML private Label statusLabel;

    // Counter for assigning student IDs
    private int lastAssignedId = 0;

    // ObservableList to store students
    private ObservableList<Student> students = FXCollections.observableArrayList();

    // Map to store student grades
    private Map<String, ObservableList<CourseGrade>> studentGrades = new HashMap<>();

    // List of courses
    private ObservableList<String> courses = FXCollections.observableArrayList("Programming", "Math", "History", "English");

    // Initialize UI components and set up listeners
    @FXML
    public void initialize() {
        nameColumn.setCellValueFactory(cellData -> cellData.getValue().nameProperty());
        idColumn.setCellValueFactory(cellData -> cellData.getValue().idProperty());
        studentTable.setItems(students);

        courseColumn.setCellValueFactory(cellData -> cellData.getValue().courseProperty());
        gradeColumn.setCellValueFactory(cellData -> {
            int grade = cellData.getValue().getGrade();
            return new SimpleStringProperty(grade == -1 ? "Not graded" : String.valueOf(grade));
        });


        courseComboBox.setItems(courses);
        gradeCourseComboBox.setItems(courses);

        studentTable.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                updateCourseGradeTable(newSelection);
                nameField.setText(newSelection.getName());
                idField.setText(newSelection.getId());
            }
        });
    }

    // Add a new student with auto-generated ID
    @FXML
    private void handleAddStudent() {
        String name = nameField.getText().trim();

        if (name.isEmpty()) {
            showAlert("Error", "Please enter a student name.");
            return;
        }

        lastAssignedId++;
        String id = String.format("%04d", lastAssignedId); // Format ID as 4-digit number

        Student newStudent = new Student(name, id);
        students.add(newStudent);
        studentGrades.put(id, FXCollections.observableArrayList());
        clearInputFields();
        updateStatusLabel("Student added successfully. Assigned ID: " + id);
    }

    // Update selected student's information
    @FXML
    private void handleUpdateStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        if (selectedStudent == null) {
            showAlert("Error", "Please select a student to update.");
            return;
        }
    
        String name = nameField.getText().trim();
    
        if (name.isEmpty()) {
            showAlert("Error", "Please enter a student name.");
            return;
        }
    
        selectedStudent.setName(name);
        studentTable.refresh();
        clearInputFields();
        updateStatusLabel("Student updated successfully.");
    }
    
    // Enroll selected student in a course
    @FXML
    private void handleEnrollStudent() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        String selectedCourse = courseComboBox.getValue();
    
        if (selectedStudent == null) {
            showAlert("Error", "Please select a student to enroll.");
            return;
        }
    
        if (selectedCourse == null) {
            showAlert("Error", "Please select a course for enrollment.");
            return;
        }
    
        ObservableList<CourseGrade> grades = studentGrades.get(selectedStudent.getId());
        if (grades == null) {
            grades = FXCollections.observableArrayList();
            studentGrades.put(selectedStudent.getId(), grades);
        }
    
        if (grades.stream().anyMatch(cg -> cg.getCourse().equals(selectedCourse))) {
            showAlert("Error", "Student is already enrolled in this course.");
            return;
        }
    
        grades.add(new CourseGrade(selectedCourse, -1));  // Using -1 to indicate not graded
        updateCourseGradeTable(selectedStudent);
        updateStatusLabel("Student enrolled successfully in " + selectedCourse + ".");
    }
    
    // Assign grade to a student for a course
    @FXML
    private void handleAssignGrade() {
        Student selectedStudent = studentTable.getSelectionModel().getSelectedItem();
        String selectedCourse = gradeCourseComboBox.getValue();
        String gradeText = gradeField.getText().trim();
    
        if (selectedStudent == null || selectedCourse == null || gradeText.isEmpty()) {
            showAlert("Error", "Please select a student, course, and enter a grade.");
            return;
        }
    
        int grade;
        try {
            grade = Integer.parseInt(gradeText);
            if (grade < 0 || grade > 100) {
                throw new IllegalArgumentException("Grade must be between 0 and 100");
            }
        } catch (NumberFormatException e) {
            showAlert("Error", "Grade must be an integer.");
            return;
        } catch (IllegalArgumentException e) {
            showAlert("Error", e.getMessage());
            return;
        }
    
        ObservableList<CourseGrade> grades = studentGrades.get(selectedStudent.getId());
        CourseGrade courseGrade = grades.stream()
                .filter(cg -> cg.getCourse().equals(selectedCourse))
                .findFirst()
                .orElse(null);
    
        if (courseGrade == null) {
            showAlert("Error", "Student is not enrolled in this course.");
            return;
        }
    
        courseGrade.setGrade(grade);  // Now passing an int instead of a String
        courseGradeTable.refresh();
        clearInputFields();
        updateStatusLabel("Grade assigned successfully.");
    }

    // Update the course grade table for the selected student
    private void updateCourseGradeTable(Student student) {
        courseGradeTable.setItems(studentGrades.get(student.getId()));
    }

    // Clear input fields after operations
    private void clearInputFields() {
        nameField.clear();
        idField.clear();
        gradeField.clear();
        courseComboBox.getSelectionModel().clearSelection();
        gradeCourseComboBox.getSelectionModel().clearSelection();
    }

    // Display an alert dialog with given title and content
    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    // Update the status label with a message
    private void updateStatusLabel(String message) {
        statusLabel.setText(message);
    }

    // Exit the application
    @FXML
    private void handleExit() {
        System.exit(0);
    }
}