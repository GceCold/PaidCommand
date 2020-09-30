package ltd.icecold.paidcommand.command.subcommands;

import ltd.icecold.paidcommand.Config;
import ltd.icecold.paidcommand.PaidBean;
import ltd.icecold.paidcommand.command.BaseCommand;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Add extends BaseCommand {

    public Add() {
        super("add");
    }

    @Override
    public void onCommand(CommandSender sender, String command, ArrayList<String> args) {
        if (!sender.hasPermission("pc.admin")){
            sender.sendMessage("[PaidCommand] > §c您没有权限");
            return;
        }
        if ("paid".equals(args.get(0))){
            if (args.size() < 4){
                Helper.sendMainHelp(sender);
                return;
            }
            PaidBean paidBean = new PaidBean();
            paidBean.setCost(Integer.parseInt(args.get(1)));
            String type = args.get(2);
            if (!"point".equals(type) && !"coin".equals(type)){
                sender.sendMessage("[PaidCommand] > §c支付类型错误，请指定coin或point");
                return;
            }
            paidBean.setType(type);
            paidBean.setIgnore(new ArrayList<String>());
            paidBean.setIsOp(false);
            StringBuffer commandString = new StringBuffer();
            for (int i = 3;i < args.size();i++){
                commandString.append(args.get(i)).append(" ");
            }
            paidBean.setName(commandString.toString().trim());
            if (Config.getCommandList().contains(commandString.toString().trim())){
                sender.sendMessage("[PaidCommand] > §c付费指令已存在");
                return;
            }
            Config.addPaid(paidBean);
            sender.sendMessage("[PaidCommand] > §b添加成功");
        }

        if ("player".equals(args.get(0))){
            if (args.size() < 3){
                Helper.sendMainHelp(sender);
                return;
            }
            StringBuffer commandString = new StringBuffer();
            for (int i = 2;i < args.size();i++){
                commandString.append(args.get(i)).append(" ");
            }
            if (!Config.getCommandList().contains(commandString.toString().trim())){
                sender.sendMessage("[PaidCommand] > §c付费指令不存在");
                return;
            }
            PaidBean paid = Config.getPaid(commandString.toString().trim());
            List<String> ignore = paid.getIgnore();
            if (ignore.contains(args.get(1))){
                sender.sendMessage("[PaidCommand] > §c玩家已存在");
                return;
            }
            ignore.add(args.get(1));
            paid.setIgnore(ignore);
            sender.sendMessage("[PaidCommand] > §b添加成功");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Object subCmd, ArrayList<String> args) {
        return null;
    }
}
