plugins {
    java
    id("com.github.johnrengelman.shadow") version "7.1.2"
}

version = "1.0.0"

repositories {
    mavenCentral()
    mavenLocal()
    maven("https://jitpack.io")
    maven("https://hub.spigotmc.org/nexus/content/repositories/snapshots/")
    maven("https://oss.sonatype.org/content/repositories/snapshots")
    maven("https://oss.sonatype.org/content/repositories/central")
    maven("https://repo.essentialsx.net/releases/")
}

java{
    toolchain{
        languageVersion.set(JavaLanguageVersion.of(17))
    }

    withSourcesJar()
    withJavadocJar()
}

tasks{
    compileJava {
        dependsOn(clean)
        options.encoding = "UTF-8"
    }

    build{
        dependsOn(shadowJar)
    }
}

dependencies {
    compileOnly("org.spigotmc:spigot-api:1.19.3-R0.1-SNAPSHOT")
    compileOnly("net.kyori:adventure-platform-bukkit:4.1.0")

    implementation("com.github.cryptomorin:XSeries:9.3.0") { isTransitive = false }

    compileOnly("org.apache.commons:commons-lang3:3.12.0")
    compileOnly("com.github.ben-manes.caffeine:caffeine:3.1.3")
    compileOnly("com.zaxxer:HikariCP:4.0.3")

    compileOnly("org.projectlombok:lombok:1.18.24")
    compileOnly("org.jetbrains:annotations:24.0.0")

    annotationProcessor("org.projectlombok:lombok:1.18.24")
    annotationProcessor("org.jetbrains:annotations:24.0.0")
}