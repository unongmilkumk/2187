package kotlun.window

import java.awt.event.ActionEvent
import java.awt.event.ActionListener
import javax.swing.Timer

object KotlunRepaint : ActionListener {
    private var canvasa : KotlunCanvas? = null
    private var t : Timer? = null
    override fun actionPerformed(e: ActionEvent) {
        canvasa!!.repaintit()
    }
    fun repaint(tickrate: Int, canvas: KotlunCanvas) {
        t = Timer(tickrate, this)
        t!!.start()
        canvasa = canvas
    }
}