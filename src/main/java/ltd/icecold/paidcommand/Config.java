package ltd.icecold.paidcommand;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ltd.icecold.paidcommand.utils.IOUtil;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author ice-cold
 */
public class Config {
    public static List<PaidBean> paidList;
    public static void init(){
        File file = new File(PaidCommand.getInstance().getDataFolder(), "paid.json");
        if (file.exists()){
            PaidCommand.getInstance().saveResource("paid.json",false);
        }
        String data = IOUtil.readFile(file);
        paidList = new Gson().fromJson(data, new TypeToken<List<PaidBean>>() {}.getType());
    }

    public static List<String> getCommandList(){
        List<String> commands = new ArrayList<>();
        for (PaidBean paid:paidList){
            commands.add(paid.getName());
         }
        return commands;
    }

    public static PaidBean getPaid(String command){
        for (PaidBean paid:paidList){
            if (paid.getName().equals(command)) {
                return paid;
            }
        }
        return null;
    }

    public static void modifyPaid(PaidBean newPaid){
        PaidBean oldPaid = null;
        for (PaidBean paid:paidList){
            if (paid.getName().equals(newPaid.getName())) {
                oldPaid = paid;
            }
        }
        if (oldPaid == null){
            throw new NullPointerException("未找到原数据");
        }
        paidList.remove(oldPaid);
        paidList.add(newPaid);
        String json = new Gson().toJson(paidList, new TypeToken<List<PaidBean>>() {}.getType());
        IOUtil.rewriteFile(new File(PaidCommand.getInstance().getDataFolder(), "paid.json"),json);
    }

    public static void addPaid(PaidBean newPaid){
        paidList.add(newPaid);
        String json = new Gson().toJson(paidList, new TypeToken<List<PaidBean>>() {}.getType());
        IOUtil.rewriteFile(new File(PaidCommand.getInstance().getDataFolder(), "paid.json"),json);
    }

    public static void delPaid(PaidBean paid){
        paidList.remove(paid);
        String json = new Gson().toJson(paidList, new TypeToken<List<PaidBean>>() {}.getType());
        IOUtil.rewriteFile(new File(PaidCommand.getInstance().getDataFolder(), "paid.json"),json);
    }
}
