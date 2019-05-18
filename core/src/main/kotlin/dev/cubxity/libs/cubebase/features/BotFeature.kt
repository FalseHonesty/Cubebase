package dev.cubxity.libs.cubebase.features

import dev.cubxity.libs.cubebase.pipeline.Pipeline

/**
 * Defines an installable bot feature
 * @param TPipeline is the type of the pipeline this feature is compatible with
 * @param TConfiguration is the type for the configuration object for this Feature
 * @param TFeature is the type for the instance of the Feature object
 */
interface BotFeature<in TPipeline : Pipeline<*>, out TConfiguration : Any, TFeature : Any> {
    /**
     * Unique id that identifies the feature
     */
    val key: String

    /**
     * Function to install the feature
     */
    fun install(pipeline: TPipeline, configure: TConfiguration.() -> Unit): TFeature
}

/**
 * Installs [feature] into current pipeline
 * @param feature the feature to install
 * @param opt callback to configure the feature
 */
fun <P : Pipeline<*>, B : Any, F : Any> P.install(
        feature: BotFeature<P, B, F>,
        opt: B.() -> Unit = {}
): F {
    return with (features[feature.key]) {
        when(this) {
            null -> {
                val f = feature.install(this@install, opt)
                features[feature.key] = f
                return f as F
            }
            else -> this as F
        }
    }

}