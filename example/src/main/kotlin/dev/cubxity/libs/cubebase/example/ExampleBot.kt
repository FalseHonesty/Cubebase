package dev.cubxity.libs.cubebase.example

import dev.cubxity.libs.cubebase.features.embed.EmbedFeature
import dev.cubxity.libs.cubebase.features.install
import dev.cubxity.libs.cubebase.features.processing.Processor
import dev.cubxity.libs.cubebase.features.processing.module
import dev.cubxity.libs.cubebase.features.processing.processing
import dev.cubxity.libs.cubebase.utils.createBot

fun main() {
    // Creates a bot with token from environment variable
    createBot(System.getenv("token")) {
        // Installs
        install(EmbedFeature) {

        }

        // Installs modules
        processing {
            prefixFactory = { "!" }
            test()
        }
    }.start()
}

fun Processor.test() = module("test") {

}