module xyz.corbolabs.parallaxedit {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;
    requires java.prefs;


    opens xyz.corbolabs.parallaxedit to javafx.fxml;
    exports xyz.corbolabs.parallaxedit;
}