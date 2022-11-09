package xyz.cssxsh.mirai.economy;

import org.jetbrains.annotations.NotNull;
import xyz.cssxsh.mirai.economy.service.EconomyCurrency;

public class EconomyServiceTestCurrency implements EconomyCurrency {

    @NotNull
    @Override
    public String getId() {
        return "EconomyUtilsTest";
    }

    @NotNull
    @Override
    public String getName() {
        return "EconomyUtilsTest";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "EconomyUtilsTest";
    }

    public static EconomyServiceTestCurrency INSTATE = new EconomyServiceTestCurrency();
}