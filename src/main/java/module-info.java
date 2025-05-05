module com.hexapawn.hexapawn {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.hexapawn.hexapawn to javafx.fxml;
    exports com.hexapawn.hexapawn;
}