    package xyz.corbolabs.parallaxedit

    import javafx.application.Platform
    import javafx.fxml.FXML
    import javafx.scene.control.Button
    import javafx.scene.control.ChoiceBox
    import javafx.scene.control.Label
    import javafx.scene.control.ListView
    import javafx.stage.FileChooser
    import java.io.DataInputStream
    import java.io.FileInputStream
    import java.lang.Exception
    import java.nio.ByteBuffer
    import java.nio.ByteOrder
    import kotlin.system.exitProcess

    class MainController {
        val layers = Layers()
        var imgNumLay = mutableListOf<Int>()
        var starsList = mutableListOf<String>()


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
        private fun onOpenButtonClick() {
            val fileChooser = FileChooser()
            val selectedFile = fileChooser.showOpenDialog(null)
            var stars: Stars

            // LOLAOWJASJDFSDKFHSDFHSLDKFH

            if (selectedFile != null){
                try{
                    val inputStream = DataInputStream(FileInputStream(selectedFile))

                    // First int is number of layers in the spk
                    inputStream.readFully(Constants.bufferInt)
                    val layNum = ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt()

                    // Second int is offset to the first star object
                    inputStream.readFully(Constants.bufferInt)
                    val starOffset = ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt()

                    System.out.println(layNum)
                    System.out.println(starOffset)

                    for (i in 0 until layNum ){
                        System.out.println("Layer: " + i)

                        // First int of header is number of stars in this layer
                        inputStream.readFully(Constants.bufferInt)
                        var layers = Layers(numLay =  ByteBuffer.wrap(Constants.bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt())
                        imgNumLay.add(i, layers.numLay)
                        layers_choicebox.items.add(i, "Layer $i - " + imgNumLay[i])
                        System.out.println("Images in layer: " + layers.numLay)
                        System.out.println("LOL: " + imgNumLay[i])

                        // First short is the layer width
                        inputStream.readFully(Constants.bufferShort)
                        layers = Layers(layWidth =  ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort())
                        System.out.println("Layer width: " + layers.layWidth)

                        // Second short is the layer height
                        inputStream.readFully(Constants.bufferShort)
                        layers = Layers(layHeight =  ByteBuffer.wrap(Constants.bufferShort).order(ByteOrder.LITTLE_ENDIAN).getShort())
                        System.out.println("Layer height: " + layers.layHeight)
                    }

                    if (layers_choicebox.items.isNotEmpty()){
                        layers_choicebox.selectionModel.select(0)
                    }

                    System.out.println("STAR INFORMATION HERE")

                    for (i in 0..layNum){
                        for (j in 0..imgNumLay[i]){

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

                            stars_listview.items.add("Layer: " + i +
                                    ", Star #: " + j +
                                    "(x: " + stars1 +
                                    ", y: " + stars2 +
                                    ", dds x: " + stars3 +
                                    ", dds y: " + stars4 +
                                    ", width: " + stars5 +
                                    ", height: " + stars6 + ")"
                            )

                            System.out.println( "Layer: " + i +
                                                ", Star #: " + j +
                                                "(x: " + stars1 +
                                                ", y: " + stars2 +
                                                ", dds x: " + stars3 +
                                                ", dds y: " + stars4 +
                                                ", width: " + stars5 +
                                                ", height: " + stars6 + ")"
                            )
                            starsList.add("Layer: " + i +
                                    ", Star #: " + j +
                                    "(x: " + stars1 +
                                    ", y: " + stars2 +
                                    ", dds x: " + stars3 +
                                    ", dds y: " + stars4 +
                                    ", width: " + stars5 +
                                    ", height: " + stars6 + ")")
                        }
                    }

                    debug_label.text = layNum.toString()
                    inputStream.close()
                } catch (e: Exception){
                    debug_label.text = "Something Failed..."
                }
            }

            layers_choicebox.selectionModel.selectedIndexProperty().addListener() { _, _, newValue ->
                val selectedItem = newValue.toInt()
                System.out.println(selectedItem)
                starsil_listview.items.clear()
                for(items in starsList){
                    if(items.contains("Layer: $selectedItem")){
                        starsil_listview.items.add(items)
                    }
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