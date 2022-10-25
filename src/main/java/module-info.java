module com.example.isned {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.isned to javafx.fxml;
    exports com.example.isned;
}