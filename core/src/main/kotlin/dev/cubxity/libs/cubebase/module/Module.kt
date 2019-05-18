package dev.cubxity.libs.cubebase.module

class Module(val name: String, val commands: MutableList<Command> = mutableListOf()) {
    /**
     * Creates a command and run [opt] and return it. [name] is not the same as [aliases], [aliases] will be used for the command processing and [name] will be used for help, error reporting etc..
     * @param name the name of the command
     * @param aliases command aliases
     */
    fun command(name: String, vararg aliases: String, opt: Command.() -> Unit = {}) = Command(Command.Metadata(name), *aliases).apply(opt).let { commands.add(it) }
}