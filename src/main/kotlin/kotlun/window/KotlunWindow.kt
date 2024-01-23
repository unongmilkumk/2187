package kotlun.window

import javax.swing.JFrame

class KotlunWindow(title: String, width: Int, height: Int) {
    var frame = JFrame()
    init {
        frame.title = title
        frame.setSize(width, height)
        frame.layout = null
        frame.setLocationRelativeTo(null)
        frame.defaultCloseOperation = JFrame.EXIT_ON_CLOSE
    }
    fun run() {
        frame.isVisible = true
    }
    fun setCanvas(canvas : KotlunCanvas) {
        frame.contentPane = canvas
    }
}