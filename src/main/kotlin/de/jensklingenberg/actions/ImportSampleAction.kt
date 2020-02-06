package de.jensklingenberg.actions

import com.intellij.openapi.actionSystem.AnAction
import com.intellij.openapi.actionSystem.AnActionEvent
import de.jensklingenberg.ui.deeplinkStarter.DeepLinkStarterContract
import de.jensklingenberg.ui.deeplinkStarter.DeepLinkStarterView

class ToolsMenuAction : AnAction() {

    override fun actionPerformed(anActionEvent: AnActionEvent) {
        DeepLinkStarterView("", DeepLinkStarterContract.Mode.CUSTOM).showAndGet()
    }

}