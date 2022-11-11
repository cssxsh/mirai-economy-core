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
    /**
     * 对应的 User
     */
    public val user: User
}

/**
 * 群组账户
 */
public interface GroupEconomyAccount : EconomyAccount {
    /**
     * 对应的 群组
     */
    public val group: Group
}

/**
 * 自定义账户，可以用于实现共享经济/商店经济
 */
public interface CustomEconomyAccount : EconomyAccount {
    /**
     * 创建时间戳
     */
    public val created: Long
}

/**
 * 经济账户 抽象类
 */
public abstract class AbstractEconomyAccount {
    /**
     * 账户ID
     */
    public abstract val uuid: String

    override fun toString(): String = uuid

    override fun equals(other: Any?): Boolean = uuid == (other as? EconomyAccount)?.uuid

    override fun hashCode(): Int = uuid.hashCode()
}

/**
 * 账号管理器，用于管理账户信息
 */
public interface EconomyAccountManager {

    /**
     * 获取对应 [user] 的账户
     * @param user 用户
     */
    @Throws(UnsupportedOperationException::class, NoSuchElementException::class)
    public fun account(user: User): UserEconomyAccount

    /**
     * 获取对应 [group] 的账户
     * @param group 用户
     */
    @Throws(UnsupportedOperationException::class, NoSuchElementException::class)
    public fun account(group: Group): GroupEconomyAccount

    /**
     * 获取对应 [uuid] 的账户
     * @param uuid 用户
     * @param description 用户描述
     */
    @Throws(UnsupportedOperationException::class, NoSuchElementException::class)
    public fun account(uuid: String, description: String? = null): EconomyAccount
}