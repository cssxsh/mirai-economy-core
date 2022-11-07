package xyz.cssxsh.mirai.economy

import xyz.cssxsh.mirai.economy.service.*

/**
 * 经济服务实例
 *
 * 提供一个经济服务需要 实现 [IEconomyService] 并 继承 [AbstractEconomyService]
 *
 * 然后在 META-INF.services 中, 为 [IEconomyService] 添加类路径
 *
 * 通过 [IEconomyService.Factory.create] 可以创建服务实例
 *
 * 可以使用 [EconomyServiceName] 标记服务名称，以在创建实例时指定
 *
 * @see IEconomyService
 * @see AbstractEconomyService
 * @see EconomyServiceName
 * @see IEconomyService.Factory.create
 */
public object EconomyService : IEconomyService by IEconomyService.create(System.getProperty(IEconomyService.NAME_KEY))