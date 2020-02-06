package de.jensklingenberg.ui.ext

import de.jensklingenberg.model.SourceItem
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import java.awt.event.MouseListener
import javax.swing.*

fun JButton.onClick(fn: () -> Unit) {
    this.addMouseListener(object : MouseListener {
        override fun mouseReleased(p0: MouseEvent?) {

        }

        override fun mouseEntered(p0: MouseEvent?) {

        }

        override fun mouseClicked(p0: MouseEvent?) {
            fn()
        }

        override fun mouseExited(p0: MouseEvent?) {

        }

        override fun mousePressed(p0: MouseEvent?) {

        }

    })

}

interface JListOnClickListener {
    fun onItemClicked(item: Any)
}

fun JList<*>.onClickListener(mouseListener: JListOnClickListener) {
    val mouseAdapter = object : MouseAdapter() {
        override fun mouseClicked(p0: MouseEvent?) {
            mouseListener.onItemClicked(selectedValue)
        }
    }
    this.addMouseListener(mouseAdapter)

}


fun JScrollPane.setItems(viewHolder: List<SourceItem>) {
    val content = JPanel()
    content.layout = BoxLayout(content, BoxLayout.Y_AXIS)
    viewHolder.forEach {
        content.add(it.getViewHolder().getComponent())
    }
    setViewportView((content))
}
