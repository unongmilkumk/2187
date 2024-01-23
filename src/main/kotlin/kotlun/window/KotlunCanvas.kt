package kotlun.window

import java.awt.Color
import java.awt.Graphics
import javax.swing.JPanel

class KotlunCanvas(color : Color) : JPanel() {
    private lateinit var paintcode : (g: Graphics) -> Unit
    init {
        background = color
        isFocusable = true
    }
    fun paint(code : (g: Graphics) -> Unit) {
        paintcode = code
    }
    override fun paint(g: Graphics) {
        super.paint(g)
        paintcode(g)
    }
    fun addKKeyListener(listener: KotlunKeyListener) {
        addKeyListener(listener)
    }
    fun repaintit() {
        repaint()
    }
}