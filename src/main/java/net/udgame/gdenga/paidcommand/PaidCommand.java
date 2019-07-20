package net.udgame.gdenga.paidcommand;

import net.milkbowl.vault.economy.Economy;
import net.udgame.gdenga.paidcommand.command.CommandHandler;
import net.udgame.gdenga.paidcommand.event.CommandListener;
import net.udgame.gdenga.paidcommand.setting.Paid;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class PaidCommand extends JavaPlugin {
    private static Economy econ = null;
    private static PaidCommand instance;
    @Override
    public void onLoad(){
        File paid = new File(getDataFolder() + "//paid.yml");
        File language = new File(getDataFolder() + "//language.yml");
        if (!paid.exists()){
            saveResource("paid.yml",false);
        }
        if (!language.exists()){
            saveResource("language.yml",false);
        }
        saveDefaultConfig();
    }

    @Override
    public void onEnable() {
        setupEconomy();
        instance = this;
        if (!Bukkit.getPluginManager().isPluginEnabled("Vault")){
            Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommand§7] §l§e> 插件需要Vault前置");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
        Bukkit.getPluginCommand("pc").setExecutor(new CommandHandler());
        Bukkit.getPluginManager().registerEvents(new CommandListener(),this);
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommand§7] §l§b> 付费指令插件已加载");
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommand§7] §l§b> 作者：冰冷  QQ:736131306");
    }

    @Override
    public void onDisable() {
        Bukkit.getServer().getConsoleSender().sendMessage("§7[PaidCommand§7] §l§e> 付费指令插件已卸载，感谢使用");
    }
    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
    public static PaidCommand getInstance(){ return instance; }
    public static Economy getEconomy() {
        return econ;
    }
}
