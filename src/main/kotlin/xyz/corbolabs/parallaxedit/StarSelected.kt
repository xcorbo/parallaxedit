package xyz.corbolabs.parallaxedit

import javafx.scene.control.ListView
import javafx.scene.shape.Rectangle

fun StarSelected(border: Rectangle, layer: String, star: String, starsil_listview: ListView<String>) {

    // Select item from list when star is clicked
    border.isVisible = !border.isVisible
    val selectionModel = starsil_listview.selectionModel

    // Clear any previous selections
    selectionModel.clearSelection()

    // Select the item at the specified index
    selectionModel.select(star.toInt())

}