package rawfish.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 调试信息
 * 显示到游戏调试界面
 */
public class DebugInfo {
    public static List<String> call(boolean isf3){
        if(isf3) {
            List<String> list = new ArrayList<>();

            list.add("");
            list.add("§cArted§bprvt §f1.2");
            list.add("A: " + ScriptSystem.B_CHAT + "  D: " + ScriptSystem.B_DEBUG);
            List<ScriptProcess> proList = ScriptProcess.getProList();
            ScriptProcess pro;
            int n = 0;
            for (int i = 0; i < proList.size(); i++) {
                pro = proList.get(i);
                n += pro.getOnRunThreadNumber();
            }
            list.add("P: " + proList.size() + "  T: " + n);

            return list;
        }else{
            List<String> list = new ArrayList<>();

            if(ScriptSystem.B_DEBUG) {
                List<ScriptProcess> processList = ScriptProcess.getProList();
                ScriptProcess process;
                for (int i = 0; i < processList.size(); i++) {
                    process = processList.get(i);
                    String str=process.getName()+"("+process.getScriptInfo().getId()+") "+process.getPid();
                    list.add(str);
                }
            }

            return list;
        }
    }
}
