package xyz.corbolabs.parallaxedit

import javafx.geometry.Rectangle2D
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane

fun viewAllStars(
    background_preview: AnchorPane,
    starsListRaw: MutableList<String>,
) {

    val imageViews = mutableListOf<ImageView>()

    for (i in 0..4) {
        for (items in starsListRaw) {
            val values = items.split(",")

            // Finding the path in the JSON
            val layerToDisplay = jsonObject.getJSONArray("multi_frame_images")
                .getJSONObject(i)
                .getString("path")

            image = Image(layerToDisplay)

            if (values[0] == i.toString()) {
                val imageView = ImageView(image)
                val viewportRect =
                    Rectangle2D(values[4].toDouble(), values[5].toDouble(), values[6].toDouble(), values[7].toDouble())
                imageView.viewport = viewportRect

                imageView.fitWidth = values[6].toDouble()
                imageView.fitHeight = values[7].toDouble()

                // Set the layoutX and layoutY to control the position
                imageView.layoutX = values[2].toDouble()
                imageView.layoutY = values[3].toDouble()

                imageViews.add(imageView)

            }
        }
    }

    background_preview.children.setAll(imageViews)
}