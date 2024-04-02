module cpsc219.assignment3.assignment3_release.newassignment3 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.testng;
    requires junit;
    requires org.junit.jupiter.api;

    opens cpsc219.assignment3.assignment3_release to javafx.fxml;
    exports cpsc219.assignment3.assignment3_release;
}