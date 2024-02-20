package xyz.corbolabs.parallaxedit

import javafx.scene.control.ChoiceBox
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.layout.AnchorPane

fun ChangeSelectionChoiceBox(
    layers_choicebox: ChoiceBox<String>,
    starsil_listview: ListView<String>,
    background_preview: AnchorPane,
    debug_label: Label
) {
    // When the user changes the option in the select box, change the stars in layer list + call to the refresh function for that
    layers_choicebox.selectionModel.selectedIndexProperty().addListener() { _, _, newValue ->
        val selectedItem = newValue.toInt()
        refreshSelection(selectedItem, starsil_listview, starsListRaw)

        if (selectedItem != null) {
            refreshStarsViewport(background_preview, starsListRaw, image, selectedItem, starsil_listview)
            openPNG(background_preview, debug_label, starsListRaw, selectedItem, starsil_listview)

        } else {
            refreshStarsViewport(background_preview, starsListRaw, image, 0, starsil_listview)
            openPNG(background_preview, debug_label, starsListRaw, 0, starsil_listview)

        }

    }
}