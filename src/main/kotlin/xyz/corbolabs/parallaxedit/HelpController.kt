package xyz.corbolabs.parallaxedit

import javafx.fxml.FXML
import javafx.scene.control.Button
import javafx.stage.Stage

class HelpController (){
    @FXML
    private lateinit var agree_button: Button

    private lateinit var helpScreenStage: Stage

    fun setHelpScreenStage(stage: Stage) {
        helpScreenStage = stage
    }

    @FXML
    fun onAgreeButtonClick(){
        helpScreenStage.close()
    }
}