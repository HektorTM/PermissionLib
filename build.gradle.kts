plugins {
    `java-library`
    `maven-publish`
}

group = "com.github.HektorTM"
version = "v1.0.8"

java {
    // Target server-compatible bytecode
    toolchain.languageVersion.set(JavaLanguageVersion.of(21))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    maven {
        name = "papermc"
        url = uri("https://repo.papermc.io/repository/maven-public/")
    }
}

dependencies {
    // Compile against Paper API. (Or swap to spigot-api:1.20.4 for broader compat.)
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}