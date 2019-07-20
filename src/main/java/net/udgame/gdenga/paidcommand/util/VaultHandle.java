package net.udgame.gdenga.paidcommand.util;
import net.udgame.gdenga.paidcommand.PaidCommand;
/**
 * @author: gdenga
 * @date: 2019/7/19 11:42
 * @content:
 */
public class VaultHandle {
    public boolean delMoney(String name, int money)
    {
        if (!PaidCommand.getEconomy().hasAccount(name)) {
            return false;
        }
        if (PaidCommand.getEconomy().has(name, money)) {
            PaidCommand.getEconomy().withdrawPlayer(name, money);
        }
        return true;
    }
    public boolean hasMoney(String name, int money)
    {
        if ((name == null) || (name.length() <= 0)) {
            return false;
        }
        if (money <= 0.0D) {
            return true;
        }
        return PaidCommand.getEconomy().has(name, money);
    }
    public int getMoney(String name)
    {
        return (int) PaidCommand.getEconomy().getBalance(name);

    }
}
