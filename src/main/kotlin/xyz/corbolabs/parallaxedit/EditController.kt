package xyz.corbolabs.parallaxedit

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.control.ListView
import javafx.scene.control.TextField
import javafx.stage.Stage

class EditController() {
    // UI elements init
    @FXML
    private lateinit var edit_layer_label: Label

    @FXML
    private lateinit var edit_star_label: Label

    @FXML
    private lateinit var edit_x_textField: TextField

    @FXML
    private lateinit var edit_y_textField: TextField

    @FXML
    private lateinit var edit_ddsX_textField: TextField

    @FXML
    private lateinit var edit_ddsY_textField: TextField

    @FXML
    private lateinit var edit_width_textField: TextField

    @FXML
    private lateinit var edit_height_textField: TextField

    @FXML
    private lateinit var agree_button: Button

    private lateinit var editScreenStage: Stage

    private lateinit var starsil_listview: ListView<String>

    fun setStarsilListview(listview: ListView<String>) {
        starsil_listview = listview
    }

    fun setEditScreenStage(stage: Stage) {
        editScreenStage = stage
    }

    @FXML
    fun onEditButtonClick() {
        val starsil_listview = this.starsil_listview
        EditStarFromContext(
            edit_layer_label.text,
            edit_star_label.text,
            edit_x_textField.text,
            edit_y_textField.text,
            edit_ddsX_textField.text,
            edit_ddsY_textField.text,
            edit_width_textField.text,
            edit_height_textField.text,
            starsil_listview
        )

        editScreenStage.close()
    }

    fun setStarInformation(
        selectedLayer: String,
        selectedItem: String,
        x: String,
        y: String,
        ddsX: String,
        ddsY: String,
        width: String,
        height: String,
        starsil_listview: ListView<String>
    ) {
        edit_layer_label.text = selectedLayer
        edit_star_label.text = selectedItem
        edit_x_textField.text = x
        edit_y_textField.text = y
        edit_ddsX_textField.text = ddsX
        edit_ddsY_textField.text = ddsY
        edit_width_textField.text = width
        edit_height_textField.text = height
    }
}