package ltd.icecold.paidcommand.utils;

import org.black_ixx.playerpoints.PlayerPoints;
import org.bukkit.Bukkit;


public class PlayerPointsUtil {
    public boolean payPoints(String playerName, int points) {
        if ((playerName == null) || (playerName.length() <= 0)) {
            return false;
        }
        if (points <= 0.0D) {
            return true;
        }
        PlayerPoints playerPoints = (PlayerPoints) Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
        return playerPoints.getAPI().take(Bukkit.getPlayer(playerName).getUniqueId(),points);
    }
    public int getMoney(String playerName) {
        PlayerPoints playerPoints = (PlayerPoints) Bukkit.getServer().getPluginManager().getPlugin("PlayerPoints");
        return playerPoints.getAPI().look(Bukkit.getPlayer(playerName).getUniqueId());
    }
}
