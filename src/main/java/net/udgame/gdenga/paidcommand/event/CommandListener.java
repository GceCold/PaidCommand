package net.udgame.gdenga.paidcommand.event;

import net.udgame.gdenga.paidcommand.PaidCommand;
import net.udgame.gdenga.paidcommand.setting.Language;
import net.udgame.gdenga.paidcommand.setting.Paid;
import net.udgame.gdenga.paidcommand.util.VaultHandle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.ArrayList;
import java.util.List;

public class CommandListener implements Listener {
    @EventHandler
    public void commandListen(PlayerCommandPreprocessEvent event){
        if (!PaidCommand.getInstance().getConfig().getBoolean("PaidCommand.Paid_Enable")){
            if (event.isCancelled()){
                event.setCancelled(false);
                return;
            }
            return;
        }
        Player player = event.getPlayer();
        String command = event.getMessage();
        if (player.hasPermission("pc.free")){
            if (event.isCancelled()){
                event.setCancelled(false);
                return;
            }
            return;
        }
        //-----------------------------------------------------------------------
        command = command.replace("/","");
        command = command.trim();
        int commandNum = command.indexOf(" ");
        String[] commands = command.split(" ");
        int cost = 0;
        List<String> ignorePlayer = new ArrayList<>();
        List<String> commandList =  Paid.getPaidCommandList();
        g1:for (int i=0; i<commandList.size();i++){
            String paidCommand = commandList.get(i);
            if (!(paidCommand.contains("-")&&command.contains(" "))){
                if (paidCommand.equals(command)){
                    cost = Paid.getCommandCost(commandList.get(i));
                    ignorePlayer = Paid.getIgnorePlayer(commandList.get(i));
                    break g1;
                }
            }
            List<String> nn = new ArrayList<>();
            String[] paidCommands = paidCommand.split("-");
            if (!paidCommand.contains("-")){
                paidCommands =new String[1];
                nn.add(paidCommand);
                nn.toArray(paidCommands);
            }
            int n = paidCommands.length;
            int n1 = 0;
            for (int i1 = 0;i1 < n; i1++){
                for (int i2 = 0;i2 < commands.length; i2++){
                    if (paidCommands[i1].equals(commands[i2])){
                        n1++;
                    }
                }
            }
            if (n1 == n){
                cost = Paid.getCommandCost(commandList.get(i));
                ignorePlayer = Paid.getIgnorePlayer(commandList.get(i));
                break g1;
            }
        }
        if (cost == 0 || ignorePlayer.contains(player.getName())){
            event.setCancelled(false);
            return;
        }
        VaultHandle vaultHandle = new VaultHandle();
        int hasMoney = vaultHandle.getMoney(player.getName());
        String message = "";
        if (!vaultHandle.hasMoney(player.getName(),cost)){
            message = Language.getNotEnough().replaceAll("\\$\\{MONEY\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(vaultHandle.getMoney(player.getName())));
            player.sendMessage(message);
            event.setCancelled(true);
            return;
        }
        vaultHandle.delMoney(player.getName(),cost);
        message = Language.getSuccess().replaceAll("\\$\\{MONEY\\}",String.valueOf(cost)).replaceAll("\\$\\{NOW\\}",String.valueOf(vaultHandle.getMoney(player.getName())));
        player.sendMessage(message);
        event.setCancelled(false);
        return;
    }
}
