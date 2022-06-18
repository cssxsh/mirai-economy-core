package io.github.skynet1748.mirai.economy

import io.github.skynet1748.mirai.economy.service.IEconomyService

/**
 * 经济服务实例
 * @see IEconomyService
 * @see IEconomyService.create
 */
public object EconomyService : IEconomyService by IEconomyService.create()