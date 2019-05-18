package dev.cubxity.libs.cubebase.module

import dev.cubxity.libs.cubebase.module.Command.Metadata
import dev.cubxity.libs.cubebase.utils.CommandParser

/**
 * Class representing a command
 * @param metadata [Metadata] for the command
 * @param aliases aliases for the command
 */
class Command(val metadata: Metadata, var parameters: Array<Parameter>, vararg var aliases: String) {
    val permission: String? = null

    /**
     * Metadata for [Command]
     * @param name name of the command (DOES NOT affect command processing, [aliases] is used for command processing
     * @param description description of the command, this will be used in help command and etc..
     */
    data class Metadata(var name: String, var description: String = "No description provided")

    /**
     * Parameter for [Command]
     * @param name unique name for the parameter. If [name] ends with ... the ... will be removed
     * @param required if the parameter is required, if false the parameter must be last or after any required ones
     * @param continuous if the parameter is a continuous parameter, not every type supports it. This should only be used in the last parameter. Default: if [name] ends with ...
     */
    data class Parameter(var name: String, var required: Boolean = true, var continuous: Boolean = name.endsWith("...")) {
        init {
            if (name.endsWith("..."))
                name = name.substringBeforeLast("...")
        }
    }

    /**
     * Secondary constructor for [Command]
     * @param metadata [Metadata] for the command
     * @param commandStr command string that will be parsed into parameters and aliases, example: help|h|? \[command]
     */
    constructor(metadata: Metadata, commandStr: String) : this(metadata, arrayOf()) {
        val (params, aliases) = CommandParser.parseCommandString(commandStr)
        parameters = params
        this.aliases = aliases
    }

}