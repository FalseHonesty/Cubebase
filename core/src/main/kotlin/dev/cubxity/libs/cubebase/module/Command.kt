package dev.cubxity.libs.cubebase.module

class Command(val metadata: Metadata, vararg val aliases: String) {
    val permission: String? = null

    data class Metadata(val name: String, val description: String = "No description provided")
}