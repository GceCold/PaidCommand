package net.udgame.gdenga.paidcommand.setting;

import net.udgame.gdenga.paidcommand.PaidCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.List;

/**
 * @author: gdenga
 * @date: 2019/7/19 11:58
 * @content:
 */
public class Language {
    public static FileConfiguration openLanguageYml() {
        File regionFile = new File(PaidCommand.getInstance().getDataFolder() + "//language.yml");
        FileConfiguration filec = YamlConfiguration.loadConfiguration(regionFile);
        return filec;
    }
    public static String getNotEnough(){
        return openLanguageYml().getString("language.not_enough");
    }

    public static String getSuccess(){
        return openLanguageYml().getString("language.success");
    }
}
