package ltd.icecold.paidcommand.command.subcommands;

import ltd.icecold.paidcommand.CommandListener;
import ltd.icecold.paidcommand.Config;
import ltd.icecold.paidcommand.PaidBean;
import ltd.icecold.paidcommand.command.BaseCommand;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class CommandRun extends BaseCommand {


    public CommandRun() {
        super("runcommand");
    }

    @Override
    public void onCommand(CommandSender sender, String command, ArrayList<String> args) {
        if (args.size() == 0){
            sender.sendMessage("[PaidCommand] > §c格式错误 /pc runcommand [Command]");
            return;
        }
        if (!(sender instanceof Player)){
            sender.sendMessage("[PaidCommand] > §c请在游戏内执行该命令");
            return;
        }
        Player player = (Player)sender;
        StringBuilder runCommand = new StringBuilder();
        for (String arg : args) {
            runCommand.append(arg).append(" ");
        }

        List<String> commandList = Config.getCommandList();
        for (String paidCommand:commandList){
            if (runCommand.toString().trim().startsWith(paidCommand)){
                PaidBean paidBean = Config.getPaid(paidCommand);
                if (paidBean == null){
                    return;
                }
                if (paidBean.getCost() == 0 || paidBean.getIgnore().contains(player.getName()) || player.hasPermission("pc.free")){
                    CommandListener.runCommand(paidBean,player,command);
                    return;
                }
                if (CommandListener.paid(player,paidBean.getCost(),paidBean.getType())){
                    CommandListener.runCommand(paidBean,player,command);
                }
            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Object subCmd, ArrayList<String> args) {
        return null;
    }
}
