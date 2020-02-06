package de.jensklingenberg.ui.deeplinkStarter

import de.jensklingenberg.model.Argument
import de.jensklingenberg.model.ArgumentSourceItem


class DeepLinkStarterPresenter(private val view: DeepLinkStarterContract.View) : DeepLinkStarterContract.Presenter,
    ArgumentListItemViewHolder.Listener {

    private var appUriValue: String = ""
    private var argsListItems: List<ArgumentSourceItem> = mutableListOf()
    private var mode: DeepLinkStarterContract.Mode = DeepLinkStarterContract.Mode.CUSTOM

    override fun onCreate() {}

    override fun onDestroy() {}
//    <depends>org.jetbrains.android</depends>
    override fun loadData(appUriValue: String?, mode: DeepLinkStarterContract.Mode) {

        //Find the variables with {}
        val argumentNames =
            "\\{[^{}]*\\}".toRegex().findAll(appUriValue ?: "").toList().map { it.groups.first()?.value ?: "" }

        this.appUriValue = appUriValue ?: ""
        argsListItems = listOf(
            ArgumentSourceItem(
                Argument("DeeplinkValue", this.appUriValue), this, true
            )
        ) + argumentNames.map {
            ArgumentSourceItem(
                Argument(it, ""), this
            )
        } + listOf(
            ArgumentSourceItem(
                Argument("Launch Flags", ""), this, false
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

    override fun onTextChanged(sourceItem: ArgumentSourceItem, text: String) {
        val newArg = sourceItem.argument

        argsListItems = argsListItems.map {
            if (it.argument.name == newArg.name) {
                ArgumentSourceItem(it.argument.copy(value = text))
            } else {
                it
            }
        }
    }

    override fun onRefresh(appUri: String) {
        loadData(appUri, this.mode)
    }


}