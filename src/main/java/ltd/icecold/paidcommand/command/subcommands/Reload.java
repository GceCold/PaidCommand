package ltd.icecold.paidcommand.command.subcommands;

import ltd.icecold.paidcommand.Config;
import ltd.icecold.paidcommand.PaidCommand;
import ltd.icecold.paidcommand.command.BaseCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Reload extends BaseCommand {

    public Reload() {
        super("reload");
    }

    @Override
    public void onCommand(CommandSender sender, String command, ArrayList<String> args) {
        if (sender.isOp()){
            PaidCommand.getInstance().reloadConfig();
            Config.init();
            sender.sendMessage("[PaidCommand] > §b已重载配置文件");
        }else {
            Helper.sendMainHelp(sender);
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Object subCmd, ArrayList<String> args) {
        return null;
    }
}
