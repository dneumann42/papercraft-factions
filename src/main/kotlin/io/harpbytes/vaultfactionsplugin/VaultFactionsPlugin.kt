package io.harpbytes.vaultfactionsplugin

import io.harpbytes.commanddispatch.InvalidCommand
import io.harpbytes.commanddispatch.processCommand
import io.papermc.paper.event.player.AsyncChatEvent
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.Bukkit
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.entity.PlayerDeathEvent
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.plugin.java.JavaPlugin
import org.jetbrains.annotations.Async
import java.lang.Exception

fun handleChatCommand(ev: AsyncChatEvent, body: (message: String) -> Unit) =
    try {
        body(PlainTextComponentSerializer.plainText().serialize(ev.message()))
    } catch (ex: InvalidCommand) {
        ev.player.sendRawMessage(ex.toString())
    }

class VaultFactionsPlugin : JavaPlugin(), Listener {
    override fun onEnable() =
        Bukkit.getPluginManager().registerEvents(this, this)

    @EventHandler
    fun onPlayerJoin(ev: PlayerJoinEvent) {
        ev.player.sendRichMessage("Hello <i><yellow>${ev.player.name}</yellow></i>! Welcome to the server.")
        ev.player.sendRichMessage("<i>To see the list of server specific commands please type !help.</i>")
        ev.player.sendRichMessage("<i>To see command palette please type !!</i>")
        ev.player.sendRichMessage("")
    }

    @EventHandler
    fun onPlayerChat(ev: AsyncChatEvent) = handleChatCommand(ev) { msg ->
        if (msg.isNotEmpty()) {
            val response = processCommand(msg.substring(1))
            ev.player.sendMessage(response.contents)
        }
    }
}
