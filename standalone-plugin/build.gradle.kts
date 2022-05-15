plugins {
    kotlin("jvm")
    `java-gradle-plugin`
    `maven-publish`
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


gradlePlugin {
    plugins {
        create("FileAnalizerPlugin") {
            id = "com.github.andrelugomes.file-analizer"
            implementationClass = "com.github.andrelugomes.fileanalizer.FileAnalizerPlugin"
        }
    }
}