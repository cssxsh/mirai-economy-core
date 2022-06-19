package io.github.skynet1748.mirai.economy.service


/**
 * 经济服务名注解，用于标记一个服务的名称, 用于在服务初始化时匹配服务
 * @param name 服务的名称
 * @see IEconomyService.create
 */
@Target(AnnotationTarget.CLASS)
public annotation class EconomyServiceName(
    val name: String
)
