package de.jensklingenberg.ui.deeplinkStarter


import de.jensklingenberg.ui.ext.onClick
import de.jensklingenberg.model.ArgumentSourceItem
import de.jensklingenberg.model.ViewHolder
import javax.swing.*
import javax.swing.event.DocumentEvent
import javax.swing.event.DocumentListener

class ArgumentListItemViewHolder(sourceItem: ArgumentSourceItem) :
    ViewHolder {

    lateinit var panel1: JPanel
    lateinit var nameLbl: JLabel
    lateinit var valueTF: JTextField

    init {
        sourceItem.argument.let { argument ->
            nameLbl.text = argument.name

            valueTF.text = argument.value

            valueTF.document.addDocumentListener(object : DocumentListener {
                override fun changedUpdate(p0: DocumentEvent?) {}

                override fun insertUpdate(p0: DocumentEvent?) {
                    p0?.let {
                        val text = it.document.getText(0, it.document.length)
                        sourceItem.listener?.onTextChanged(sourceItem, text)
                    }
                }

                override fun removeUpdate(p0: DocumentEvent?) {
                    p0?.let {
                        val text = it.document.getText(0, it.document.length)
                        sourceItem.listener?.onTextChanged(sourceItem, text)
                    }
                }

            })

        }
    }

    interface Listener {
        fun onTextChanged(sourceItem: ArgumentSourceItem, text: String)
    }

    override fun getComponent(): JComponent = panel1
}


