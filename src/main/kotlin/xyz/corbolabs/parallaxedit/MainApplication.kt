package xyz.corbolabs.parallaxedit

import javafx.application.Application
import javafx.fxml.FXMLLoader
import javafx.scene.Scene
import javafx.scene.image.Image
import javafx.stage.Stage

class MainApplication : Application() {

    // To handle later
    companion object {
        lateinit var primaryStage: Stage
    }

    override fun start(stage: Stage) {
        // Setting Stage
        primaryStage = stage

        // Loading the proper FXML UI
        val fxmlLoader = FXMLLoader(MainApplication::class.java.getResource("main-screen.fxml"))
        val scene = Scene(fxmlLoader.load(), 820.0, 550.0)

        // Setting up init variables for scene/window
        stage.title = "SC: Remastered Parallax Editor -- (none selected)"
        stage.scene = scene

        // Icon
        val iconPath = "resources\\icon.png"
        val iconStream = ClassLoader.getSystemResourceAsStream(iconPath)
        if (iconStream != null){
            val iconImage = Image(iconStream)
            primaryStage.icons.add(iconImage)
        } else {
            System.err.println("Icon resource not found: $iconPath")
        }

        stage.show()

    }
}

fun main() {
    Application.launch(MainApplication::class.java)
}