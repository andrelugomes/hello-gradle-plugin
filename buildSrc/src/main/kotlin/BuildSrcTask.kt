package com.github.andrelugomes.buildsrc

import org.gradle.api.DefaultTask
import org.gradle.api.provider.Property
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.TaskAction

abstract class BuildSrcTask : DefaultTask() {

    @get:Input
    abstract val greeting: Property<String>

    init {
        greeting.convention("hello from BuildSrcTask")
    }

    @TaskAction
    fun greet() {
        println(greeting.get())
    }
}