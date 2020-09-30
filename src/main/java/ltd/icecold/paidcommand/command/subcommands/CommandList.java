package ltd.icecold.paidcommand.command.subcommands;

import ltd.icecold.paidcommand.Config;
import ltd.icecold.paidcommand.PaidBean;
import ltd.icecold.paidcommand.PaidCommand;
import ltd.icecold.paidcommand.command.BaseCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class CommandList extends BaseCommand {

    public CommandList() {
        super("list");
    }

    @Override
    public void onCommand(CommandSender sender, String command, ArrayList<String> args) {
        if (args.get(0).equals("command")){
            sender.sendMessage("[PaidCommand] > §5§a以下为付费指令以及所需费用");
            sender.sendMessage("[PaidCommand] > §9---------------------------------------------");
            List<PaidBean> paidList = Config.paidList;
            for (PaidBean paid : paidList){
                sender.sendMessage("[PaidCommand] > §e"+ paid.getName()+"   §b售价："+paid.getCost() + ("coin".equals(paid.getType()) ? PaidCommand.getInstance().getConfig().getString("paidcommand.coin") : PaidCommand.getInstance().getConfig().getString("paidcommand.point")));
            }
            sender.sendMessage("[PaidCommand] > §9---------------------------------------------");
            return;
        }

        if (args.get(0).equals("player")){
            if (args.size() < 2){
                sender.sendMessage("[PaidCommand] > §c格式错误 /pc list player [Command]");
                return ;
            }
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 1; i < args.size(); i++) {
                stringBuilder.append(args.get(i)).append(" ");
            }
            PaidBean paid = Config.getPaid(stringBuilder.toString().trim());

            sender.sendMessage("[PaidCommand] > §5§a以下玩家可免费使用付费指令 §e"+paid.getName());
            sender.sendMessage("[PaidCommand] > §9----------------------------------------------");
            for (String playerName : paid.getIgnore()){
                sender.sendMessage("[PaidCommand] > §e    "+playerName);
            }
            sender.sendMessage("[PaidCommand] > §9----------------------------------------------");
            return;
        }
    }

    @Override
    public java.util.List<String> onTabComplete(CommandSender sender, Object subCmd, ArrayList<String> args) {
        return null;
    }
}
