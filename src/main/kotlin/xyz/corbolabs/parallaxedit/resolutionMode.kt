package xyz.corbolabs.parallaxedit

import javafx.scene.control.Button

class ResolutionMode {

    private lateinit var save_button: Button
    private lateinit var open_button_dds: Button
    private lateinit var sd_button: Button
    private lateinit var hd_button: Button
    private lateinit var hd2_button: Button

    constructor(
        save_button: Button,
        open_button_dds: Button,
        sd_button: Button,
        hd_button: Button,
        hd2_button: Button
    ) {
        this.save_button = save_button
        this.open_button_dds = open_button_dds
        this.sd_button = sd_button
        this.hd_button = hd_button
        this.hd2_button = hd2_button
    }

    public fun parse(height: Int) {
        // Enable rest of the UI and determine SD, HD or HD2
        save_button.isDisable = false
        open_button_dds.isDisable = false

        // Case SD
        if (height == 488) {
            sd_button.isDisable = false
        }
        // Case HD
        if (height == 976) {
            hd_button.isDisable = false
        }
        // Case HD2
        if (height == 1952) {
            hd2_button.isDisable = false
        }
    }

}