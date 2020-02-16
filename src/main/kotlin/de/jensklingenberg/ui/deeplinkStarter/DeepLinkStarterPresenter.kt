package de.jensklingenberg.ui.deeplinkStarter

import de.jensklingenberg.model.Argument
import de.jensklingenberg.model.ArgumentSourceItem
import java.awt.Desktop
import java.net.URI


class DeepLinkStarterPresenter(private val view: DeepLinkStarterContract.View) : DeepLinkStarterContract.Presenter,
    ArgumentListItemViewHolder.Listener {

    private var appUriValue: String = ""
    private var argsListItems: List<ArgumentSourceItem> = mutableListOf()
    private var mode: DeepLinkStarterContract.Mode = DeepLinkStarterContract.Mode.CUSTOM

    override fun onCreate() {}

    override fun onDestroy() {}

    override fun loadData(appUriValue: String?, mode: DeepLinkStarterContract.Mode) {

        //Find the variables with {}
        val argumentNames =
            "\\{[^{}]*\\}".toRegex().findAll(appUriValue ?: "").toList().map { it.groups.first()?.value ?: "" }

        this.appUriValue = appUriValue ?: ""
        argsListItems = listOf(
            ArgumentSourceItem(
                Argument("DeeplinkValue", this.appUriValue), this
            )
        ) + argumentNames.map {
            ArgumentSourceItem(
                Argument(it, ""), this
            )
        } + listOf(
            ArgumentSourceItem(
                Argument("Launch Flags", ""), this
            )
        )
        view.setListData(argsListItems)
    }


    override fun onOpenDeeplink() {
        var new = argsListItems.first().argument.value
        argsListItems.map { it.argument }.forEach {
            new = new.replace(it.name, it.value)
        }
        new = new + " " + argsListItems.last().argument.value
        val deeplinkCmd = "adb shell am start -d $new"
        view.showNotification(deeplinkCmd)

        Runtime.getRuntime().exec(deeplinkCmd)
    }

    override fun onReload() {
        loadData(appUriValue, this.mode)
    }

    override fun onHelpButtonClicked() {
        Desktop.getDesktop().browse(URI("https://github.com/Foso/Android_Deeplink_Starter"))

    }

    override fun onTextChanged(sourceItem: ArgumentSourceItem, text: String) {
        if(sourceItem.argument.name == "DeeplinkValue"){
            appUriValue=text
        }
        val newArg = sourceItem.argument

        argsListItems = argsListItems.map {
            if (it.argument.name == newArg.name) {
                ArgumentSourceItem(it.argument.copy(value = text))
            } else {
                it
            }
        }
    }




}