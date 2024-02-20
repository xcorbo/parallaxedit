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

        // lmao css
        scene.getStylesheets().add(
            javaClass.getResource("styles.css").toExternalForm()
        )

        // Setting up init variables for scene/window
        stage.title = StringsEN.stageTitleNF
        stage.scene = scene

        // Icon
        val iconPath = "icon.png"
        val iconStream = javaClass.classLoader.getResourceAsStream(iconPath)
        if (iconStream != null) {
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