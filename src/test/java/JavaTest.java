import io.github.skynet1748.mirai.economy.EconomyApi;
import io.github.skynet1748.mirai.economy.IEconomyContext;
import io.github.skynet1748.mirai.economy.IEconomyService;

public class JavaTest {
    public static void main() {
        IEconomyService economy = EconomyApi.get("java");
        if (economy == null) {
            System.out.println("没有可用的经济服务");
            return;
        }
        IEconomyContext context = economy.getGlobalContext();
        context.set(114514L, 1919810);
        System.out.println("完毕，114514的金钱为" + context.get(114514L));
    }
}
