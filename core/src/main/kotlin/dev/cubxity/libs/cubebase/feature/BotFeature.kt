package dev.cubxity.libs.cubebase.feature

import dev.cubxity.libs.cubebase.CubeBase
import dev.cubxity.libs.cubebase.annotations.BuilderTagMarker

/**
 * The class that feature should extends
 * @param C configuration type
 */
abstract class BotFeature<C : Any> {

    /**
     * The feature's configuration
     */
    abstract val config: C

    /**
     * Function that will be called once the feature is installed
     */
    fun install(bot: CubeBase) {

    }

    /**
     * This function allows the user to configure the plugin
     * @param opt callback to configure [C]
     */
    @BuilderTagMarker
    fun configure(opt: C.() -> Unit = {}) {
        opt.invoke(config)
    }

    /**
     * This function will find feature with the provided [name] then configure it
     */
    fun FeaturesManager.depend(name: String, opt: BotFeature<*>.() -> Unit = {}): BotFeature<*> {

    }
}