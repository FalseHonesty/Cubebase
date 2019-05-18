package dev.cubxity.libs.cubebase.features.processing

import dev.cubxity.libs.cubebase.module.Command
import dev.cubxity.libs.cubebase.module.Module
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

data class CommandProcessingContext(
        val event: GuildMessageReceivedEvent,
        val module: Module,
        val command: Command,
        val args: MutableMap<String, Any> = mutableMapOf(),
        var cancelled: Boolean = false
)

fun CommandProcessingContext.cancel() {
    cancelled = true
}