package rawfish.artedprvt.core;

import java.util.ArrayList;
import java.util.List;

/**
 * 调试信息
 * 显示到游戏调试界面
 */
public class DebugInfo {
    public static List<String> call(){
        List<String> list=new ArrayList<>();

        list.add("§cArted§bprvt §f1.2");
        list.add("A: "+ScriptSystem.B_CHAT+"  D: "+ScriptSystem.B_DEBUG);
        List<ScriptProcess> proList=ScriptProcess.getProList();
        ScriptProcess pro;
        int n=0;
        for(int i=0;i<proList.size();i++){
            pro=proList.get(i);
            n+=pro.getOnRunThreadNumber();
        }
        list.add("P: "+proList.size()+"  T: "+n);

        return list;
    }
}
