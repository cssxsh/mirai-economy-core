import io.github.skynet1748.mirai.economy.IEconomyContextGroup;
import io.github.skynet1748.mirai.economy.IEconomyService;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JavaEconomyContextGroup implements IEconomyContextGroup {
    final JavaEconomyService service;
    final long groupId;
    final Map<Long, Double> data;

    public JavaEconomyContextGroup(JavaEconomyService service, long groupId) {
        this.service = service;
        this.groupId = groupId;
        this.data = service.groupData.getOrDefault(groupId, new HashMap<>());
    }

    @NotNull
    @Override
    public String getName() {
        return "群" + groupId;
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
        return data.containsKey(userId);
    }

    @NotNull
    @Override
    public List<Long> listAccounts(int count) {
        List<Long> result = new ArrayList<>();
        int i = 0;
        for (long u : data.keySet()) {
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
        return data.getOrDefault(userId, 0.0d);
    }

    @Override
    public void set(long userId, double money) {
        data.put(userId, money);
        service.groupData.put(groupId, data);
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

    @Override
    public long getGroupId() {
        return groupId;
    }
}
