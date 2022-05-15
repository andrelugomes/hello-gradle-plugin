package com.github.andrelugomes.fileanalizer

import org.gradle.api.DefaultTask
import org.gradle.api.GradleException
import org.gradle.api.file.DirectoryProperty
import org.gradle.api.file.FileType
import org.gradle.api.tasks.*
import org.gradle.api.tasks.options.Option
import org.gradle.work.ChangeType
import org.gradle.work.Incremental
import org.gradle.work.InputChanges

abstract class IncrementalTask : DefaultTask() {

    @get:Incremental
    @get:PathSensitive(PathSensitivity.NAME_ONLY)
    @get:InputDirectory
    abstract val input: DirectoryProperty

    @get:OutputDirectory
    abstract val output: DirectoryProperty

    @get:Input
    @set:Option(option = "skip", description = "Skip task")
    var skip: Boolean = false

    private val extension = project.extensions.findByName("fileAnalizer") as IncrementalTaskExtention

    private val pattern = """(Lorem|Ipsum)""".toRegex()

    @TaskAction
    fun execute(inputChanges: InputChanges) {
        println(
            if (inputChanges.isIncremental) "Executing incrementally"
            else "Executing non-incrementally"
        )

        println("skip=${skip} | extension.skip=${extension.skip}")
        if (!(skip || extension.skip)) {
            inputChanges.getFileChanges(input).forEach { change ->
                if (change.fileType == FileType.DIRECTORY) return@forEach

                val result = pattern.find(change.file.readText())
                if (result != null) {
                    println("ERROR: ${change.normalizedPath}")
                    println(change.file.readText())

                    throw GradleException("Incremental Task found a file that matches-> ${change.normalizedPath}")
                }

                println("${change.changeType}: ${change.normalizedPath}")
                val targetFile = output.file(change.normalizedPath).get().asFile
                if (change.changeType == ChangeType.REMOVED) {
                    targetFile.delete()
                } else {
                    targetFile.writeText(change.file.readText())
                }
            }
        } else {
            println("Task Skiped!")
        }
    }
}
