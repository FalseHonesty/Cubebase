package dev.cubxity.libs.cubebase.pipeline

open class Pipeline<TContext>(vararg phases: String) {
    val features = mutableMapOf<String, Any>()
    val phases = hashMapOf<String, MutableList<TContext.() -> Unit>>().apply { phases.forEach { put(it, mutableListOf()) } }

    /**
     * Intercepts the pipeline in the [phase], [opt] will be ran before the [phase] runs
     */
    fun intercept(phase: String, opt: TContext.() -> Unit = {}) = phases[phase]?.add(opt)
            ?: throw IllegalArgumentException("Invalid phase")

    /**
     * Runs the pipeline phase with context [ctx]
     */
    fun run(phase: String, ctx: TContext, opt: TContext.() -> Unit = {}) {
        if (phases.containsKey(phase)) {
            phases[phase]!!.forEach { it.invoke(ctx) }
            opt.invoke(ctx)
        } else throw IllegalArgumentException("Invalid phase")
    }
}