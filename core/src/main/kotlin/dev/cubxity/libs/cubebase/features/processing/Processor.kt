package dev.cubxity.libs.cubebase.features.processing

import club.minnced.jda.reactor.on
import dev.cubxity.libs.cubebase.CubeBase
import dev.cubxity.libs.cubebase.annotations.BuilderTagMarker
import dev.cubxity.libs.cubebase.features.BotFeature
import dev.cubxity.libs.cubebase.features.install
import dev.cubxity.libs.cubebase.module.Module
import dev.cubxity.libs.cubebase.pipeline.CommandProcessingPipeline
import net.dv8tion.jda.api.entities.Guild
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent

/**
 * The processor for module and commands
 */
class Processor(val bot: CubeBase) {

    /**
     * Factory for providing prefix for the specified guild
     */
    var prefixFactory: PrefixFactory = { "." }

    /**
     * Factory for providing permission for the specified executor
     */
    var permissionFactory: PermissionFactory = { true }

    /**
     * List of modules that the processor should process
     */
    val modules = mutableListOf<Module>()

    /**
     * This method processes the command
     */
    fun processEvent(e: GuildMessageReceivedEvent) {
        val msg = e.message.contentRaw.ifEmpty { return }
        val p = prefixFactory.invoke(e.guild)
        val args = msg.substring(p.length).split(' ')
        // Check if the message starts with the prefix
        if (msg.startsWith(p)) {
            val c = args[0].ifEmpty { return }.toLowerCase()
            modules.forEach { m ->
                val cmd = m.commands.find { it.aliases.contains(c) } ?: return@forEach
                val ctx = CommandProcessingContext(e, m, cmd)
                // Feature filtering & permission filtering
                bot.run(CommandProcessingPipeline.FILTER, ctx) {
                    if (!permissionFactory.invoke(ctx)) cancelled = true
                }
                if (ctx.cancelled) return

                // Logging, etc..
                bot.run(CommandProcessingPipeline.MONITORING, ctx)

                // Parsing
                bot.run(CommandProcessingPipeline.PARSE, ctx) {

                }
                if (ctx.cancelled) return

                // Processing

                return
            }
        }
    }

    data class Config(var prefixFactory: PrefixFactory = { "." }, val modules: MutableList<Module> = mutableListOf())

    companion object Feature : BotFeature<CubeBase, Processor, Processor> {
        override val key = "cubebase.features.processor"

        override fun install(pipeline: CubeBase, configure: Processor.() -> Unit): Processor {
            val feature = Processor(pipeline)
            pipeline.manager
                    .on<GuildMessageReceivedEvent>()
                    .next()
                    .subscribe { feature.processEvent(it) }
            return feature
        }
    }
}

/**
 * Get or install [Processor] feature and run [opt] on it
 */
@BuilderTagMarker
fun CubeBase.processing(opt: Processor.() -> Unit = {}): Processor = features[Processor.key] as Processor?
        ?: install(Processor)

/**
 * Creates a module and add it to the config, run [opt] on it, add it then return it
 * @param name the name for the module
 */
fun Processor.module(name: String, opt: Module.() -> Unit = {}) = Module(name).apply(opt).let { modules.add(it) }

typealias PrefixFactory = (Guild) -> String

typealias PermissionFactory = (CommandProcessingContext) -> Boolean