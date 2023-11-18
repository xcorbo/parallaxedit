    package xyz.corbolabs.parallaxedit

    import javafx.fxml.FXML
    import javafx.scene.control.Label
    import javafx.stage.FileChooser
    import java.io.DataInputStream
    import java.io.FileInputStream
    import java.lang.Exception
    import java.nio.ByteBuffer
    import java.nio.ByteOrder

    class MainController {

        @FXML
        private lateinit var debug_label: Label

        @FXML
        private fun onOpenButtonClick() {
            val fileChooser = FileChooser()
            val selectedFile = fileChooser.showOpenDialog(null)

            // LOLAOWJASJDFSDKFHSDFHSLDKFH

            if (selectedFile != null){
                try{
                    val inputStream = DataInputStream(FileInputStream(selectedFile))

                    val bufferInt = ByteArray(4)
                    inputStream.readFully(bufferInt)
                    val layNum = ByteBuffer.wrap(bufferInt).order(ByteOrder.LITTLE_ENDIAN).getInt()

                    val bufferInt2 = ByteArray(4)
                    inputStream.readFully(bufferInt2)
                    val layNum2 = ByteBuffer.wrap(bufferInt2).order(ByteOrder.LITTLE_ENDIAN).getInt()

                    System.out.println(layNum)
                    System.out.println(layNum2)

                    for (i in 0 until layNum ){
                        System.out.println(i)

                        val bufferInt3 = ByteArray(4)
                        inputStream.readFully(bufferInt3)
                        val structInt1 = ByteBuffer.wrap(bufferInt3).order(ByteOrder.LITTLE_ENDIAN).getInt()
                        System.out.println(structInt1)

                        val bufferShort1 = ByteArray(2)
                        inputStream.readFully(bufferShort1)
                        val structInt2 = ByteBuffer.wrap(bufferShort1).order(ByteOrder.LITTLE_ENDIAN).getShort()
                        System.out.println(structInt2)

                        val bufferShort2 = ByteArray(2)
                        inputStream.readFully(bufferShort2)
                        val structInt3 = ByteBuffer.wrap(bufferShort2).order(ByteOrder.LITTLE_ENDIAN).getShort()
                        System.out.println(structInt3)

                    }

                    debug_label.text = layNum.toString()
                    inputStream.close()
                } catch (e: Exception){
                    debug_label.text = "Something Failed..."
                }
            }
        }
    }