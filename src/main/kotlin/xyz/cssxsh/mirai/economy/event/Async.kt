@file:JvmName(name = "EconomyEventUtils")

package xyz.cssxsh.mirai.economy.event

import kotlinx.coroutines.*
import net.mamoe.mirai.event.*
import java.util.concurrent.*

/**
 * 将 Economy 操作 作为 Mirai Event 发送出去 然后等待回执
 */
public fun <E: EconomyEvent> CoroutineScope.broadcast(event: E, block: E.() -> Unit) {
    val future = CompletableFuture<Any>()
    launch {
        event.broadcast()
        if (event !is CancellableEvent || event.isCancelled.not()) {
            block.invoke(event)
        }
    }.invokeOnCompletion { cause ->
        future.complete(cause ?: Unit)
    }
    val cause = future.get() as? Throwable
    if (event is CancellableEvent && event.isCancelled) {
        throw EconomyEventCancelledException(event, cause)
    }
}