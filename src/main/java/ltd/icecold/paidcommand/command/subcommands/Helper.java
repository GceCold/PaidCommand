package ltd.icecold.paidcommand.command.subcommands;

import org.bukkit.command.CommandSender;

public class Helper {
    public static void sendMainHelp(CommandSender sender){
        sender.sendMessage("[PaidCommand] >§a§b---------------------------------------------");
        sender.sendMessage("[PaidCommand] >§b§a/pc add paid [Cost] [coin/point] [Command] 添加新的付费指令");
        sender.sendMessage("[PaidCommand] >§b§a/pc add player [PlayerName] [Command] 为指令添加免费玩家");
        sender.sendMessage("[PaidCommand] >§b§a/pc op [Command] 使指令以op权限执行");
        sender.sendMessage("[PaidCommand] >§b§a/pc runcommand [Command] 执行某付费指令");
        sender.sendMessage("[PaidCommand] >§b§a/pc list command 列出所有付费指令");
        sender.sendMessage("[PaidCommand] >§b§a/pc list player [Command] 查询某个指令的免费玩家列表");
        sender.sendMessage("[PaidCommand] >§b§a/pc del paid [Command] 删除付费指令");
        sender.sendMessage("[PaidCommand] >§b§a/pc del player [Player] [Command] 删除付费指令的免费玩家");
        sender.sendMessage("[PaidCommand] >§a§b--------------------------------------------");
    }
}
