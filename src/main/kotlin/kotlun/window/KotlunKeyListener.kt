package kotlun.window

import java.awt.event.KeyAdapter
import java.awt.event.KeyEvent

class KotlunKeyListener : KeyAdapter() {
    var press : ((KeyEvent) -> Unit)? = null
    var release: ((KeyEvent) -> Unit)? = null
    override fun keyPressed(e: KeyEvent) {
        if (press != null) press!!(e)
    }
    override fun keyReleased(e: KeyEvent) {
        if (release != null) release!!(e)
    }
}