package net.udgame.gdenga.paidcommand.setting;

import net.udgame.gdenga.paidcommand.PaidCommand;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @author: gdenga
 * @date: 2019/7/19 9:57
 * @content: paid内容读取
 */
public class Paid {
    public static FileConfiguration openPaidYml() {
        File paidFile = new File(PaidCommand.getInstance().getDataFolder() + "//paid.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(paidFile);
        return filec;
    }
    public static List<String> getPaidCommandList(){
        Set<String> key = openPaidYml().getKeys(true);
        key.remove("Paid");
        key.remove("Paid.Command");
        List<String> command = new ArrayList<>();
        for (String str : key) {
            str = str.replace("Paid.Command.","");
            if (!str.contains(".")){
                command.add(str);
            }
        }
        return command;
    }

    public static Integer getCommandCost(String commandName){
        String path = "Paid.Command."+commandName+".cost";
        return openPaidYml().getInt(path);
    }

    public static List<String> getIgnorePlayer(String commandName){
        String path = "Paid.Command."+commandName+".ignore_player";
        if (openPaidYml().getStringList(path) == null){
            return new ArrayList<String>();
        }
        return openPaidYml().getStringList(path);
    }

    public static void addPaidCommand(String commandName,Integer cost){
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(new File(PaidCommand.getInstance().getDataFolder() + "//paid.yml"));
        ConfigurationSection commandSection = yamlConfiguration.getConfigurationSection("Paid.Command");
        commandSection.set(commandName+".cost",cost);
        commandSection.set(commandName+".ignore_player",new ArrayList<String>());
        try {
            yamlConfiguration.save(PaidCommand.getInstance().getDataFolder() + "//paid.yml");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static void addCommandIgnorePlayer(String commandName,String playerName){
        File paidFile = new File(PaidCommand.getInstance().getDataFolder() + "//paid.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(paidFile);
        List<String> ignorePlayer =getIgnorePlayer(commandName);
        ignorePlayer.add(playerName);
        filec.set("Paid.Command."+commandName+".ignore_player",ignorePlayer);
        try {
            filec.save(paidFile);
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public static Boolean delPaidCommand(String command){
        File paidFile = new File(PaidCommand.getInstance().getDataFolder() + "//paid.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(paidFile);
        if (!getPaidCommandList().contains(command)){
            return false;
        }
        filec.set("Paid.Command."+command,null);
        try {
            filec.save(paidFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

    public static Boolean delPaidCommandIgnorePlayer(String command,String player){
        File paidFile = new File(PaidCommand.getInstance().getDataFolder() + "//paid.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(paidFile);
        if (!getPaidCommandList().contains(command)){
            return false;
        }
        List<String> playerList = filec.getStringList("Paid.Command."+command+".ignore_player");
        playerList.remove(player);
        filec.set("Paid.Command."+command+".ignore_player",playerList);
        try {
            filec.save(paidFile);
        }catch (IOException e){
            e.printStackTrace();
        }
        return true;
    }

}
