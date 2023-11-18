module xyz.corbolabs.parallaxedit {
    requires javafx.controls;
    requires javafx.fxml;
    requires kotlin.stdlib;


    opens xyz.corbolabs.parallaxedit to javafx.fxml;
    exports xyz.corbolabs.parallaxedit;
}