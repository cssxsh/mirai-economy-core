plugins {
    kotlin("jvm") version "1.7.22"
    kotlin("plugin.jpa") version "1.7.22"
    kotlin("plugin.serialization") version "1.7.22"

    id("net.mamoe.mirai-console") version "2.13.2"
    id("me.him188.maven-central-publish") version "1.0.0-dev-3"
}

group = "xyz.cssxsh.mirai"
version = "1.0.6"

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
    compileOnly("xyz.cssxsh.mirai:mirai-script-plugin:1.0.2")
    compileOnly("xyz.cssxsh.mirai:mirai-hibernate-plugin:2.5.1")
    testImplementation(kotlin("test"))
    testImplementation("xyz.cssxsh.mirai:mirai-hibernate-plugin:2.5.1")
    testImplementation("org.luaj:luaj-jse:3.0.1")
    testImplementation("org.graalvm.js:js:22.2.0")
    testImplementation("org.graalvm.js:js-scriptengine:22.2.0")
    testImplementation("org.python:jython-standalone:2.7.3")
    //
    implementation(platform("net.mamoe:mirai-bom:2.13.2"))
    compileOnly("net.mamoe:mirai-console-compiler-common")
    testImplementation("net.mamoe:mirai-logging-slf4j")
    //
    implementation(platform("org.slf4j:slf4j-parent:2.0.6"))
    testImplementation("org.slf4j:slf4j-simple")
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
