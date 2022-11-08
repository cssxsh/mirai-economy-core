package xyz.cssxsh.mirai.economy.console.jpa

import jakarta.persistence.*
import net.mamoe.mirai.utils.*
import org.hibernate.*
import xyz.cssxsh.hibernate.*
import xyz.cssxsh.mirai.economy.console.entity.*
import xyz.cssxsh.mirai.economy.event.*
import xyz.cssxsh.mirai.economy.service.*
import java.io.Flushable

@PublishedApi
internal abstract class JpaSessionAction : Flushable, AutoCloseable, EconomyAction {
    abstract val logger: MiraiLogger
    abstract val session: Session
    abstract val id: String
    abstract val service: JpaEconomyService

    override fun flush() {
        if (session.isJoinedToTransaction) session.flush()
    }

    override fun close() {
        session.close()
    }

    @Synchronized
    open fun <T> transaction(block: Session.() -> T) {
        val transaction = session.beginTransaction()
        try {
            block.invoke(session)
            transaction.commit()
        } catch (exception: RollbackException) {
            try {
                transaction.rollback()
            } catch (cause: PersistenceException) {
                logger.debug({ "回滚异常" }, cause)
            }
        }
    }

    override fun EconomyAccount.get(currency: EconomyCurrency): Double {
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.name,
            context = id
        )
        val record = session[EconomyGlobalRecord::class.java, index]
        return record?.balance ?: 0.0
    }

    override fun EconomyAccount.balance(): Map<EconomyCurrency, Double> {
        val records = session.withCriteria<EconomyGlobalRecord> { criteria ->
            val record = criteria.from<EconomyGlobalRecord>()
            criteria.select(record)
                .where(
                    equal(record.get<EconomyAccountIndex>("index").get<String>("uuid"), uuid)
                )
        }.list()

        return buildMap {
            for (record in records) {
                val currency = service.basket[record.index.currency] ?: continue
                put(currency, record.balance)
            }
        }
    }

    override fun EconomyAccount.set(currency: EconomyCurrency, quantity: Double) {
        val current = get(currency)
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = current,
            change = quantity,
            mode = EconomyBalanceChangeMode.SET
        )
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.name,
            context = id
        )
        val record = EconomyGlobalRecord(
            index = index,
            balance = quantity
        )
        service.broadcast(event) {
            transaction {
                session.merge(record)
            }
        }
    }

    override fun EconomyAccount.plusAssign(currency: EconomyCurrency, quantity: Double) {
        val current = get(currency)
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = current,
            change = quantity,
            mode = EconomyBalanceChangeMode.PLUS
        )
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.name,
            context = id
        )
        val record = EconomyGlobalRecord(
            index = index,
            balance = current + quantity
        )
        service.broadcast(event) {
            transaction {
                session.merge(record)
            }
        }
    }

    override fun EconomyAccount.minusAssign(currency: EconomyCurrency, quantity: Double) {
        val current = get(currency)
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = current,
            change = quantity,
            mode = EconomyBalanceChangeMode.MINUS
        )
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.name,
            context = id
        )
        val record = EconomyGlobalRecord(
            index = index,
            balance = current - quantity
        )
        service.broadcast(event) {
            transaction {
                session.merge(record)
            }
        }
    }

    override fun EconomyAccount.timesAssign(currency: EconomyCurrency, quantity: Double) {
        val current = get(currency)
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = current,
            change = quantity,
            mode = EconomyBalanceChangeMode.TIMES
        )
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.name,
            context = id
        )
        val record = EconomyGlobalRecord(
            index = index,
            balance = current * quantity
        )
        service.broadcast(event) {
            transaction {
                session.merge(record)
            }
        }
    }

    override fun EconomyAccount.divAssign(currency: EconomyCurrency, quantity: Double) {
        val current = get(currency)
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = current,
            change = quantity,
            mode = EconomyBalanceChangeMode.DIV
        )
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.name,
            context = id
        )
        val record = EconomyGlobalRecord(
            index = index,
            balance = current / quantity
        )
        service.broadcast(event) {
            transaction {
                session.merge(record)
            }
        }
    }
}