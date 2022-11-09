package xyz.cssxsh.mirai.economy;

import net.mamoe.mirai.event.EventHandler;
import net.mamoe.mirai.event.SimpleListenerHost;
import net.mamoe.mirai.event.events.BotEvent;
import net.mamoe.mirai.event.events.GroupEvent;
import org.junit.jupiter.api.Test;
import xyz.cssxsh.mirai.economy.event.EconomyBalanceChangeEvent;
import xyz.cssxsh.mirai.economy.event.EconomyCurrencyRegisteredEvent;
import xyz.cssxsh.mirai.economy.event.EconomyServiceInitEvent;
import xyz.cssxsh.mirai.economy.service.EconomyAccount;
import xyz.cssxsh.mirai.economy.service.EconomyContext;

public class EconomyUtilsTest extends SimpleListenerHost {

    @Test
    public void register() {
        EconomyService.INSTANCE.register(EconomyServiceTestCurrency.INSTATE, false);
    }

    @Test
    public void global() {
        EconomyAccount test1 = EconomyService.INSTANCE.account( "test1",  "test");
        try (EconomyContext context = EconomyUtils.getGlobalEconomy()) {
            Double v2 = context.get(test1, EconomyServiceTestCurrency.INSTATE);
            context.set(test1, EconomyServiceTestCurrency.INSTATE, 1000.0);
            context.plusAssign(test1, EconomyServiceTestCurrency.INSTATE, 100.0);
            context.minusAssign(test1, EconomyServiceTestCurrency.INSTATE, 10.0);
            context.timesAssign(test1, EconomyServiceTestCurrency.INSTATE, 10.0);
            context.divAssign(test1, EconomyServiceTestCurrency.INSTATE, 5.0);

        } catch (Exception e) {
            e.getStackTrace();
        }

    }

    @EventHandler
    public void handle(EconomyServiceInitEvent event) {
        event.getService();
    }

    @EventHandler
    public void handle(EconomyCurrencyRegisteredEvent event) {
        event.getService();
        event.getCurrency();
    }

    @EventHandler
    public void handle(EconomyBalanceChangeEvent event) {
        event.getAccount();
        event.getService();
        event.getCurrency();
        event.getChange();
        event.getMode();
    }

    @EventHandler
    public void handle(BotEvent event) {
        try (EconomyContext context = EconomyUtils.getEconomy(event.getBot())) {
            context.getId();
            // ...
        } catch (Exception e) {
            e.getStackTrace();
        }
    }

    @EventHandler
    public void handle(GroupEvent event) {
        try (EconomyContext context = EconomyUtils.getEconomy(event.getBot())) {
            context.getId();
            // ...
        } catch (Exception e) {
            e.getStackTrace();
        }
    }
}
