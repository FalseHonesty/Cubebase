package dev.cubxity.libs.cubebase.utils

import dev.cubxity.libs.cubebase.module.Command
import java.util.regex.Pattern


object CommandParser {
    val commandStringCompile = Pattern.compile("(?<aliases>[^ ]+) (?<param>([<\\[])[^ ]+([]>]) ?)+")

    fun parseCommandString(string: String): CommandParserOutput {
        val res = commandStringCompile.matcher(string)
        res.find()
        val aliases = res.group("aliases").split('|')
        return CommandParserOutput(listOf(), aliases)
    }

    class CommandParserOutput(val parameters: List<Command.Parameter>, val aliases: List<String>) {
        operator fun component1() = parameters
        operator fun component2() = aliases
    }
}