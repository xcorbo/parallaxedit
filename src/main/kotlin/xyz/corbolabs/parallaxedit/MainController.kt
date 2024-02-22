package xyz.corbolabs.parallaxedit

import javafx.application.Platform
import javafx.fxml.FXML
import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.stage.Modality
import javafx.stage.Stage
import java.io.DataOutputStream
import java.io.FileOutputStream
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

    @FXML
    lateinit var help_button: Button

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
        OpenJSON(
            background_preview,
            debug_label,
            preferences,
            lp_0,
            lp_1,
            lp_2,
            lp_3,
            lp_4,
            lp_all,
            open_button_dds,
            starsil_listview
        )
    }

    @FXML
    private fun onLP0ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 0, starsil_listview)
        refreshSelection(0, starsil_listview, starsListRaw)
        layers_choicebox.selectionModel.select(0)

    }

    @FXML
    private fun onLP1ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 1, starsil_listview)
        refreshSelection(1, starsil_listview, starsListRaw)
        layers_choicebox.selectionModel.select(1)

    }

    @FXML
    private fun onLP2ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 2, starsil_listview)
        refreshSelection(2, starsil_listview, starsListRaw)
        layers_choicebox.selectionModel.select(2)

    }

    @FXML
    private fun onLP3ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 3, starsil_listview)
        refreshSelection(3, starsil_listview, starsListRaw)
        layers_choicebox.selectionModel.select(3)
    }

    @FXML
    private fun onLP4ButtonClick() {
        openPNG(background_preview, debug_label, starsListRaw, 4, starsil_listview)
        refreshSelection(4, starsil_listview, starsListRaw)
        layers_choicebox.selectionModel.select(4)
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

        // Auxiliary lists
        val distinctLayers = mutableSetOf<Int>()
        val layerItemCounts = mutableMapOf<Int, Int>()

        if (starsListRaw.isNotEmpty()) {

            // Iterate over the items and extract layer information
            starsListRaw.forEach { item ->
                val layer = item.split(",")[0].toInt()

                // Let's get the number of layers...
                val layerItemCount = layerItemCounts.getOrDefault(layer, 0) + 1
                layerItemCounts[layer] = layerItemCount
                distinctLayers.add(layer)
            }

            // Can we save? Can we pls?
            val outputStream = DataOutputStream(FileOutputStream("C:\\Users\\Corbo\\Desktop\\star2.spk"))

            try {

                // First byte, layer number
                outputStream.writeInt(Integer.reverseBytes(distinctLayers.size))

                // Second byte, offset to first image (hardcoded to 48 for now which is the ammount if the spk has 5 layers
                outputStream.writeInt(Integer.reverseBytes(48))

                // Loop to get the header done, Images in layer, Layer width, Layer Height... for all layers
                for (i in 0..distinctLayers.size - 1) {

                    layerItemCounts[i]?.let { Integer.reverseBytes(it) }?.let { outputStream.writeInt(it) }

                    if (!sd_button.isDisable) {
                        val width = 648
                        val height = 488
                        outputStream.writeShort(reverseBytesShort(width.toShort()).toInt())
                        outputStream.writeShort(reverseBytesShort(height.toShort()).toInt())
                    }
                    if (!hd_button.isDisable) {
                        val width = 648 * 2
                        val height = 488 * 2
                        outputStream.writeShort(reverseBytesShort(width.toShort()).toInt())
                        outputStream.writeShort(reverseBytesShort(height.toShort()).toInt())
                    }
                    if (!hd2_button.isDisable) {
                        val width = 648 * 4
                        val height = 488 * 4
                        outputStream.writeShort(reverseBytesShort(width.toShort()).toInt())
                        outputStream.writeShort(reverseBytesShort(height.toShort()).toInt())
                    }
                }

                // The one loop to merge them all, one loop to find them and in the darkness bind them
                for (i in 0..distinctLayers.size - 1) {
                    for (j in 0..layerItemCounts.getOrDefault(i, 0)) {
                        starsListRaw.forEach() { item ->
                            val values = item.split(",")
                            if (values[0].toInt() == i && values[1].toInt() == j) {
                                outputStream.writeShort(reverseBytesShort(values[2].toShort()).toInt())
                                outputStream.writeShort(reverseBytesShort(values[3].toShort()).toInt())

                                outputStream.writeShort(reverseBytesShort(values[4].toShort()).toInt())
                                outputStream.writeShort(reverseBytesShort(values[5].toShort()).toInt())

                                outputStream.writeShort(reverseBytesShort(values[6].toShort()).toInt())
                                outputStream.writeShort(reverseBytesShort(values[7].toShort()).toInt())
                            }
                        }
                    }
                }
            } finally {
                // Close the output stream
                outputStream.close()
            }
        }
    }

    fun reverseBytesShort(value: Short): Short {
        return ((value.toInt() and 0xFF) shl 8 or ((value.toInt() and 0xFF00) ushr 8)).toShort()
    }

    @FXML
    fun onHelpButtonClick(){

        // Find the FXML to load + get its parent
        val loader = FXMLLoader(javaClass.getResource("help-screen.fxml"))
        val helpScreenRoot = loader.load<Parent>()

        // Assign it a controller and set its stage
        val helpScreenController = loader.getController<HelpController>()
        val helpScreenStage = Stage()

        // Setting title and icons and other settings...
        helpScreenStage.title = "Help Screen"
        helpScreenStage.scene = Scene(helpScreenRoot, 600.0, 400.0)

        // Icon
        val iconPath = "icon.png"
        val iconStream = javaClass.classLoader.getResourceAsStream(iconPath)
        if (iconStream != null) {
            val iconImage = Image(iconStream)
            helpScreenStage.icons.add(iconImage)
        } else {
            System.err.println("Icon resource not found: $iconPath")
        }

        // Make the help screen a modal dialog so it stays there until you close it and prevent from interaction on mainStage
        helpScreenStage.initModality(Modality.APPLICATION_MODAL)

        helpScreenController.setHelpScreenStage(helpScreenStage)

        helpScreenStage.showAndWait()

    }

    @FXML
    fun onContextMenuRequested(){
        ContextMenuRender(starsil_listview, layers_choicebox, starsListRaw)
    }

    // END
}