import com.github.andrelugomes.buildsrc.BuildSrcTask

buildscript {
    repositories {
        mavenLocal()
        mavenCentral()
    }

    dependencies {
        classpath(files("standalone-task/build/libs/standalone-task-1.0.0-SNAPSHOT.jar"))
    }
}

plugins {
    kotlin("jvm") version "1.6.20"

    id("com.github.andrelugomes.file-analizer") version "1.0.0-SNAPSHOT"
}

group = "com.github.andrelugomes"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))

    testImplementation("org.junit.jupiter:junit-jupiter:5.5.2")
}

//Simple Task
tasks.register("hello") {
    println("Hello World!")
}

//Greeting Task
/*abstract class GreetingTask : DefaultTask() {
    @TaskAction
    fun greet() {
        println("hello from GreetingTask")
    }
}*/
abstract class GreetingTask : DefaultTask() {
    @get:Input
    abstract val greeting: Property<String>

    init {
        greeting.convention("hello from GreetingTask")
    }

    @TaskAction
    fun greet() {
        println(greeting.get())
    }
}
tasks.register<GreetingTask>("greeting")

/*tasks.register<GreetingTask>("greeting") {
    greeting.set("greetings from GreetingTask Overrided")
}*/

// From buildSrc
tasks.register<BuildSrcTask>("greeting-from-buildsrc")

// From standalone-task project/module
tasks.register<com.github.andrelugomes.task.StandAloneTask>("greeting-from-standalone-task") // registered in module



// From Plugin
tasks.fileAnalizerTask {
    input.set(file("$rootDir/src/main/resources/files"))
    output.set(file("$buildDir/outputs/files"))
}
// Extention
fileAnalizer {
    skip = false
}


