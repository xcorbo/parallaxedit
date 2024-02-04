package xyz.corbolabs.parallaxedit

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import java.util.prefs.Preferences

class MainController {

    // UI elements init
    @FXML
    private lateinit var debug_label: Label

    @FXML
    private lateinit var layers_choicebox: ChoiceBox<String>

    @FXML
    private lateinit var stars_listview: ListView<String>

    @FXML
    private lateinit var starsil_listview: ListView<String>

    @FXML
    private lateinit var save_button: Button

    @FXML
    private lateinit var open_button_dds: Button

    @FXML
    private lateinit var sd_button: Button

    @FXML
    private lateinit var hd_button: Button

    @FXML
    private lateinit var hd2_button: Button

    @FXML
    private lateinit var background_preview: AnchorPane

    @FXML
    lateinit var stars_shinning: ImageView

    // Settings Variables
    private lateinit var resolutionMode: ResolutionMode
    private val preferences = Preferences.userNodeForPackage(MainController::class.java)

    @FXML
    private fun initialize() {
        resolutionMode = ResolutionMode(save_button,
                                        open_button_dds,
                                        sd_button,
                                        hd_button,
                                        hd2_button)
        stars_shinning = ImageView()
    }

    @FXML
    private fun onOpenButtonClick() {
        openSPK(
            debug_label,
            layers_choicebox,
            stars_listview,
            starsil_listview,
            background_preview,
            stars_shinning,
            resolutionMode,
            preferences
        )
    }

    @FXML
    private fun onOpenButtonDDSClick() {
        openPNG(background_preview, debug_label, starsListRaw)
    }

    @FXML
    private fun onCloseButtonClick() {
        Platform.exit()
    }

    @FXML
    private fun onSaveButtonClick() {
        //TODO Saving...
    }

    // END
}