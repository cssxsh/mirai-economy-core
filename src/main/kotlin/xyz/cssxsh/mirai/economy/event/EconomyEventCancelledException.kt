package xyz.cssxsh.mirai.economy.event

/**
 * 经济服务被终止异常
 * @param event 经济事件
 * @param cause 异常原因
 */
public class EconomyEventCancelledException(public val event: EconomyEvent, override val cause: Throwable?) :
    UnsupportedOperationException("Economy Event Cancelled")