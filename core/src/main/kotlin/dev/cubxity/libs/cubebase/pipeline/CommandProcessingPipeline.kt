package dev.cubxity.libs.cubebase.pipeline

import dev.cubxity.libs.cubebase.features.processing.CommandProcessingContext

open class CommandProcessingPipeline : Pipeline<CommandProcessingContext>(FILTER, MONITORING, PARSE, PROCESS) {
    companion object BotPhase {
        /**
         * Phase for filtering commands
         */
        const val FILTER = "Filter"

        /**
         * Phase for tracing commands, useful for logging, metrics, etc..
         * canceling in the phase will cancel [PROCESS] phase, use [FILTER] instead
         */
        const val MONITORING = "Monitoring"

        /**
         * Phase for parsing command, useful for additional parsing etc..
         */
        const val PARSE = "Parse"

        /**
         * Phase for processing command and sending a response
         */
        const val PROCESS = "Process"
    }
}