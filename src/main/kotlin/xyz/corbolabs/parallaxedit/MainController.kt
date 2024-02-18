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
    private lateinit var open_button: Button

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

    @FXML
    lateinit var lp_0: Button

    @FXML
    lateinit var lp_1: Button

    @FXML
    lateinit var lp_2: Button

    @FXML
    lateinit var lp_3: Button

    @FXML
    lateinit var lp_4: Button
    @FXML
    lateinit var lp_all: Button

    // Settings Variables
    private lateinit var resolutionMode: ResolutionMode
    private val preferences = Preferences.userNodeForPackage(MainController::class.java)
    private lateinit var loadedJSON: String

    @FXML
    private fun initialize() {
        resolutionMode = ResolutionMode(
            save_button,
            open_button_dds,
            sd_button,
            hd_button,
            hd2_button
        )
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
            preferences,
            open_button,
            open_button_dds
        )
    }

    @FXML
    private fun onOpenButtonDDSClick() {
        OpenJSON(background_preview, debug_label, preferences, lp_0, lp_1, lp_2, lp_3, lp_4, lp_all, open_button_dds)
    }

    @FXML
    private fun onLP0ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 0)
        refreshSelection(0, starsil_listview, starsListRaw)
    }

    @FXML
    private fun onLP1ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 1)
        refreshSelection(1, starsil_listview, starsListRaw)
    }

    @FXML
    private fun onLP2ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 2)
        refreshSelection(2, starsil_listview, starsListRaw)
    }

    @FXML
    private fun onLP3ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 3)
        refreshSelection(3, starsil_listview, starsListRaw)
    }

    @FXML
    private fun onLP4ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 4)
        refreshSelection(3, starsil_listview, starsListRaw)
    }

    @FXML
    private fun onLPAllButtonClick() {
        viewAllStars(background_preview, starsListRaw)
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