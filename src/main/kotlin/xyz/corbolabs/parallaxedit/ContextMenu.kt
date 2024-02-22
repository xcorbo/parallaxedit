package xyz.corbolabs.parallaxedit

import javafx.scene.control.ChoiceBox
import javafx.scene.control.ContextMenu
import javafx.scene.control.ListView
import javafx.scene.control.MenuItem

fun ContextMenuRender(
    starsil_listview: ListView<String>,
    layers_choicebox: ChoiceBox<String>,
    starsListRaw: MutableList<String>
){

    //Get variables we need...
    val selectedLayer = layers_choicebox.selectionModel.selectedIndex


    // Create context menu items
    val addItem = MenuItem("Add New Star")
    val editItem = MenuItem("Edit This Star")
    val duplicateItem = MenuItem("Duplicate This Star")
    val deleteItem = MenuItem("Delete This Star")

    // Create context menu
    val contextMenu = ContextMenu(addItem, editItem, duplicateItem, deleteItem)

    // Set context menu for list view
    starsil_listview.contextMenu = contextMenu

    // Handle right-click event to show context menu
    if(starsil_listview.items.isNotEmpty()){
        starsil_listview.setOnMouseClicked { event ->
            if (event.isSecondaryButtonDown) {
                contextMenu.show(starsil_listview, event.screenX, event.screenY)
            }
        }
    }


    // Context Menu Actions
    addItem.setOnAction {

        AddStarFromContext(selectedLayer, starsListRaw, starsil_listview)
    }

    editItem.setOnAction {

        val selectedItem = starsil_listview.selectionModel.selectedItem
        DeployEditBox(selectedLayer, selectedItem, starsListRaw, starsil_listview)
    }

    duplicateItem.setOnAction {

        val selectedItem = starsil_listview.selectionModel.selectedItem
        DuplicateStarFromContext(selectedLayer, selectedItem, starsListRaw, starsil_listview)
    }

    deleteItem.setOnAction {

        val selectedItem = starsil_listview.selectionModel.selectedItem
        DeleteStarFromContext(selectedLayer, selectedItem, starsListRaw, starsil_listview)
    }

}