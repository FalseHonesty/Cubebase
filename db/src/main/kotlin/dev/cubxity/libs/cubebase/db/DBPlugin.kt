package dev.cubxity.libs.cubebase.db

import dev.cubxity.libs.cubebase.feature.BotFeature

object DBPlugin : BotFeature<DBPlugin.Configuration>() {
    override val config = Configuration()

    class Configuration {

    }
}