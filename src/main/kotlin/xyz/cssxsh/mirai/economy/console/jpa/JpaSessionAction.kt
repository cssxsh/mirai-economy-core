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

    // region EconomyContext

    abstract val logger: MiraiLogger
    abstract val session: Session
    protected abstract val context: String
    abstract val service: JpaEconomyService

    override fun flush() {
        if (session.isJoinedToTransaction) session.flush()
    }

    override fun close() {
        session.close()
    }

    // endregion

    open fun <T> transaction(block: (Session) -> T) {
        val transaction = session.beginTransaction()
        try {
            block.invoke(session)
            transaction.commit()
        } catch (exception: RollbackException) {
            logger.error({ "事务提交异常" }, exception)
            try {
                transaction.rollback()
            } catch (cause: PersistenceException) {
                logger.warning({ "回滚异常" }, cause)
            }
        }
    }

    override fun EconomyAccount.get(currency: EconomyCurrency): Double {
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.id,
            context = context
        )
        val record = session[EconomyBalanceRecord::class.java, index]
        return record?.balance ?: 0.0
    }

    override fun EconomyAccount.balance(): Map<EconomyCurrency, Double> {
        val records = session.withCriteria<EconomyBalanceRecord> { criteria ->
            val record = criteria.from<EconomyBalanceRecord>()
            val index = record.get<EconomyAccountIndex>("index")
            criteria.select(record)
                .where(
                    equal(index.get<String>("uuid"), uuid),
                    equal(index.get<String>("context"), context)
                )
        }.list()

        return buildMap {
            for (record in records) {
                val currency = service.basket[record.index.currency] ?: continue
                put(currency, record.balance)
            }
        }
    }

    override fun EconomyAccount.set(currency: EconomyCurrency, quantity: Double) = synchronized(currency) {
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.id,
            context = context
        )
        val record = session[EconomyBalanceRecord::class.java, index] ?: EconomyBalanceRecord(
            index = index,
            balance = 0.0
        )
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = record.balance,
            change = quantity,
            mode = EconomyBalanceChangeMode.SET
        )
        service.broadcast(event) {
            transaction { session ->
                session.merge(record.copy(balance = quantity))
            }
        }
    }

    override fun EconomyAccount.plusAssign(currency: EconomyCurrency, quantity: Double) = synchronized(currency) {
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.id,
            context = context
        )
        val record = session[EconomyBalanceRecord::class.java, index] ?: EconomyBalanceRecord(
            index = index,
            balance = 0.0
        )
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = record.balance,
            change = quantity,
            mode = EconomyBalanceChangeMode.PLUS
        )
        service.broadcast(event) {
            transaction { session ->
                session.merge(record.copy(balance = current + change))
            }
        }
    }

    override fun EconomyAccount.minusAssign(currency: EconomyCurrency, quantity: Double) = synchronized(currency) {
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.id,
            context = context
        )
        val record = session[EconomyBalanceRecord::class.java, index] ?: EconomyBalanceRecord(
            index = index,
            balance = 0.0
        )
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = record.balance,
            change = quantity,
            mode = EconomyBalanceChangeMode.MINUS
        )
        service.broadcast(event) {
            transaction { session ->
                session.merge(record.copy(balance = current - quantity))
            }
        }
    }

    override fun EconomyAccount.timesAssign(currency: EconomyCurrency, quantity: Double) = synchronized(currency) {
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.id,
            context = context
        )
        val record = session[EconomyBalanceRecord::class.java, index] ?: EconomyBalanceRecord(
            index = index,
            balance = 0.0
        )
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = record.balance,
            change = quantity,
            mode = EconomyBalanceChangeMode.TIMES
        )
        service.broadcast(event) {
            transaction { session ->
                session.merge(record.copy(balance = current * change))
            }
        }
    }

    override fun EconomyAccount.divAssign(currency: EconomyCurrency, quantity: Double) = synchronized(currency) {
        val index = EconomyAccountIndex(
            uuid = uuid,
            currency = currency.id,
            context = context
        )
        val record = session[EconomyBalanceRecord::class.java, index] ?: EconomyBalanceRecord(
            index = index,
            balance = 0.0
        )
        val event = EconomyBalanceChangeEvent(
            account = this,
            service = service,
            currency = currency,
            current = record.balance,
            change = quantity,
            mode = EconomyBalanceChangeMode.DIV
        )
        service.broadcast(event) {
            transaction { session ->
                session.merge(record.copy(balance = current / change))
            }
        }
    }

    override fun EconomyCurrency.balance(): Map<EconomyAccount, Double> {
        val records = session.withCriteria<EconomyBalanceRecord> { criteria ->
            val record = criteria.from<EconomyBalanceRecord>()
            val index = record.get<EconomyAccountIndex>("index")
            criteria.select(record)
                .where(
                    equal(index.get<String>("currency"), id),
                    equal(index.get<String>("context"), context)
                )
        }.list()

        return buildMap {
            for (record in records) {
                val account = try {
                    service.account(uuid = record.index.uuid)
                } catch (cause: NoSuchElementException) {
                    logger.warning({ "找不到用户 ${record.index.uuid}" }, cause)
                    continue
                }
                put(account, record.balance)
            }
        }
    }
}