package xyz.corbolabs.parallaxedit

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import javafx.scene.Scene
import javafx.scene.control.ListView
import javafx.scene.image.Image
import javafx.stage.Modality
import javafx.stage.Stage


fun DeployEditBox(
    selectedLayer: Int,
    selectedItem: String,
    starsListRaw: MutableList<String>,
    starsil_listview: ListView<String>
) {

    // Find the FXML to load + get its parent
    val javaClass = MainApplication::class.java
    val loader = FXMLLoader(javaClass.getResource("edit-star.fxml"))
    val editScreenRoot = loader.load<Parent>()

    // Assign it a controller and set its stage
    val editScreenController = loader.getController<EditController>()
    val editScreenStage = Stage()

    // Setting title and icons and other settings...
    editScreenStage.title = "Editing Screen"
    editScreenStage.scene = Scene(editScreenRoot, 200.0, 165.0)

    // Icon
    val iconPath = "icon.png"
    val iconStream = javaClass.classLoader.getResourceAsStream(iconPath)
    if (iconStream != null) {
        val iconImage = Image(iconStream)
        editScreenStage.icons.add(iconImage)
    } else {
        System.err.println("Icon resource not found: $iconPath")
    }

    // Make the help screen a modal dialog so it stays there until you close it and prevent from interaction on mainStage
    editScreenStage.initModality(Modality.APPLICATION_MODAL)

    editScreenController.setEditScreenStage(editScreenStage)

    // Get Star information to pass it
    val selectedItemInt = extractStarNumber(selectedItem)

    // Find the index of the star in the starsListRaw
    val index = starsListRaw.indexOfFirst {
        val values = it.split(",")
        values[0] == selectedLayer.toString() && values[1] == selectedItemInt.toString()
    }

    val currentStar = starsListRaw[index]
    val valuesToWrite = currentStar.split(",").toMutableList()

    editScreenController.setStarsilListview(starsil_listview)
    editScreenController.setStarInformation(valuesToWrite[0], valuesToWrite[1], valuesToWrite[2], valuesToWrite[3], valuesToWrite[4], valuesToWrite[5], valuesToWrite[6], valuesToWrite[7], starsil_listview)

    editScreenStage.showAndWait()
}