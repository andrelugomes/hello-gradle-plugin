plugins {
    kotlin("jvm")
}

group = "com.github.andrelugomes.task"
version = "1.0.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
}

//tasks.register<com.github.andrelugomes.task.StandAloneTask>("greeting-from-standalone-task")
