package dev.cubxity.libs.cubebase.module

import dev.cubxity.libs.cubebase.CubeBase

/**
 * This class manages all the modules and handles events
 */
class ModuleManager(val bot: CubeBase) {

    /**
     * This function will create module and return it
     * @param name the name of the module
     * @param opt callback to configure the module
     */
    fun module(name: String, opt: Module.() -> Unit = {}) : Module {

    }
}