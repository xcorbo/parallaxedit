package xyz.corbolabs.parallaxedit

import javafx.scene.control.ListView

fun refreshSelection(selectedItem: Int, starsil_listview: ListView<String>, starsListRaw: MutableList<String>) {

    starsil_listview.items.clear()

    // Filter starsListRaw based on the selected layer index
    val filteredStars = starsListRaw.filter { star ->
        val parts = star.split(",")
        parts[0].toInt() == selectedItem
    }

    // Add filtered stars to the starsil_listview
    for (filteredStar in filteredStars) {
        val parts = filteredStar.split(",")
        val formattedString = "Star #: ${parts[1]}, X: ${parts[2]}, Y: ${parts[3]}"
        starsil_listview.items.add(formattedString)

    }

}