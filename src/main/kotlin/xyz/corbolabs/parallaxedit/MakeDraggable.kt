package xyz.corbolabs.parallaxedit

import javafx.scene.control.ListView
import javafx.scene.layout.StackPane

fun MakeDraggable(stackPane: StackPane, layer: String, star: String, starsil_listview: ListView<String>) {
    var dragDeltaX = 0.0
    var dragDeltaY = 0.0

    stackPane.setOnMousePressed { event ->
        // Record the position of the mouse relative to the StackPane
        dragDeltaX = stackPane.layoutX - event.sceneX
        dragDeltaY = stackPane.layoutY - event.sceneY
    }

    stackPane.setOnMouseDragged { event ->
        // Update the position of the StackPane based on the mouse movement
        stackPane.layoutX = event.sceneX + dragDeltaX
        stackPane.layoutY = event.sceneY + dragDeltaY

        // Round layout coordinates to the nearest integer value
        val roundedX = stackPane.layoutX.toInt()
        val roundedY = stackPane.layoutY.toInt()

        // Find the index of the star in the starsListRaw
        val index = starsListRaw.indexOfFirst {
            val values = it.split(",")
            values[0] == layer && values[1] == star
        }

        // If the star is found, update its position
        if (index != -1) {
            val currentStar = starsListRaw[index]
            val values = currentStar.split(",").toMutableList()
            values[2] = roundedX.toString()
            values[3] = roundedY.toString()
            val updatedStar = values.joinToString(",")
            starsListRaw[index] = updatedStar
            refreshSelection(layer.toInt(), starsil_listview, starsListRaw)
        }

    }
}