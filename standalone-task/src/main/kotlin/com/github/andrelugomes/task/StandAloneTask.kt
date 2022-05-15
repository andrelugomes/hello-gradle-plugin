package com.github.andrelugomes.task

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class StandAloneTask : DefaultTask() {

    @get:Input
    abstract val greeting: Property<String>

    init {
        greeting.convention("hello from StandAloneTask")
    }

    @TaskAction
    fun greet() {
        println(greeting.get())
    }
}