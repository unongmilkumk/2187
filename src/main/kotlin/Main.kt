import kotlun.window.*
import java.awt.*
import java.awt.event.KeyEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.ImageIcon
import kotlin.math.sqrt


class Main {
    val icon = this.javaClass.classLoader.getResource("Icon.png")!!
    companion object {
        @JvmStatic fun main(args: Array<String>) {
            var number =
                arrayListOf(
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0,
                    0, 0, 0, 0
                )
            val window = KotlunWindow("2187", 1920, 1080)
            val canvas = KotlunCanvas(Color.PINK)

            window.frame.iconImage = ImageIcon(Main().icon).image

            val boardSize = sqrt(number.size.toDouble()).toInt()
            fun getIndex(row: Int, col: Int): Int {
                return row * boardSize + col
            }
            fun hasEmptyCell(): Boolean {
                for (i in 0 until boardSize) {
                    for (j in 0 until boardSize) {
                        if (number[getIndex(i, j)] == 0) {
                            return true
                        }
                    }
                }

                return false
            }
            fun isGameOver(): Boolean {
                if (hasEmptyCell()) {
                    return false
                }

                for (i in 0 until boardSize) {
                    for (j in 1 until boardSize) {
                        if (number[getIndex(i, j)] == number[getIndex(i, j - 1)]) {
                            return false
                        }
                    }
                }

                for (j in 0 until boardSize) {
                    for (i in 1 until boardSize) {
                        if (number[getIndex(i, j)] == number[getIndex(i - 1, j)]) {
                            return false
                        }
                    }
                }

                return true
            }
            fun addRandomTile() {
                val emptyTiles = mutableListOf<Int>()
                for (index in number.indices) {
                    if (number[index] == 0) {
                        emptyTiles.add(index)
                    }
                }
                if (emptyTiles.isEmpty()) {
                    return
                }
                val randomIndex = (Math.random() * emptyTiles.size).toInt()
                val tile = emptyTiles[randomIndex]
                val value = if (Math.random() < 0.9) 3 else 9
                number[tile] = value
            }
            addRandomTile()

            canvas.addKKeyListener(KotlunKeyListener().apply {
                if (isGameOver()) return
                this.press = {
                    when (it.keyCode) {
                        KeyEvent.VK_LEFT -> {
                            for (i in 0 until boardSize) {
                                val row = IntArray(boardSize)
                                var currentIndex = 0

                                for (j in 0 until boardSize) {
                                    val index = getIndex(i, j)
                                    if (number[index] != 0) {
                                        row[currentIndex++] = number[index]
                                        number[index] = 0
                                    }
                                }

                                for (j in 0 until currentIndex - 1) {
                                    if (row[j] == row[j + 1]) {
                                        row[j] *= 3
                                        row[j + 1] = 0
                                    }
                                }

                                currentIndex = 0
                                for (j in row.indices) {
                                    if (row[j] != 0) {
                                        val index = getIndex(i, currentIndex++)
                                        number[index] = row[j]
                                    }
                                }
                            }
                            addRandomTile()
                        }
                        KeyEvent.VK_RIGHT -> {
                            for (i in 0 until boardSize) {
                                val row = IntArray(boardSize)
                                var currentIndex = boardSize - 1

                                for (j in boardSize - 1 downTo 0) {
                                    val index = getIndex(i, j)
                                    if (number[index] != 0) {
                                        row[currentIndex--] = number[index]
                                        number[index] = 0
                                    }
                                }

                                for (j in boardSize - 1 downTo 1) {
                                    if (row[j] == row[j - 1]) {
                                        row[j] *= 3
                                        row[j - 1] = 0
                                    }
                                }

                                currentIndex = boardSize - 1
                                for (j in row.indices.reversed()) {
                                    if (row[j] != 0) {
                                        val index = getIndex(i, currentIndex--)
                                        number[index] = row[j]
                                    }
                                }
                            }
                            addRandomTile()
                        }
                        KeyEvent.VK_UP -> {
                            for (j in 0 until boardSize) {
                                val column = IntArray(boardSize)
                                var currentIndex = 0

                                for (i in 0 until boardSize) {
                                    val index = getIndex(i, j)
                                    if (number[index] != 0) {
                                        column[currentIndex++] = number[index]
                                        number[index] = 0
                                    }
                                }

                                for (i in 0 until currentIndex - 1) {
                                    if (column[i] == column[i + 1]) {
                                        column[i] *= 3
                                        column[i + 1] = 0
                                    }
                                }

                                currentIndex = 0
                                for (i in column.indices) {
                                    if (column[i] != 0) {
                                        val index = getIndex(currentIndex++, j)
                                        number[index] = column[i]
                                    }
                                }
                            }
                            addRandomTile()
                        }
                        KeyEvent.VK_DOWN -> {
                            for (j in 0 until boardSize) {
                                val column = IntArray(boardSize)
                                var currentIndex = boardSize - 1

                                for (i in boardSize - 1 downTo 0) {
                                    val index = getIndex(i, j)
                                    if (number[index] != 0) {
                                        column[currentIndex--] = number[index]
                                        number[index] = 0
                                    }
                                }

                                for (i in boardSize - 1 downTo 1) {
                                    if (column[i] == column[i - 1]) {
                                        column[i] *= 3
                                        column[i - 1] = 0
                                    }
                                }

                                currentIndex = boardSize - 1
                                for (i in column.indices.reversed()) {
                                    if (column[i] != 0) {
                                        val index = getIndex(currentIndex--, j)
                                        number[index] = column[i]
                                    }
                                }
                            }
                            addRandomTile()
                        }
                    }
                }
            })
            fun drawCenteredString(g: Graphics, text: String, rect: Rectangle, font: Font) {
                var metrics = g.getFontMetrics(font)
                var fontSize = font.size
                while (metrics.stringWidth(text) > rect.width || metrics.height > rect.height) {
                    fontSize--
                    g.font = font.deriveFont(fontSize.toFloat())
                    metrics = g.getFontMetrics(g.font)
                }

                val x = rect.x + (rect.width - metrics.stringWidth(text)) / 2
                val y = rect.y + ((rect.height - metrics.height) / 2) + metrics.ascent

                g.font = font.deriveFont(fontSize.toFloat())
                g.drawString(text, x, y)
            }
            canvas.addMouseListener(object : MouseAdapter() {
                override fun mousePressed(e: MouseEvent) {
                    if (e.x in 100..475 && e.y in 550..650) {
                        number = arrayListOf(
                            0, 0, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0,
                            0, 0, 0, 0
                        )
                        addRandomTile()
                    }
                }
            })
            canvas.paint {
                it.color = Color.GRAY
                it.fillRect(90, 90, 410, 410)
                for (i in 0 until boardSize) {
                    for (j in 0 until boardSize) {
                        val index = getIndex(i, j)
                        if (number[index] != 0) {
                            val coloarasdf = Color.RGBtoHSB(115, 182, 197, null)
                            it.color = Color.getHSBColor(coloarasdf[0], coloarasdf[1], coloarasdf[2])
                            it.fillRect(j * 100 + 100, i * 100 + 100, 90, 90)
                            it.color = Color.WHITE
                            drawCenteredString(it, number[index].toString(), Rectangle(j * 100 + 100, i * 100 + 100, 90, 90), it.font.deriveFont(30.0f))
                        } else {
                            it.color = Color.LIGHT_GRAY
                            it.fillRect(j * 100 + 100, i * 100 + 100, 90, 90)
                        }
                    }
                }
                it.color = Color.DARK_GRAY
                it.fillRect(100, 550, 375, 100)
                it.color = Color.WHITE
                drawCenteredString(it, "RESET", Rectangle(100, 550, 375, 100), it.font.deriveFont(30.0f))
                if (isGameOver()) {
                    it.drawString("FAILED", 100, 50)
                }
            }
            KotlunRepaint.repaint(10, canvas)
            window.setCanvas(canvas)
            window.run()
        }
    }
}