package SMSGUI;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.IntegerProperty;

public class CourseGrade {  
    // Properties for course and grade
    private final StringProperty course;
    private final IntegerProperty grade;

    // Constructor
    public CourseGrade(String course, int grade) {
        this.course = new SimpleStringProperty(course);
        this.grade = new SimpleIntegerProperty(grade);
    }

    // Getter for course
    public String getCourse() {
        return course.get();
    }

    // Property getter for course
    public StringProperty courseProperty() {
        return course;
    }

    // Setter for course
    public void setCourse(String course) {
        this.course.set(course);
    }

    // Getter for grade
    public int getGrade() {
        return grade.get();
    }

    // Property getter for grade
    public IntegerProperty gradeProperty() {
        return grade;
    }

    // Setter for grade with validation 
    public void setGrade(int grade) {
        if (grade < 0 || grade > 100) {
            throw new IllegalArgumentException("Grade must be between 0 and 100");
        }
        this.grade.set(grade);
    }
}