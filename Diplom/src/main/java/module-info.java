module com.example.diplom {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.diplom to javafx.fxml;
    exports com.example.diplom;
}