    package xyz.corbolabs.parallaxedit

    import javafx.application.Platform
    import javafx.fxml.FXML
    import javafx.scene.control.Button
    import javafx.scene.control.ChoiceBox
    import javafx.scene.control.Label
    import javafx.scene.control.ListView
    import javafx.scene.image.Image
    import javafx.scene.image.ImageView
    import javafx.scene.layout.AnchorPane
    import javafx.stage.FileChooser
    import java.io.DataInputStream
    import java.io.File
    import java.io.FileInputStream
    import java.lang.Exception
    import java.nio.ByteBuffer
    import java.nio.ByteOrder
    import java.util.prefs.Preferences

    class MainController {
        val layers = Layers()
        var imgNumLay = mutableListOf<Int>()
        var starsList = mutableListOf<String>()
        var starsListRaw = mutableListOf<String>()

        @FXML
        private lateinit var debug_label: Label
        @FXML
        private lateinit var layers_choicebox: ChoiceBox<String>
        @FXML
        private lateinit var stars_listview: ListView<String>
        @FXML
        private lateinit var starsil_listview: ListView<String>
        @FXML
        private lateinit var close_button: Button
        @FXML
        private lateinit var save_button: Button
        @FXML
        private lateinit var open_button_dds: Button
        @FXML
        private lateinit var sd_button: Button
        @FXML
        private lateinit var hd_button: Button
        @FXML
        private lateinit var hd2_button: Button
        @FXML
        private lateinit var background_preview: AnchorPane

        private lateinit var resolutionMode: ResolutionMode
        private val preferences = Preferences.userNodeForPackage(MainController::class.java)

        @FXML
        private fun initialize() {
            resolutionMode = ResolutionMode(save_button, open_button_dds, sd_button, hd_button, hd2_button)
        }

        @FXML
        private fun onOpenButtonClick() {

            // Setting up FileChooser, Init, filter, icon, preferences, title, whatnot
            val fileChooser = FileChooser()

            // Filter
            val spkFilter = FileChooser.ExtensionFilter(StringsEN.spkFilterName, StringsEN.spkFilterSymbol)
            fileChooser.extensionFilters.add(spkFilter)

            // Last directory
            val storedDirectory = preferences.get("lastDirectory", null)
            val initialDirectory = storedDirectory?.let { File(it) }
            if (initialDirectory != null && initialDirectory.isDirectory) {
                fileChooser.initialDirectory = initialDirectory
            }

            // Title
            fileChooser.title = StringsEN.stageTitleCF

            val selectedFile = fileChooser.showOpenDialog(null)

            var stars: Stars

            // Actual opening starts here
            if (selectedFile != null){
                try{
                    // init inputStream, shove the file into a stream for parsing + last dir preferences
                    val inputStream = DataInputStream(FileInputStream(selectedFile))
                    preferences.put("lastDirectory", selectedFile.parent)

                    // Resetting the scene title to the opened file + dir
                    MainApplication.primaryStage.title = StringsEN.stageTitleWF + "(${selectedFile.absoluteFile})"

                    // Now we start parsing the file/inputStream
                    // First int is number of layers in the spk
                    inputStream.readFully(Constants.bufferInt)
                    val layNum = ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt()

                    // Second int is offset to the first star object
                    inputStream.readFully(Constants.bufferInt)
                    val starOffset = ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt()

                    // After two ints are read, we read the header that give us the number of stars, number of images in layer and layer size
                    // As a note SD = 648 x 488, HD = 1296 x 976, HD2 = 2592 x 1952
                    for (i in 0 until layNum ){

                        // First int of header is number of stars in this layer
                        inputStream.readFully(Constants.bufferInt)
                        var layers = Layers(numLay =  ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt())
                        imgNumLay.add(i, layers.numLay)
                        layers_choicebox.items.add(i, StringsEN.layerSymbolCombobox + i + " - " + imgNumLay[i])

                        // First short is the layer width
                        inputStream.readFully(Constants.bufferShort)
                        layers = Layers(layWidth =  ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort())

                        // Second short is the layer height
                        inputStream.readFully(Constants.bufferShort)
                        layers = Layers(layHeight =  ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort())

                        resolutionMode.parse(layers.layHeight.toInt())

                    }

                    if (layers_choicebox.items.isNotEmpty()){
                        layers_choicebox.selectionModel.select(0)
                    }

                    for (i in 0..layNum){
                        for (j in 0..imgNumLay[i]){

                            var indexStar = 0;

                            // Layer X Position
                            inputStream.readFully(Constants.bufferShort)
                            var stars1 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                            // Layer Y Position
                            inputStream.readFully(Constants.bufferShort)
                            var stars2 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                            // DDS X Position
                            inputStream.readFully(Constants.bufferShort)
                            var stars3 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                            // DDS Y Position
                            inputStream.readFully(Constants.bufferShort)
                            var stars4 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                            // Star Width
                            inputStream.readFully(Constants.bufferShort)
                            var stars5 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()

                            // Star Height
                            inputStream.readFully(Constants.bufferShort)
                            var stars6 = ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort()
                            starsListRaw.add("$i,$j,$stars1,$stars2,$stars3,$stars4,$stars5,$stars6;")

                            stars_listview.items.add(
                                    "Layer: "       + i +
                                    ", Star #: "    + j +
                                    "(x: "          + stars1 +
                                    ", y: "         + stars2 +
                                    ", dds x: "     + stars3 +
                                    ", dds y: "     + stars4 +
                                    ", width: "     + stars5 +
                                    ", height: "    + stars6 +
                                    ")"
                            )

                            starsList.add(
                                    "Layer: "       + i +
                                    ", Star #: "    + j +
                                    "(x: "          + stars1 +
                                    ", y: "         + stars2 +
                                    ", dds x: "     + stars3 +
                                    ", dds y: "     + stars4 +
                                    ", width: "     + stars5 +
                                    ", height: "    + stars6 +
                                    ")"
                            )
                        }
                    }

                    debug_label.text = layNum.toString()
                    inputStream.close()
                } catch (e: Exception){
                    debug_label.text = StringsEN.genErr
                }
            }

            layers_choicebox.selectionModel.selectedIndexProperty().addListener() { _, _, newValue ->
                val selectedItem = newValue.toInt()
                System.out.println(starsListRaw.toString())
                starsil_listview.items.clear()
                for(items in starsList){
                    if(items.contains("Layer: $selectedItem")){
                        starsil_listview.items.add(items)
                    }
                }

            }

        }

        @FXML
        private fun onOpenButtonDDSClick() {

            // Setting up FileChooser, Init, filter, icon, preferences, title, whatnot
            val pngChooser = FileChooser()

            // Filter
            val pngFilter = FileChooser.ExtensionFilter(StringsEN.pngFilterName, StringsEN.pngFilterSymbol)
            pngChooser.extensionFilters.add(pngFilter)

            // TODO Preferences should try to remember last opened dir here

            // Title
            pngChooser.title = StringsEN.stageTitleCFPNG

            val selectedFile = pngChooser.showOpenDialog(null)

            // Actual opening starts here
            if (selectedFile != null) {
                try {

                    // init inputStream, shove the file into a stream for parsing
                    val image = Image(selectedFile.toURI().toString())
                    val imageView = ImageView(image)
                    imageView.fitWidth = background_preview.width
                    imageView.fitHeight = background_preview.height
                    background_preview.children.add(imageView)

                } catch (e: Exception){

                    System.out.println(starsListRaw)
                    debug_label.text = StringsEN.genErr

                }
            }
        }

        @FXML
        private fun onCloseButtonClick(){
            Platform.exit()
        }

        @FXML
        private  fun onSaveButtonClick(){
            for(items in starsList){
                if(items.contains("Layer: 4")){
                    starsil_listview.items.add(items)
                }
            }
        }


    }