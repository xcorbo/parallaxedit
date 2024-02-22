package xyz.corbolabs.parallaxedit

import javafx.scene.control.ListView

fun AddStarFromContext(
    selectedLayer: Int,
    starsListRaw: MutableList<String>,
    starsil_listview: ListView<String>
) {

    val lastExistingItem = starsil_listview.items[starsil_listview.items.size - 1]
    val nextNumber = extractStarNumber(lastExistingItem) + 1
    starsListRaw.add("$selectedLayer,$nextNumber,0,0,0,0,0,0")
    refreshSelection(selectedLayer, starsil_listview, starsListRaw)
}

fun EditStarFromContext(
    layer: String,
    star: String,
    x: String,
    y: String,
    ddsX: String,
    ddsY: String,
    width: String,
    height: String,
    starsil_listview: ListView<String>
) {

    // Find the index of the star in the starsListRaw
    val index = starsListRaw.indexOfFirst {
        val values = it.split(",")
        values[0] == layer && values[1] == star
    }

    // If the star is found, update its position
    if (index != -1) {
        val currentStar = starsListRaw[index]
        val values = currentStar.split(",").toMutableList()
        values[2] = x
        values[3] = y
        values[4] = ddsX
        values[5] = ddsY
        values[6] = width
        values[7] = height
        val updatedStar = values.joinToString(",")
        starsListRaw[index] = updatedStar
        refreshSelection(layer.toInt(), starsil_listview, starsListRaw)
    }

}

fun DuplicateStarFromContext(
    selectedLayer: Int,
    selectedItem: String,
    starsListRaw: MutableList<String>,
    starsil_listview: ListView<String>
) {

    val selectedItemInt = extractStarNumber(selectedItem)
    val lastExistingItem = starsil_listview.items[starsil_listview.items.size - 1]
    val nextNumber = extractStarNumber(lastExistingItem) + 1

    // Find the index of the star in the starsListRaw
    val index = starsListRaw.indexOfFirst {
        val values = it.split(",")
        values[0] == selectedLayer.toString() && values[1] == selectedItemInt.toString()
    }

    val currentStar = starsListRaw[index]
    val valuesToWrite = currentStar.split(",").toMutableList()

    starsListRaw.add("$selectedLayer,$nextNumber,${valuesToWrite[2]},${valuesToWrite[3]},${valuesToWrite[4]},${valuesToWrite[5]},${valuesToWrite[6]},${valuesToWrite[7]}")
    refreshSelection(selectedLayer, starsil_listview, starsListRaw)
}

fun DeleteStarFromContext(
    selectedLayer: Int,
    selectedItem: String,
    starsListRaw: MutableList<String>,
    starsil_listview: ListView<String>
) {

    val selectedItemInt = extractStarNumber(selectedItem)

    // Find the index of the star in the starsListRaw
    val index = starsListRaw.indexOfFirst {
        val values = it.split(",")
        values[0] == selectedLayer.toString() && values[1] == selectedItemInt.toString()
    }

    // If the star is found, update its position
    if (index != -1) {
        starsListRaw.removeAt(index)
        refreshSelection(selectedLayer, starsil_listview, starsListRaw)
    }

}

fun extractStarNumber(input: String): Int {
    // Find the index of "Star #:"
    val starIndex = input.indexOf("Star #:")

    // Find the index of the first comma after "Star #:"
    val commaIndex = input.indexOf(',', starIndex)

    // Extract the substring between "Star #:" and the first comma
    val starNumberString = input.substring(starIndex + 7, commaIndex).trim()

    // Parse the extracted substring to an integer and return it
    return starNumberString.toInt()
}