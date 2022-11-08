package xyz.cssxsh.mirai.economy.event;

import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.service.*

/**
 * 经济服务相关插件
 */
public interface EconomyEvent : Event {
    /**
     * 发出事件的服务
     */
    public val service: IEconomyService
}