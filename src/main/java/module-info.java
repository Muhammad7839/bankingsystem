module com.bank {
    requires javafx.controls;
    requires javafx.fxml;
    requires transitive javafx.graphics;

    opens com.bank.Controllers to javafx.fxml;

    exports com.bank;
}
