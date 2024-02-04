// FileUtils.kt
package xyz.corbolabs.parallaxedit

import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import java.io.DataInputStream
import java.io.File
import java.io.FileInputStream
import java.lang.Exception
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.util.prefs.Preferences

var imgNumLay = mutableListOf<Int>()
var starsListRaw = mutableListOf<String>()
private var layNum = 0

fun openSPK(
    debug_label: Label,
    layers_choicebox: ChoiceBox<String>,
    stars_listview: ListView<String>,
    starsil_listview: ListView<String>,
    background_preview: AnchorPane,
    stars_shinning: ImageView,
    resolutionMode: ResolutionMode,
    preferences: Preferences
) {
    // Setting up FileChooser, Init, filter, icon, preferences, title, whatnot
    val fileChooser = FileChooser()

    // Filter
    val spkFilter = FileChooser.ExtensionFilter(StringsEN.spkFilterName, StringsEN.spkFilterSymbol)
    fileChooser.extensionFilters.add(spkFilter)

    // Last directory
    val storedDirectory = preferences.get("lastDirectory", null)
    val initialDirectory = storedDirectory?.let { File(it) }
    if (initialDirectory != null && initialDirectory.isDirectory) {
        fileChooser.initialDirectory = initialDirectory
    }

    // Title
    fileChooser.title = StringsEN.stageTitleCF

    val selectedFile = fileChooser.showOpenDialog(null)

    var stars: Stars

    // Actual opening starts here
    if (selectedFile != null) {
        try {
            // init inputStream, shove the file into a stream for parsing + last dir preferences
            val inputStream = DataInputStream(FileInputStream(selectedFile))
            preferences.put("lastDirectory", selectedFile.parent)

            // Resetting the scene title to the opened file + dir
            MainApplication.primaryStage.title = StringsEN.stageTitleWF + "(${selectedFile.absoluteFile})"

            // Now we start parsing the file/inputStream
            // First int is number of layers in the spk
            inputStream.readFully(Constants.bufferInt)
            layNum = ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt()

            // Second int is offset to the first star object
            inputStream.readFully(Constants.bufferInt)
            val starOffset = ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt()

            // After two ints are read, we read the header that give us the number of stars, number of images in layer and layer size
            // As a note SD = 648 x 488, HD = 1296 x 976, HD2 = 2592 x 1952
            for (i in 0 until layNum) {

                // First int of header is number of stars in this layer
                inputStream.readFully(Constants.bufferInt)
                var layers =
                    Layers(numLay = ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt())
                imgNumLay.add(i, layers.numLay)
                layers_choicebox.items.add(i, StringsEN.layerSymbolCombobox + i + " - " + imgNumLay[i])

                // First short is the layer width
                inputStream.readFully(Constants.bufferShort)
                layers = Layers(
                    layWidth = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()
                )

                // Second short is the layer height
                inputStream.readFully(Constants.bufferShort)
                layers = Layers(
                    layHeight = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()
                )

                resolutionMode.parse(layers.layHeight.toInt())

            }

            if (layers_choicebox.items.isNotEmpty()) {
                layers_choicebox.selectionModel.select(0)
            }

            for (i in 0..layNum) {
                for (j in 0..imgNumLay[i]) {

                    var indexStar = 0;

                    // Layer X Position
                    inputStream.readFully(Constants.bufferShort)
                    var stars1 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                    // Layer Y Position
                    inputStream.readFully(Constants.bufferShort)
                    var stars2 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                    // DDS X Position
                    inputStream.readFully(Constants.bufferShort)
                    var stars3 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                    // DDS Y Position
                    inputStream.readFully(Constants.bufferShort)
                    var stars4 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                    // Star Width
                    inputStream.readFully(Constants.bufferShort)
                    var stars5 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                    // Star Height
                    inputStream.readFully(Constants.bufferShort)
                    var stars6 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                    // Storing all the parsed bytes into a List
                    starsListRaw.add("$i,$j,$stars1,$stars2,$stars3,$stars4,$stars5,$stars6")

                    // Call function to populate the main list that lists all the stars
                    populateMainList(starsListRaw, stars_listview)
                }

                // Set the selection from the combobox to 0 to call the populate function for stars in layer 0, by default
                refreshSelection(0, starsil_listview, starsListRaw)
            }

            debug_label.text = layNum.toString()
            inputStream.close()
        } catch (e: Exception) {
            debug_label.text = StringsEN.genErr
        }
    }

    // When the user changes the option in the select box, change the stars in layer list + call to the refresh function for that
    layers_choicebox.selectionModel.selectedIndexProperty().addListener() { _, _, newValue ->
        val selectedItem = newValue.toInt()
        refreshSelection(selectedItem, starsil_listview, starsListRaw)

    }

}