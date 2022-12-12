# Mirai Economy Core

> Mirai 经济服务前置插件

[![maven-central](https://img.shields.io/maven-central/v/xyz.cssxsh.mirai/mirai-economy-core)](https://search.maven.org/artifact/xyz.cssxsh.mirai/mirai-economy-core)
[![Jpa Economy Service Test](https://github.com/cssxsh/mirai-economy-core/actions/workflows/Test.yml/badge.svg)](https://github.com/cssxsh/mirai-economy-core/actions/workflows/Test.yml)
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

### 自定义货币

自定义货币 提供以下两种方式

1.  实现 `xyz.cssxsh.mirai.economy.service.EconomyCurrency` 接口  
    然后使用 `EconomyService.register` 注册货币

2.  脚本文件夹, 例如 [Lua](example/currencies/Lua) 或 [TXT](example/currencies/Him188), 文件夹名称将是 货币ID  
    也支持打包为 Zip 压缩包, 此时压缩包名称将是 货币ID  
    可以使用 `EconomyScriptCurrency.fromFolder`  或者 `EconomyScriptCurrency.fromZip` 手动加载, 然后手动注册  
    也可以放到 `data/xyz.cssxsh.mirai.plugin.mirai-economy-core/currencies` 中，在经济系统初始化时自动加载并注册

## [爱发电](https://afdian.net/@cssxsh)

![afdian](example/sponsor/afdian.jpg)