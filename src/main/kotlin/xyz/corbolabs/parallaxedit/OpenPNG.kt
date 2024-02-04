package xyz.corbolabs.parallaxedit

import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.stage.FileChooser
import java.lang.Exception

fun openPNG(background_preview: AnchorPane, debug_label: Label) {

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
            val image = Image(selectedFile.toURI().toString())
            val imageView = ImageView(image)
            imageView.fitWidth = background_preview.width
            imageView.fitHeight = background_preview.height
            background_preview.children.add(imageView)

        } catch (e: Exception) {

            debug_label.text = StringsEN.genErr

        }
    }
}