package ltd.icecold.paidcommand;

import ltd.icecold.paidcommand.utils.PlayerPointsUtil;
import ltd.icecold.paidcommand.utils.VaultHandle;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.List;

public class CommandListener implements Listener {

    @EventHandler
    public void commandListen(PlayerCommandPreprocessEvent event){
        if (!PaidCommand.getInstance().getConfig().getBoolean("paidcommand.enable")){
            event.setCancelled(false);
            return;
        }
        Player player = event.getPlayer();
        String command = event.getMessage();
        command = command.replace("/","");
        command = command.trim();

        List<String> commandList = Config.getCommandList();
        for (String paidCommand:commandList){
            if (command.startsWith(paidCommand)){
                PaidBean paidBean = Config.getPaid(paidCommand);
                if (paidBean == null){
                    return;
                }
                if (paidBean.getCost() == 0 || paidBean.getIgnore().contains(player.getName()) || player.hasPermission("pc.free")){
                    event.setCancelled(true);
                    runCommand(paidBean,player,command);
                    return;
                }
                if (paid(player,paidBean.getCost(),paidBean.getType())){
                    event.setCancelled(true);
                    runCommand(paidBean,player,command);
                    return;
                }else{
                    event.setCancelled(true);
                    return;
                }
            }
        }


    }

    public static boolean paid(Player player,int cost,String type){
        if ("coin".equals(type)){
            VaultHandle vaultHandle = new VaultHandle();
            if (!vaultHandle.hasMoney(player,cost)){
                player.sendMessage(PaidCommand.getInstance().getConfig().getString("language.coin.notEnough").replaceAll("\\$\\{MONEY\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(vaultHandle.getMoney(player))));
                return false;
            }
            vaultHandle.delMoney(player,cost);
            player.sendMessage(PaidCommand.getInstance().getConfig().getString("language.coin.success").replaceAll("\\$\\{MONEY\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(vaultHandle.getMoney(player))));
            return true;
        }
        if ("point".equals(type) && PaidCommand.isPlayerPoint) {
            PlayerPointsUtil playerPointsUtil = new PlayerPointsUtil();
            if (playerPointsUtil.getMoney(player.getName()) < cost) {
                player.sendMessage(PaidCommand.getInstance().getConfig().getString("language.point.notEnough").replaceAll("\\$\\{MONEY\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(playerPointsUtil.getMoney(player.getName()))));
                return false;
            }
            playerPointsUtil.payPoints(player.getName(),cost);
            player.sendMessage(PaidCommand.getInstance().getConfig().getString("language.point.success").replaceAll("\\$\\{MONEY\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(playerPointsUtil.getMoney(player.getName()))));
            return true;
        }else {
            throw new NullPointerException("PlayerPoints未加载");
        }
    }

    public static void runCommand(PaidBean paid,Player player,String command){
        if (paid.isIsOp()) {
            boolean isOp = player.isOp();
            try {
                player.setOp(true);
                PaidCommand.getInstance().getServer().dispatchCommand(player, command.replace("%palyer%",player.getName()).replace("/",""));
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                player.setOp(isOp);
            }
        }else {
            PaidCommand.getInstance().getServer().dispatchCommand(player, command.replace("%palyer%",player.getName()).replace("/",""));
        }
    }
}
