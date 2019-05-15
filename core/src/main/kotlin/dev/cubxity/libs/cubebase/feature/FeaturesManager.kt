package dev.cubxity.libs.cubebase.feature

import dev.cubxity.libs.cubebase.CubeBase

class FeaturesManager(val bot: CubeBase) {
    /**
     * This function allows the user to install a feature and configure it
     * @param feature the feature to install
     * @param opt callback to configure the feature
     */
    fun <F : BotFeature<C>, C> install(feature: F, opt: C.() -> Unit = {}) {
        feature.install(bot)
        opt.invoke(feature.config)
    }
}