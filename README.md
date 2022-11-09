# Mirai Economy Core

> Mirai 经济服务前置插件

[![Release](https://img.shields.io/github/v/release/cssxsh/mirai-economy-core)](https://github.com/cssxsh/mirai-economy-core/releases)
[![Downloads](https://img.shields.io/github/downloads/cssxsh/mirai-economy-core/total)](https://repo1.maven.org/maven2/xyz/cssxsh/mirai/mirai-economy-core/)
[![maven-central](https://img.shields.io/maven-central/v/xyz.cssxsh.mirai/mirai-economy-core)](https://search.maven.org/artifact/xyz.cssxsh.mirai/mirai-economy-core)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/18191e26563d47eaa3354d43cfa57ff6)](https://www.codacy.com/gh/cssxsh/mirai-economy-core/dashboard?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=cssxsh/mirai-economy-core&amp;utm_campaign=Badge_Grade)

**目前需要 [Mirai Hibernate Plugin](https://github.com/cssxsh/mirai-hibernate-plugin) 前置才能使用**

## 在 Mirai Console Plugin 项目中引用

```kotlin
repositories {
    mavenCentral()
}

dependencies {
    compileOnly("xyz.cssxsh.mirai:mirai-economy-core:${version}")
}

// hibernate 6 和 HikariCP 5 需要 jdk11
mirai {
    jvmTarget = JavaVersion.VERSION_11
}
```

### 示例代码

*   [kotlin](src/test/kotlin/xyz/cssxsh/mirai/economy/EconomyServiceTest.kt)
*   [java](src/test/java/xyz/cssxsh/mirai/economy/EconomyUtilsTest.java)