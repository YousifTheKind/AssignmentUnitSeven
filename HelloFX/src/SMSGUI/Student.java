package SMSGUI;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Student {
    // Properties for student name and ID
    private final StringProperty name;
    private final StringProperty id;

    // Constructor
    public Student(String name, String id) {
        this.name = new SimpleStringProperty(name);
        this.id = new SimpleStringProperty(id);
    }

    // Getter for name
    public String getName() {
        return name.get();
    }

    // Property getter for name
    public StringProperty nameProperty() {
        return name;
    }

    // Setter for name
    public void setName(String name) {
        this.name.set(name);
    }

    // Getter for ID
    public String getId() {
        return id.get();
    }

    // Property getter for ID
    public StringProperty idProperty() {
        return id;
    }

    // Setter for ID
    public void setId(String id) {
        this.id.set(id);
    }
}