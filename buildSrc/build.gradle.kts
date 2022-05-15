plugins {
    kotlin("jvm") version "1.6.20"
}

repositories {
    mavenCentral()
}

group = "com.github.andrelugomes.buildsrc"
version = "1.0.0-SNAPSHOT"

dependencies {
    implementation(kotlin("stdlib"))
    implementation(gradleApi())
}
