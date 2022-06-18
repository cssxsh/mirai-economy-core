package io.github.skynet1748.mirai.economy.events

import io.github.skynet1748.mirai.economy.IEconomyService
import net.mamoe.mirai.event.AbstractEvent

public class EconomyApiRegisterEvent(
    public var pluginId: String,
    public val service: IEconomyService,
    public var isSetToDefault: Boolean) : AbstractEvent()

public class EconomyApiUnRegisterEvent(
    public val pluginId: String,
    public val service: IEconomyService) : AbstractEvent()