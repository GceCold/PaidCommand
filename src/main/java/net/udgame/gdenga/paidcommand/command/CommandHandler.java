package net.udgame.gdenga.paidcommand.command;

import net.udgame.gdenga.paidcommand.setting.Paid;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

/**
 * @author: gdenga
 * @date: 2019/7/19 7:57
 * @content:
 */
public class CommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("pc.admin")){
            sender.sendMessage("[PaidCommand] > §c您没有权限");
            return true;
        }
        if (args.length == 0) {
            sendHelp(sender);
            return true;
        }
        //添加
        if ("add".equals(args[0])){
            if (args.length < 2){
                sendHelp(sender);
                return true;
            }
            //为指令添加免费玩家
            if ("player".equals(args[1])){
                if (args.length <= 3){
                    sender.sendMessage("[PaidCommand] > §c格式错误 /xp add player [PlayerName] [Command]");
                    return true;
                }
                String playerName = args[2];

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }

                if (!Paid.getPaidCommandList().contains(stringBuilder.toString())){
                    sender.sendMessage("[PaidCommand] > §c付费指令不存在");
                    return true;
                }
                Paid.addCommandIgnorePlayer(stringBuilder.toString(),playerName);
                sender.sendMessage("[PaidCommand] > §b添加成功");
                return true;
            }


            //添加付费指令
            if ("paid".equals(args[1])) {
                if (args.length <= 3) {
                    sender.sendMessage("[PaidCommand] > §c格式错误 /xp add paid [Cost] [Command]");
                    return true;
                }
                int cost = Integer.valueOf(args[2]);
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                Paid.addPaidCommand(stringBuilder.toString(), cost);
                sender.sendMessage("[PaidCommand] > §b添加成功");
                return true;
            }
            sendHelp(sender);
            return true;
        }

        if ("list".equals(args[0])){
            if (args.length < 2){
                sendHelp(sender);
                return true;
            }

            //列出所有付费指令
            if ("command".equals(args[1])){
                sender.sendMessage("[PaidCommand] > §5§a以下为付费指令以及所需费用");
                sender.sendMessage("[PaidCommand] > §9---------------------------------------------");
                List<String> commandList = Paid.getPaidCommandList();
                for (String command : commandList){
                    sender.sendMessage("[PaidCommand] > §e"+command.replaceAll("-"," ")+"    §b售价："+Paid.getCommandCost(command));
                }
                sender.sendMessage("[PaidCommand] > §9---------------------------------------------");
                return true;
            }

            //查询某个指令的免费玩家列表
            if ("player".equals(args[1])){
                if (args.length < 3) {
                    sender.sendMessage("[PaidCommand] > §c格式错误 /xp list player [Command]");
                    return true;
                }
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                if (!Paid.getPaidCommandList().contains(stringBuilder.toString())){
                    sender.sendMessage("[PaidCommand] > §c付费指令不存在");
                    return true;
                }
                List<String> playerList = Paid.getIgnorePlayer(stringBuilder.toString());
                sender.sendMessage("[PaidCommand] > §5§a以下玩家可免费使用付费指令 §e"+stringBuilder.toString().replaceAll("-"," "));
                sender.sendMessage("[PaidCommand] > §9---------------------------------------------");
                for (String playerName : playerList){
                    sender.sendMessage("[PaidCommand] > §e    "+playerName);
                }
                sender.sendMessage("[PaidCommand] > §9---------------------------------------------");
                return true;
            }
            sendHelp(sender);
            return true;

        }

        if ("del".equals(args[0])){
            if (args.length < 2){
                sendHelp(sender);
                return true;
            }
            //删除付费指令
            // /pc del paid [Command]
            if ("paid".equals(args[1])){

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 2; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                Paid.delPaidCommand(stringBuilder.toString());
                sender.sendMessage("[PaidCommand] > §b成功删除付费指令/"+stringBuilder.toString().replaceAll("-"," "));
                return true;
            }
            // /pc del player [Player] [Command]
            if ("player".equals(args[1])){
                if (args.length < 3){
                    sender.sendMessage("[PaidCommand] > §c格式错误 /xp add player [PlayerName] [Command]");
                    return true;
                }
                String playerName = args[2];
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 3; i < args.length; i++) {
                    if (i == args.length - 1) {
                        stringBuilder.append(args[i]);
                    } else {
                        stringBuilder.append(args[i] + "-");
                    }
                }
                if (!Paid.getPaidCommandList().contains(stringBuilder.toString())){
                    sender.sendMessage("[PaidCommand] > §c付费指令不存在");
                    return true;
                }
                if (!Paid.getIgnorePlayer(stringBuilder.toString()).contains(playerName)){
                    sender.sendMessage("[PaidCommand] > §c付费指令忽略玩家不存在");
                    return true;
                }
                Paid.delPaidCommandIgnorePlayer(stringBuilder.toString(),playerName);
                sender.sendMessage("[PaidCommand] > §b成功删除免费玩家"+playerName);
                return true;
            }
            sendHelp(sender);
            return true;
        }

        sendHelp(sender);
        return true;
    }

    public static void sendHelp(CommandSender sender){
        sender.sendMessage("[PaidCommand] >§a§b---------------------------------------------");
        sender.sendMessage("[PaidCommand] >§b§a/pc add paid [Cost] [Command]             添加新的付费指令");
        sender.sendMessage("[PaidCommand] >§b§a/pc add player [PlayerName] [Command]     为指令添加免费玩家");
        sender.sendMessage("[PaidCommand] >§b§a/pc list command                    列出所有付费指令");
        sender.sendMessage("[PaidCommand] >§b§a/pc list player [Command]           查询某个指令的免费玩家列表");
        sender.sendMessage("[PaidCommand] >§b§a/pc del paid [Command]              删除付费指令");
        sender.sendMessage("[PaidCommand] >§b§a/pc del player [Player] [Command]   删除付费指令的免费玩家");
        sender.sendMessage("[PaidCommand] >§a§b--------------------------------------------");
    }
}
