# Starting a bot
```kotlin
    fun main() {
        val bot = createBot("token") { // Registers modules and creates JDA instance
            install(PermissionManager) {
                role(ModuleRestrictions.MODERATOR) {
                    permissions += Permission.BAN_MEMBER // Required permission
                }
            }
            install(MongoDBSupport) {
                host = "localhost:6969"
                user = "root"
                password = "toor"
            }
            
            modules {
                moderation()
                utilities()
            }
        }.start(wait = true)
    }
```

# Module
```kotlin
    fun Modules.moderation(db: Database) = module("Moderation") { // Creates a module
            restrict(ModuleRestrictions.MODERATOR) // Plugin's extension
            filter { // Module filter for command execution, events, etc.. But in this case PermissionManager is going to take care of that for us
                
            }
            // 3 dots (...) behind the name for rest of command
            command("warn <user> [reason...]") { // this: ExecutionContext
                val u = arg<UserMapper>("user")
                if (u != null) {
                    val reason = arg<StringMapper>("reason", "unknown reason")
                    u.dm()
                        .doOnSuccess {
                            embed("You have been warned in ${event.guild.name} for $reason").queue()
                        }
                        .doOnError {
                            embed("The record has been stored but user has not been warned").queue()
                        }
                        .subscribe()
                    db.store(guild, "warnings", WarnRecord(u.id, reason, System.currentTimeMillis()))
                        .fail {
                            embed()
                                .bold("FATAL ERROR: ")
                                .append("Could not save the warn record into the database")
                                .queue()
                            // Report to bot admin
                        }
                        .success {
                            embed("Warning has been recorded for ${u.mention}").queue()
                        }
                        .queue()
                } else {
                    embed("The mentioned user could not be found")
                }
            }
        }
```

# Plugin
```kotlin
object PermissionManager : Plugin<PMConfig> {
    class PMConfig(defaultRoles: MutableList<BotRole>)
    data class BotRole(
        @BsonId
        val name: String,
        val permissions: MutableList<Permission> = mutableListOf(),
        val includes: MutableList<String> = mutableListOf(),
        val excludes: MutableList<String> = mutableListOf()
    )
}

fun PMConfig.role(name: String, opt: BotRole.() -> Unit = {}) {
    val r = BotRole()
    r.invoke(opt)
    defaultRoles += r
}

fun Module.restrict(name: String) {
    filter { // this: ExecutionContext
        // Get bot roles from the db
        var br = db.get(guild, "roles", BotRole::name eq name)
        if (br == null) {
            val default = PermissionManager.config.defaultRoles.find { it.name == name } ?: return@filter
            br = default
        }
        if (!br.permissions.any { it in user.permissions } || br.excludes.contains(user.id) || !br.includes.contains(user.id))
            deny() // ExecutionContext: stop execution
    }
}
```