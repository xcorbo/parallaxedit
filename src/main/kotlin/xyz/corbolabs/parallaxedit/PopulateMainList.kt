package xyz.corbolabs.parallaxedit

import javafx.scene.control.ListView

fun populateMainList(starsListRaw: MutableList<String>, stars_listview: ListView<String>) {

    stars_listview.items.clear()

    // Add filtered stars to the stars_listview
    for (items in starsListRaw) {
        val parts = items.split(",")
        val formattedString =
                    "Layer: ${parts[0]}, " +
                    "Star #: ${parts[1]}, " +
                    "x: ${parts[2]}, " +
                    "y: ${parts[3]}, " +
                    "dds x: ${parts[4]}, " +
                    "dds y: ${parts[5]}" +
                    "width: ${parts[6]}" +
                    "height: ${parts[7]}"
        stars_listview.items.add(formattedString)

    }

}