package xyz.corbolabs.parallaxedit

import javafx.application.Application
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.stage.Stage

class MainApplication : Application() {

    override fun start(stage: Stage) {
        val fxmlLoader = FXMLLoader(MainApplication::class.java.getResource("main-screen.fxml"))
        val scene = Scene(fxmlLoader.load(), 820.0, 550.0)
        stage.title = "SC: Remastered Parallax Editor -- (Filename.here)"
        stage.scene = scene
        stage.show()
        //LMAO
    }
}

fun main() {
    Application.launch(MainApplication::class.java)
}