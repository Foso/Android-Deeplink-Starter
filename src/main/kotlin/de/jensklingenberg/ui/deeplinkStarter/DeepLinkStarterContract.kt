package de.jensklingenberg.ui.deeplinkStarter

import de.jensklingenberg.model.SourceItem

interface DeepLinkStarterContract {
    interface View {
        fun setListData(data: List<SourceItem>)
        fun showNotification(text: String)
    }

    interface Presenter {
        fun onCreate()
        fun onDestroy()
        fun loadData(appUriValue: String?, mode: Mode)
        fun onOpenDeeplink()
        fun onReload()
        fun onHelpButtonClicked()
    }

    enum class Mode {
        NAVLIB, CUSTOM, MANIFEST
    }
}

