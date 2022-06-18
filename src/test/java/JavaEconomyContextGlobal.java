import io.github.skynet1748.mirai.economy.IEconomyContextGlobal;
import io.github.skynet1748.mirai.economy.IEconomyService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class JavaEconomyContextGlobal implements IEconomyContextGlobal {
    final JavaEconomyService service;

    public JavaEconomyContextGlobal(JavaEconomyService service) {
        this.service = service;
    }

    @NotNull
    @Override
    public String getName() {
        return "全局";
    }

    @NotNull
    @Override
    public IEconomyService getService() {
        return service;
    }

    @Override
    public void createAccount(long userId, double money) {
        set(userId, money);
    }

    @Override
    public boolean hasAccount(long userId) {
        return service.globalData.containsKey(userId);
    }

    @NotNull
    @Override
    public List<Long> listAccounts(int count) {
        List<Long> result = new ArrayList<>();
        int i = 0;
        for (long u : service.globalData.keySet()) {
            result.add(u);
            if (count > 0) {
                i++;
                if (i >= count) break;
            }
        }
        return result;
    }

    @Override
    public boolean has(long userId, double money) {
        return get(userId) >= money;
    }

    @Override
    public double get(long userId) {
        return service.globalData.getOrDefault(userId, 0.0d);
    }

    @Override
    public void set(long userId, double money) {
        service.globalData.put(userId, money);
        service.save();
    }

    @Override
    public double increase(long userId, double money) {
        double m = get(userId) + money;
        set(userId, m);
        return m;
    }

    @Override
    public double decrease(long userId, double money) {
        double m = get(userId) - money;
        set(userId, m);
        return m;
    }
}
