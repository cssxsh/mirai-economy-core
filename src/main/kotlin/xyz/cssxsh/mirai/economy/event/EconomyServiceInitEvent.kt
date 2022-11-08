package xyz.cssxsh.mirai.economy.event

import net.mamoe.mirai.event.*
import xyz.cssxsh.mirai.economy.service.*

/**
 * 经济服务初始化事件
 */
public class EconomyServiceInitEvent(override val service: IEconomyService) : EconomyEvent, AbstractEvent()