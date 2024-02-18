package xyz.corbolabs.parallaxedit

import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import java.io.File
import java.lang.Exception
import org.json.JSONObject
import java.util.prefs.Preferences

lateinit var jsonObject: JSONObject


fun OpenJSON(
    background_preview: AnchorPane,
    debug_label: Label,
    preferences: Preferences,
    lp_0: Button,
    lp_1: Button,
    lp_2: Button,
    lp_3: Button,
    lp_4: Button,
    lp_all: Button,
    open_button_dds: Button
) {

    // Setting up FileChooser, Init, filter, icon, preferences, title, whatnot
    val JSONChooser = FileChooser()

    // Filter
    val JSONFilter = FileChooser.ExtensionFilter(StringsEN.JSONFilterName, StringsEN.JSONFilterSymbol)
    JSONChooser.extensionFilters.add(JSONFilter)

    // Last directory
    val storedDirectory = preferences.get("lastDirectory", null)
    val initialDirectory = storedDirectory?.let { File(it) }
    if (initialDirectory != null && initialDirectory.isDirectory) {
        JSONChooser.initialDirectory = initialDirectory
    }

    // Title
    JSONChooser.title = StringsEN.stageTitleCFPNG

    val selectedFile = JSONChooser.showOpenDialog(null)

    // Actual opening starts here
    if (selectedFile != null) {
        try {

            // init inputStream, shove the file into a stream for parsing
            val loadedJSON = File(selectedFile.absolutePath).readText()
            jsonObject = JSONObject(loadedJSON)

            // set to layer 0 by default
            val selectedLayer = 0
            openPNG(background_preview, debug_label, starsListRaw, selectedLayer)
            lp_0.isDisable = false
            lp_1.isDisable = false
            lp_2.isDisable = false
            lp_3.isDisable = false
            lp_4.isDisable = false
            lp_all.isDisable = false
            open_button_dds.isDisable = true

        } catch (e: Exception) {

            debug_label.text = StringsEN.errJSON

        }
    }
}