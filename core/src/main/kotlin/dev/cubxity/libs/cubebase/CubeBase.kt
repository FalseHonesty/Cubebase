package dev.cubxity.libs.cubebase

import club.minnced.jda.reactor.ReactiveEventManager
import dev.cubxity.libs.cubebase.utils.createBot
import net.dv8tion.jda.api.JDA
import net.dv8tion.jda.api.JDABuilder

/**
 * This is the main wrapper class, create the instance directly or use the helper function: [createBot]
 */
class CubeBase(val token: String, val shards: Int = 1) {
    /**
     * The instance of [JDA], this will be initialized once [start] is called
     */
    lateinit var jda: JDA

    /**
     * The instance of [ReactiveEventManager] for Flux event streams
     */
    val manager = ReactiveEventManager()

    /**
     * This function starts the bot and builds the JDA instances
     */
    fun start(wait: Boolean) {
        JDABuilder(token)
                .setEventManager(manager)
                .apply {
                    for (i in 0 until shards)
                        useSharding(i, shards)
                }
                .build()
    }
}
