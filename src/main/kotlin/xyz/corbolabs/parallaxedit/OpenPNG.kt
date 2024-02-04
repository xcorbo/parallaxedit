package xyz.corbolabs.parallaxedit

import javafx.geometry.Rectangle2D
import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import java.lang.Exception

lateinit var image: Image

fun openPNG(background_preview: AnchorPane, debug_label: Label, starsListRaw: MutableList<String>) {

    // Setting up FileChooser, Init, filter, icon, preferences, title, whatnot
    val pngChooser = FileChooser()

    // Filter
    val pngFilter = FileChooser.ExtensionFilter(StringsEN.pngFilterName, StringsEN.pngFilterSymbol)
    pngChooser.extensionFilters.add(pngFilter)

    // TODO Preferences should try to remember last opened dir here

    // Title
    pngChooser.title = StringsEN.stageTitleCFPNG

    val selectedFile = pngChooser.showOpenDialog(null)

    // Actual opening starts here
    if (selectedFile != null) {
        try {

            // init inputStream, shove the file into a stream for parsing
            image = Image(selectedFile.toURI().toString())

            refreshStarsViewport(background_preview, starsListRaw, image, 0)

            //debug_label.text = starsListRaw.toString()

        } catch (e: Exception) {

            debug_label.text = starsListRaw.toString()

        }
    }
}