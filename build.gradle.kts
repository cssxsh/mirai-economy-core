plugins {
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.serialization") version "1.6.21"

    id("net.mamoe.mirai-console") version "2.11.1"
    id("net.mamoe.maven-central-publish") version "0.7.1"

    id("me.him188.kotlin-jvm-blocking-bridge") version "2.0.0-162.1"
}

group = "io.github.skynet1748"
version = "1.0.0-dev"

mavenCentralPublish {
    useCentralS01()
    githubProject("skynet1748", "mirai-economy-core")
    licenseFromGitHubProject("AGPL-3.0", "master")
    publication {
        artifact(tasks.getByName("buildPlugin"))
        artifact(tasks.getByName("buildPluginLegacy"))
    }
}

repositories {
    mavenLocal()
    mavenCentral()
}

dependencies {
    compileOnly("net.mamoe:mirai-core:2.11.1")
    compileOnly("net.mamoe:mirai-core-utils:2.11.1")
    compileOnly("me.him188:kotlin-jvm-blocking-bridge-runtime-jvm:2.0.0-162.1")
    api("com.cronutils:cron-utils:9.1.6") {
        exclude("org.slf4j")
        exclude("org.glassfish")
        exclude("org.javassist")
    }
    compileOnly("javax.validation:validation-api:2.0.1.Final")

    testImplementation(kotlin("test", "1.6.21"))
}

kotlin {
    explicitApi()
}

tasks {
    test {
        useJUnitPlatform()
    }
}
