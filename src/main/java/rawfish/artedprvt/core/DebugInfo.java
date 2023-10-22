package rawfish.artedprvt.core;

import rawfish.artedprvt.Artedprvt;

import java.util.List;

/**
 * 调试信息
 * 显示到游戏调试界面
 */
public class DebugInfo {
    public static String version= Artedprvt.VERSION;
    public static String call(boolean isf3){
        if(isf3) {
            StringBuilder sb = new StringBuilder();

            List<ScriptProcess> proList = ProcessController.getProcessList();
            ScriptProcess pro;

            int p=proList.size();
            int t = 0;
            for (int i = 0; i < p; i++) {
                pro = proList.get(i);
                t += pro.getOnRunThreadNumber();
            }

            sb.append("  ");
            sb.append("§cArted§bprvt§r");
            sb.append(" ");
            sb.append(version);
            sb.append(" ");

            if(ScriptSystem.B_CHAT){
                sb.append("§aC§r");
            }else{
                sb.append("C");
            }
            if(ScriptSystem.B_DEBUG){
                sb.append("§aD§r");
            }else{
                sb.append("D");
            }
            sb.append(" ");

            sb.append(p);
            sb.append(" ");
            sb.append(t);

            return sb.toString();
        }
        return "";
    }
}
