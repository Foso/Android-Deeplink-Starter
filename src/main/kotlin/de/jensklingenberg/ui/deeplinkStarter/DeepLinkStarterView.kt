package de.jensklingenberg.ui.deeplinkStarter

import com.intellij.notification.Notification
import com.intellij.notification.NotificationType
import com.intellij.notification.Notifications
import com.intellij.openapi.ui.DialogWrapper
import com.intellij.ui.components.JBScrollPane
import de.jensklingenberg.ui.ext.onClick
import de.jensklingenberg.ui.ext.setItems
import de.jensklingenberg.model.SourceItem
import javax.swing.JButton
import javax.swing.JComponent
import javax.swing.JPanel


class DeepLinkStarterView(appUriValue: String, private val mode: DeepLinkStarterContract.Mode) : DialogWrapper(true),
    DeepLinkStarterContract.View {

    lateinit var myPanel: JPanel
    lateinit var sampleList: JBScrollPane
    lateinit var openDeeplinkButton: JButton

    private val presenter: DeepLinkStarterContract.Presenter = DeepLinkStarterPresenter(this)

    init {
        init()
        if (appUriValue.isEmpty()) {
            presenter.loadData(appUriValue, DeepLinkStarterContract.Mode.CUSTOM)
        } else {
            presenter.loadData(appUriValue, mode)
        }
        openDeeplinkButton.onClick {
            presenter.onOpenDeeplink()
        }
    }

    override fun createCenterPanel(): JComponent? = myPanel

    override fun setListData(data: List<SourceItem>) {
        sampleList.setItems(data)
        sampleList.updateUI()
        this.pack()
    }

    override fun showNotification(text: String) {
        Notifications.Bus.notify(
            Notification(
                "Android Deeplink Starter",
                "Hallo",
                text,
                NotificationType.INFORMATION
            )
        )
    }

    override fun dispose() {
        presenter.onDestroy()
        super.dispose()
    }

}



