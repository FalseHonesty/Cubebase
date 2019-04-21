# Starting a bot
```kotlin
    fun main() {
        val bot = createBot("token") { // Registers modules and creates JDA instance
            install(PermissionManager)
            install(MongoDBSupport) {
                host = "localhost:6969"
                user = "root"
                credentials = "toor"
            }
            
            modules {
                moderation()
                utilities()
            }
        }
    }
```

# Module
```kotlin
    fun Modules.moderation(db: Database) {
        module("Moderation") { // Creates a module
            restrict(ModuleRestrictions.MODERATOR) // Plugin's extension
            filter { // Module filter for command execution, events, etc.. But in this case PermissionManager is going to take care of that for us
                
            }
            command("warn <user> [reason]") {
                val u = arg<UserMapper>("user")
                if (u != null) {
                    val reason = arg<StringMapper>("reason", "unknown reason") { rest = true } // Read until next param or the end
                    u.directMessage ({ embed("The record has been stored but user has not been warned").queue() }) {
                        embed("You have been warned in ${event.guild.name} for $reason").queue()
                    }
                    db.store("warnings", WarnRecord(u.id, reason, System.currentTimeMillis()))
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
    }
```