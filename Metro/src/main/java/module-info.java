module com.example.metro {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.metro to javafx.fxml;
    exports com.example.metro;
}