import io.github.skynet1748.mirai.economy.IEconomyContext;
import io.github.skynet1748.mirai.economy.IEconomyService;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class JavaEconomyService implements IEconomyService {
    protected final Map<Long, Double> globalData = new HashMap<>();
    protected final Map<Long, Map<Long, Double>> groupData = new HashMap<>();

    public JavaEconomyService() {
        // 读取数据
    }

    public void save() {
        // 保存数据
    }

    @NotNull
    @Override
    public IEconomyContext getGlobalContext() {
        return new JavaEconomyContextGlobal(this);
    }

    @NotNull
    @Override
    public IEconomyContext getGroupContext(long groupId) {
        return new JavaEconomyContextGroup(this, groupId);
    }
}
