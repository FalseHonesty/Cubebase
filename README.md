# Cubebase (W.I.P)
Cubebase is a fully modular, powerful JDA command processor library with useful utilities and diverse support written in Kotlin. This library is heavily inspired by [ktor](https://github.com/ktorio/ktor).

## License
Cubebase is licensed under GNU AGPLv3, read more about the license [here](LICENSE)

## JDA 4.x BETA
This libraries make use of JDA (Java Discord API) which is a wrapper for Discord APIs. JDA 4.x is currently still in beta. Therefore this library might be unstable.

## Getting Started
Check [examples](example) for detailed example

**Starting a bot:**
```kotlin
fun main() {
    // Creates a bot with token from environment variable
    createBot(System.getenv("token")) {
        processing {
            // Create a simple module
            module("test") {
                
            }
        }
    }
}
```

## Libraries
- [JDA](https://github.com/DV8FromTheWorld/JDA)
- [jda-reactor](https://github.com/MinnDevelopment/jda-reactor)