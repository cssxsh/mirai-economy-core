package io.github.skynet1748.mirai.economy.service

import net.mamoe.mirai.contact.*

/**
 * 经济账户
 */
public sealed interface EconomyAccount {
    public val uuid: String
    public val description: String
}

/**
 * 用户账户
 */
public interface UserEconomyAccount : EconomyAccount {
    public val user: User
}

/**
 * 群组账户
 */
public interface GroupEconomyAccount : EconomyAccount {
    public val group: Group
}

/**
 * 自定义账户，可以用于实现共享经济/商店经济
 */
public interface CustomEconomyAccount : EconomyAccount

/**
 * 空账户，可以无限转入转出
 */
public interface EmptyEconomyAccount : EconomyAccount