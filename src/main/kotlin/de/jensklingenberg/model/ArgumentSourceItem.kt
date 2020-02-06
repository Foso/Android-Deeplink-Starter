package de.jensklingenberg.model


import de.jensklingenberg.ui.deeplinkStarter.ArgumentListItemViewHolder

class ArgumentSourceItem(val argument: Argument, val listener: ArgumentListItemViewHolder.Listener? = null, val showRefresh :Boolean = false) :
    SourceItem {
    override fun getViewHolder(): ViewHolder = ArgumentListItemViewHolder(this)
}