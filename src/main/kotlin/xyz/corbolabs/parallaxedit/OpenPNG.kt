package xyz.corbolabs.parallaxedit

import javafx.scene.control.Label
import javafx.scene.image.Image
import javafx.scene.layout.AnchorPane
import java.lang.Exception

lateinit var image: Image

fun openPNG(background_preview: AnchorPane, debug_label: Label, starsListRaw: MutableList<String>, selectedLayer: Int) {

    // Actual opening starts here
    if (jsonObject != null) {
        try {

            // Finding the path in the JSON
            val layerToDisplay = jsonObject.getJSONArray("multi_frame_images")
                .getJSONObject(selectedLayer)
                .getString("path")
            image = Image(layerToDisplay)

            refreshStarsViewport(background_preview, starsListRaw, image, selectedLayer)

        } catch (e: Exception) {

            debug_label.text = starsListRaw.toString()

        }
    }
}