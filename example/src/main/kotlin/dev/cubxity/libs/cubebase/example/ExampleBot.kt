package dev.cubxity.libs.cubebase.example

import dev.cubxity.libs.cubebase.db.DBPlugin
import dev.cubxity.libs.cubebase.module.ModuleManager
import dev.cubxity.libs.cubebase.utils.createBot

fun main() {
    createBot(System.getenv("token")) {
        // Creates a bot with token from environment variable
        features {
            install(DBPlugin) {

            }
        }

        modules {
            // Add the modules
            test()
        }
    }
}

fun ModuleManager.test() = module("test") {

}