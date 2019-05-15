package dev.cubxity.libs.cubebase

import club.minnced.jda.reactor.ReactiveEventManager
import dev.cubxity.libs.cubebase.annotations.BuilderTagMarker
import dev.cubxity.libs.cubebase.module.ModuleManager
import dev.cubxity.libs.cubebase.feature.FeaturesManager
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
     * The instance of [ModuleManager] for modules and event processing
     */
    val moduleManager = ModuleManager(this)

    /**
     * The instance of [FeaturesManager] for managing features
     */
    val featuresManager = FeaturesManager(this)

    /**
     * This function starts the bot and builds the JDA instances
     * @param wait block until JDA is ready
     */
    fun start(wait: Boolean = false) {
        jda = JDABuilder(token)
                .setEventManager(manager)
                .apply {
                    for (i in 0 until shards)
                        useSharding(i, shards)
                }
                .build()
        if (wait)
            jda.awaitReady()
    }

    /**
     * This function accepts a lambda to configure [ModuleManager]
     * @param opt callback to configure [ModuleManager]
     */
    @BuilderTagMarker
    fun modules(opt: ModuleManager.() -> Unit = {}) {
        opt.invoke(moduleManager)
    }

    /**
     * This function accepts a lambda to configure [FeaturesManager]
     * @param opt callback to configure [FeaturesManager]
     */
    @BuilderTagMarker
    fun features(opt: FeaturesManager.() -> Unit = {}) {
        opt.invoke(featuresManager)
    }
}
