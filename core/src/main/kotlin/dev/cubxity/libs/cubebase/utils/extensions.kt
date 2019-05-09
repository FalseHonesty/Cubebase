package dev.cubxity.libs.cubebase.utils

import dev.cubxity.libs.cubebase.CubeBase

/**
 * This function creates an instance of [CubeBase] with the specified [token]
 *
 * @param token The bot application token
 * @param opt Function to modify [CubeBase]
 */
fun createBot(token: String, opt: CubeBase.() -> Unit = {}) = opt.invoke(CubeBase(token))

/**
 * This function creates an instance of [CubeBase] with the specified [token]
 *
 * @param token The bot application token
 * @param shards The amount of shards to create (This should only be used with big bots)
 * @param opt Function to modify [CubeBase]
 */
fun createBot(token: String, shards: Int, opt: CubeBase.() -> Unit = {}) = opt.invoke(CubeBase(token, shards))