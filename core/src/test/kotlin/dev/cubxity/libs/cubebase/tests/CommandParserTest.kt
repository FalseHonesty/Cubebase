package dev.cubxity.libs.cubebase.tests

import dev.cubxity.libs.cubebase.module.Command
import dev.cubxity.libs.cubebase.utils.CommandParser
import org.junit.Test
import strikt.api.expectThat
import strikt.assertions.containsExactly
import kotlin.test.assertTrue

class CommandParserTest {
    val parameters: List<Command.Parameter>
    val aliases: List<String>

    init {
        val parsed = CommandParser.parseCommandString("h|help|? [command]")

        parameters = parsed.parameters
        aliases = parsed.aliases
    }

    @Test
    fun `Aliases are correctly parsed`() {
        expectThat(aliases)
            .containsExactly("h", "help", "?")
    }
}