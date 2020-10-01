package ltd.icecold.paidcommand;

import ltd.icecold.paidcommand.command.CommandHandler;
import net.milkbowl.vault.economy.Economy;
import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;


public final class PaidCommand extends JavaPlugin {
    private static Economy econ = null;
    private static PaidCommand instance;
    public static boolean isPlayerPoint = false;
    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        setupEconomy();
        if (getServer().getPluginManager().getPlugin("PlayerPoints") != null){
            isPlayerPoint = hookPlayerPoints();
        }
        Config.init();
        Bukkit.getPluginCommand("pc").setExecutor(CommandHandler.instance);
        Bukkit.getPluginManager().registerEvents(new CommandListener(),this);
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommand§7] §l§b> 付费指令插件已加载");
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommand§7] §l§b> 作者：冰冷  QQ:736131306");
    }

    private void setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return;
        }
        econ = rsp.getProvider();
    }

    private boolean hookPlayerPoints() {
        PlayerPoints playerPoints = (PlayerPoints) getServer().getPluginManager().getPlugin("PlayerPoints");;
        return playerPoints != null;
    }

    public static PaidCommand getInstance(){
        return instance;
    }
    public static Economy getEconomy() {
        return econ;
    }
}
