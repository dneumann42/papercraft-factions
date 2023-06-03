package io.harpbytes.tui

import net.kyori.adventure.text.Component
import net.kyori.adventure.text.event.ClickEvent

data class MenuItem(
    val label: Component,
    val callback: () -> Component,
    val onResults: (component: Component) -> Unit
) {
    init {
        label.clickEvent(ClickEvent.callback { onResults(callback()) })
    }
}

data class Menu(val items: List<MenuItem> = emptyList(), val onResults: (component: Component) -> Unit) {
    fun add(label: Component, callback: () -> Component): Menu =
        Menu(
            items = this.items + listOf(
                MenuItem(
                    label = label,
                    callback = callback,
                    onResults = onResults
                )
            ),
            onResults = this.onResults
        )
}