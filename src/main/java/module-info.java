module com.example.rentcar {
    requires javafx.controls;
    requires javafx.fxml;

    opens viewController to javafx.fxml;
    exports viewController;
}