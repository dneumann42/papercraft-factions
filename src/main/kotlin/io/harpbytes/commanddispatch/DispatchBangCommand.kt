package io.harpbytes.commanddispatch

import io.harpbytes.tui.Menu
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.ComponentBuilder
import net.kyori.adventure.text.format.TextColor
import java.lang.Exception

class InvalidCommand(msg: String) : Exception(msg)

data class CommandResponse(val contents: Component)

fun tokenizeCommand(command: String): List<String> =
    command.split(" ").filter { it.isNotBlank() }

fun commandPalette() {
    val menu = Menu {
        println(it)
    }
        .add(
            Component.text("Hello").color(TextColor.color(255, 0, 255))
        ) {
            Component.text("World").color(TextColor.color(255, 255, 0))
        }
}

fun processQuickCommands(
    command: Char
) = when (command) {
    '!' -> CommandResponse(Component.text("Nice!"))
    else -> throw InvalidCommand("unknown quick command $command")
}

fun processCommand(
    command: String
): CommandResponse = tokenizeCommand(command).let { tokens ->
    when (tokens.size) {
        0 -> throw InvalidCommand("command missing")
        1 -> processQuickCommands(tokens.first().first())
        else -> throw InvalidCommand("unknown command $command")
    }
}

