package ltd.icecold.paidcommand.utils;

import ltd.icecold.paidcommand.PaidCommand;
import org.bukkit.entity.Player;

/**
 * @author icecold
 */
public class VaultHandle {
    public boolean delMoney(Player player, int money)
    {
        if (!PaidCommand.getEconomy().hasAccount(player)) {
            return false;
        }
        if (PaidCommand.getEconomy().has(player, money)) {
            PaidCommand.getEconomy().withdrawPlayer(player, money);
        }
        return true;
    }
    public boolean hasMoney(Player player, int money)
    {
        if (player == null) {
            return false;
        }
        if (money <= 0.0D) {
            return true;
        }
        return PaidCommand.getEconomy().has(player, money);
    }
    public int getMoney(Player player)
    {
        return (int) PaidCommand.getEconomy().getBalance(player);

    }
}
