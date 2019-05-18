package dev.cubxity.libs.cubebase.tests

import dev.cubxity.libs.cubebase.utils.CommandParser
import org.junit.Test
import kotlin.test.assertTrue

class CommandParserTest {
    @Test
    fun `Check command string parser`() {
        val (params, aliases) = CommandParser.parseCommandString("h|help|? [command]")
        assertTrue("Expected aliases to be h, help and ?") { aliases.contentEquals(arrayOf("h", "help", "?")) }

    }
}