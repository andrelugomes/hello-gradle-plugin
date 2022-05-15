package com.github.andrelugomes.fileanalizer

import org.gradle.api.Plugin
import org.gradle.api.Project

class FileAnalizerPlugin : Plugin<Project> {

    override fun apply(project: Project) {
        project.extensions.create("fileAnalizer", IncrementalTaskExtention::class.java)
        val task = project.tasks.create("fileAnalizerTask", IncrementalTask::class.java)
        task.group = "verification"
    }
}