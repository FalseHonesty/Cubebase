package dev.cubxity.libs.cubebase.features.embed

import dev.cubxity.libs.cubebase.features.BotFeature
import dev.cubxity.libs.cubebase.pipeline.CommandProcessingPipeline

class EmbedFeature(val conf: Config) {

    data class Config(val footerFactory: () -> String? = { null })

    companion object Feature : BotFeature<CommandProcessingPipeline, Config, EmbedFeature> {
        override val key = "cubebase.features.embed"

        override fun install(pipeline: CommandProcessingPipeline, configure: Config.() -> Unit): EmbedFeature {
            val conf = Config().apply(configure)

            return EmbedFeature(conf)
        }
    }
}