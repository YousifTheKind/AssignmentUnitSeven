package SMSGUI;

// Importing necessary JavaFX classes
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

// Main class for the Student Management System, extending JavaFX Application
public class StudentManagementSystem extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // Load the FXML file that defines the UI layout
        Parent root = FXMLLoader.load(getClass().getResource("StudentManagementSystem.fxml"));
        
        // Set the title of the application window
        primaryStage.setTitle("Student Management System");
        
        // Create a new Scene with the loaded FXML root and set dimensions (800x600)
        primaryStage.setScene(new Scene(root, 800, 600));
        
        // Display the primary stage (main window)
        primaryStage.show();
    }

    // Main method to launch the JavaFX application
    public static void main(String[] args) {
        launch(args);
    }
}