package de.jensklingenberg.model

import javax.swing.JComponent

interface ViewHolder {
    fun getComponent(): JComponent
}