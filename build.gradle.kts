plugins {
    kotlin("jvm") version "1.7.20"
    kotlin("plugin.jpa") version "1.7.20"
    kotlin("plugin.serialization") version "1.7.20"

    id("net.mamoe.mirai-console") version "2.13.0-RC2"
    id("me.him188.maven-central-publish") version "1.0.0-dev-3"
}

group = "xyz.cssxsh.mirai"
version = "1.0.2"

mavenCentralPublish {
    useCentralS01()
    singleDevGithubProject("cssxsh", "mirai-economy-core")
    licenseFromGitHubProject("AGPL-3.0")
    workingDir = System.getenv("PUBLICATION_TEMP")?.let { file(it).resolve(projectName) }
        ?: buildDir.resolve("publishing-tmp")
    publication {
        artifact(tasks["buildPlugin"])
    }
}

repositories {
    mavenCentral()
}

dependencies {
    compileOnly("net.mamoe:mirai-core:2.13.0")
    compileOnly("net.mamoe:mirai-core-utils:2.13.0")
    compileOnly("xyz.cssxsh.mirai:mirai-script-plugin:1.0.2")
    compileOnly("xyz.cssxsh.mirai:mirai-hibernate-plugin:2.5.0")
    compileOnly("com.google.protobuf:protobuf-java:3.21.9")
    testImplementation(kotlin("test"))
    testImplementation("org.slf4j:slf4j-simple:2.0.3")
    testImplementation("net.mamoe:mirai-logging-slf4j:2.13.0")
    testImplementation("xyz.cssxsh.mirai:mirai-hibernate-plugin:2.5.0")
    // lua
    testImplementation("org.luaj:luaj-jse:3.0.1")
    // js
    testImplementation("org.graalvm.js:js:22.2.0")
    testImplementation("org.graalvm.js:js-scriptengine:22.2.0")
    // python
    testImplementation("org.python:jython-standalone:2.7.3")
}

kotlin {
    explicitApi()
}

mirai {
    jvmTarget = JavaVersion.VERSION_11
}

tasks {
    test {
        useJUnitPlatform()
    }
}
