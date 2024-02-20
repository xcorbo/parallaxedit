package xyz.corbolabs.parallaxedit

import javafx.geometry.Rectangle2D
import javafx.scene.control.ListView
import javafx.scene.image.Image
import javafx.scene.image.ImageView
import javafx.scene.layout.AnchorPane
import javafx.scene.layout.StackPane
import javafx.scene.paint.Color
import javafx.scene.shape.Rectangle

fun refreshStarsViewport(
    background_preview: AnchorPane,
    starsListRaw: MutableList<String>,
    image: Image,
    selectionValue: Int,
    starsil_listview: ListView<String>
) {

    val stackPanes = mutableListOf<StackPane>()

    for (items in starsListRaw) {
        val values = items.split(",")

        if (values[0] == selectionValue.toString()) {
            val imageView = ImageView(image)
            val viewportRect = Rectangle2D(
                values[4].toDouble(),
                values[5].toDouble(),
                values[6].toDouble(),
                values[7].toDouble()
            )

            imageView.viewport = viewportRect

            imageView.fitWidth = values[6].toDouble() //width
            imageView.fitHeight = values[7].toDouble() //height


            // Create a Rectangle to add a green selection border
            val border = Rectangle(imageView.fitWidth, imageView.fitHeight)
            border.stroke = Color.GREEN
            border.strokeWidth = 1.0
            border.fill = null // Set fill to null to make it transparent
            border.isVisible = false

            // Create a StackPane to hold the ImageView and the Rectangle
            val stackPane = StackPane()
            stackPane.children.addAll(border, imageView)

            // Set the alignment of the ImageView within the StackPane to TOP_LEFT
            StackPane.setAlignment(imageView, javafx.geometry.Pos.TOP_LEFT)

            // Set the layoutX and layoutY to control the position
            stackPane.layoutX = values[2].toDouble() //LayerX
            stackPane.layoutY = values[3].toDouble() //LayerY

            stackPane.setOnMouseClicked {
                StarSelected(border, values[0], values[1], starsil_listview)
            }

            MakeDraggable(stackPane, values[0], values[1], starsil_listview)

            stackPanes.add(stackPane)

        }
    }

    background_preview.children.setAll(stackPanes)
}