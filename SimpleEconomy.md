## 默认经济实现

### 命令

指令权限ID 格式为 io.github.skynet1748.mirai.economy:command.*, * 是指令的第一指令名
例如 /economy 的权限ID为 io.github.skynet1748.mirai.economy:command.economy

| 命令/参数                                       |                 描述 |
|:--------------------------------------------|-------------------:|
| /economy get \<id\>                         | 获取用户金钱 (控制台,全局上下文) |
| /economy get [id]                           |   获取用户金钱 (自动获取上下文) |
| /economy set \<id\> \<money\>               | 设置用户金钱 (控制台,全局上下文) |
| /economy set [id] \<money\>                 |   设置用户金钱 (自动获取上下文) |
| //economy get <ecoContext> \<id\>           |             获取用户金钱 |
| //economy set <ecoContext> \<id\> \<money\> |             设置用户金钱 |
