package xyz.cssxsh.mirai.economy;

import org.jetbrains.annotations.NotNull;
import xyz.cssxsh.mirai.economy.service.EconomyCurrency;

public class EconomyServiceTestCoin implements EconomyCurrency {

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

    public static EconomyServiceTestCoin INSTATE = new EconomyServiceTestCoin();
}