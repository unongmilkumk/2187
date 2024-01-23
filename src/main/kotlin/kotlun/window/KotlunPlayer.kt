package kotlun.window

import java.awt.event.KeyEvent

object KotlunPlayer {
    var x = 0
    var y = 0
    var speed = 10
    fun moveLeft() {
        this.x -= speed
    }
    fun moveRight() {
        this.x += speed
    }
    fun moveUp() {
        this.y -= speed
    }
    fun moveDown() {
        this.y += speed
    }
    fun keyListener(): KotlunKeyListener {
        val ret = KotlunKeyListener()
        ret.press = {
            when (it.keyCode) {
                KeyEvent.VK_LEFT -> moveLeft()
                KeyEvent.VK_RIGHT -> moveRight()
                KeyEvent.VK_UP -> moveUp()
                KeyEvent.VK_DOWN -> moveDown()
            }
        }
        return ret
    }
}