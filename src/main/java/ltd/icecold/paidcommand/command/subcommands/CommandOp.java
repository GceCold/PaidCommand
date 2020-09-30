package ltd.icecold.paidcommand.command.subcommands;

import ltd.icecold.paidcommand.Config;
import ltd.icecold.paidcommand.PaidBean;
import ltd.icecold.paidcommand.command.BaseCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandOp extends BaseCommand {

    public CommandOp() {
        super("op");
    }

    @Override
    public void onCommand(CommandSender sender, String command, ArrayList<String> args) {
        if (!sender.hasPermission("pc.admin")){
            sender.sendMessage("[PaidCommand] > §c您没有权限");
            return;
        }
        if (args.size() == 0){
            sender.sendMessage("[PaidCommand] > §c格式错误 /pc op [Command]");
            return;
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (String arg : args) {
            stringBuilder.append(arg).append(" ");
        }
        PaidBean paid = Config.getPaid(stringBuilder.toString().trim());
        if (paid == null){
            sender.sendMessage("[PaidCommand] > §c未找到指令");
            return;
        }
        paid.setIsOp(true);
        Config.modifyPaid(paid);
        sender.sendMessage("[PaidCommand] > §b成功设置付费指令/"+stringBuilder.toString().trim());
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Object subCmd, ArrayList<String> args) {
        return null;
    }
}
