package ltd.icecold.paidcommand.command.subcommands;

import ltd.icecold.paidcommand.Config;
import ltd.icecold.paidcommand.PaidBean;
import ltd.icecold.paidcommand.command.BaseCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Delete extends BaseCommand {

    public Delete() {
        super("del");
    }

    @Override
    public void onCommand(CommandSender sender, String command, ArrayList<String> args) {
        if (!sender.hasPermission("pc.admin")){
            sender.sendMessage("[PaidCommand] > §c您没有权限");
            return;
        }
        if ("paid".equals(args.get(0))){
            if (args.size() < 2){
                sender.sendMessage("[PaidCommand] > §c格式错误 /pc del paid [Command]");
                return;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < args.size(); i++) {
                stringBuilder.append(args.get(i)).append(" ");
            }
            if (Config.getPaid(stringBuilder.toString().trim()) == null){
                sender.sendMessage("[PaidCommand] > §c未找到指令");
                return;
            }
            Config.delPaid(Config.getPaid(stringBuilder.toString().trim()));
            sender.sendMessage("[PaidCommand] > §b成功删除付费指令/"+stringBuilder.toString().trim());
            return;
        }
        if ("player".equals(args.get(0))){
            if (args.size() < 3){
                sender.sendMessage("[PaidCommand] > §c格式错误 /pc del player [Player] [Command]");
                return;
            }
            StringBuilder commands = new StringBuilder();
            for (int i = 2; i < args.size(); i++) {
                commands.append(args.get(i)).append(" ");
            }
            PaidBean paid = Config.getPaid(commands.toString().trim());
            if (paid == null){
                sender.sendMessage("[PaidCommand] > §c未找到指令");
                return;
            }
            List<String> ignore = paid.getIgnore();
            if (!ignore.contains(args.get(1))){
                sender.sendMessage("[PaidCommand] > §c付费指令忽略玩家不存在");
                return;
            }
            ignore.remove(args.get(1));
            paid.setIgnore(ignore);
            Config.modifyPaid(paid);
            sender.sendMessage("[PaidCommand] > §b成功删除免费玩家 "+args.get(1));
            return;
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Object subCmd, ArrayList<String> args) {
        return null;
    }
}
