plugins {
    `java-library`
    `maven-publish`
}

group = "dev.hektortm"          // JitPack will still publish as com.github.<you>
version = "1.0.1"               // JitPack uses your git tag as the public version

java {
    // Target server-compatible bytecode
    toolchain.languageVersion.set(JavaLanguageVersion.of(17))
    withSourcesJar()
    withJavadocJar()
}

repositories {
    mavenCentral()
    maven("https://repo.papermc.io/repository/maven-public/")
}

dependencies {
    // Compile against Paper API. (Or swap to spigot-api:1.20.4 for broader compat.)
    compileOnly("io.papermc.paper:paper-api:1.21.1-R0.1-SNAPSHOT")
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}