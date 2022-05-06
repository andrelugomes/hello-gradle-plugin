plugins {
    kotlin("jvm") version "1.6.20"
}

group = "com.github.andrelugomes"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

abstract class IncrementalTaskExtention {
    var skip: Boolean = false
}

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

    private val extension = project.extensions.findByName("incremental") as IncrementalTaskExtention

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

tasks.register<IncrementalTask>("incremental") {
    input.set(file("$rootDir/src/main/resources/files"))
    output.set(file("$buildDir/outputs/files"))
    //skip = true
}
project.extensions.create("incremental", IncrementalTaskExtention::class.java)

project.extensions.getByName<IncrementalTaskExtention>("incremental").apply {
    skip = true
}
