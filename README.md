# mirai-economy-core

[![kotlin](https://img.shields.io/badge/kotlin-1.6.21-7B6D9C?logo=kotlin)](https://kotlinlang.org/) [![mirai](https://img.shields.io/badge/mamoe%2Fmirai-2.11.1-blue?logo=github)](https://github.com/mamoe/mirai) [![releases](https://img.shields.io/github/v/release/SkyNet1748/mirai-economy-core?label=%E6%9C%80%E6%96%B0%E7%89%88&logo=Github)](https://github.com/SkyNet1748/mirai-economy-core/releases)

mirai 抽象经济系统核心

## 用途

或许你是一名开发者，想为你的娱乐机器人插件设定一套经济系统。  
经济系统很好，用户可以通过日常活动获取虚拟货币，同时可以使用虚拟货币购买一次命令服务，
这可以有效增加用户活跃度并能限制用户使用机器人的功能。

但是，如果你是自己写，一旦你发布的插件多了起来，事情将会变得很复杂。  
你应该互相对接？还是该写一个中心插件来处理经济操作？

这时，这个抽象的经济系统核心就派上用场了。  
你可以自由实现和注册自己的经济服务，也可以干脆懒一点使用他人写好的经济服务。  
经济接口统一，无论怎么实现用法总是相同的，  
这将极大减少你的工作量，只需调用接口，其他的交给用户安装的经济服务实现即可。

## 用法

[开发人员文档](Developer.md)

下载: [Github Releases](https://github.com/SkyNet1748/mirai-economy-core/releases)

mirai 版本在 2.11 以前的下载 `[Legacy]mirai-economy-core-*.mirai.jar`  
mirai 版本在 2.11 或以后的下载 `mirai-economy-core-*.mirai2.jar`

## 构建

```
./gradlew buildPugin buildPluginLegacy
```

## 许可证

本插件使用 `AGLP-3.0` 许可证开源。  
