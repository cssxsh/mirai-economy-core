package xyz.cssxsh.mirai.economy.service

import net.mamoe.mirai.contact.*
import kotlin.jvm.*

/**
 * 经济账户
 */
public sealed interface EconomyAccount {
    /**
     * 账户ID
     */
    public val uuid: String

    /**
     * 账户简介
     */
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

public interface EconomyAccountManager {

    @Throws(UnsupportedOperationException::class, NoSuchElementException::class)
    public fun account(user: User): UserEconomyAccount

    @Throws(UnsupportedOperationException::class, NoSuchElementException::class)
    public fun account(group: Group): GroupEconomyAccount

    @Throws(UnsupportedOperationException::class, NoSuchElementException::class)
    public fun account(uuid: String, description: String): CustomEconomyAccount
}