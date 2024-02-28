package com.artedprvt.client;

import com.artedprvt.core.CoreInitializer;
import com.artedprvt.core.Environment;
import com.artedprvt.core.Process;
import com.artedprvt.core.app.AppProcess;

import java.util.List;

/**
 * 调试信息
 * 显示到游戏调试界面
 */
public class DebugInfo {
    public static String version = Environment.MODVERSION;

    public static String call(boolean isf3) {
        if (isf3) {
            StringBuilder sb = new StringBuilder();
            List<AppProcess> proList = CoreInitializer.getProcessController().getProcessList(AppProcess.class);
            Process pro;

            int p = 0;
            int t = 0;
            for (int i = 0; i < proList.size(); i++) {
                pro = proList.get(i);
                if (pro.getRet() >= Process.BEGIN) {
                    t += pro.getRunningThreadCount();
                    p++;
                }
            }

            sb.append("§cArted§bprvt§r");
            sb.append(" ");
            sb.append(version);
            sb.append(" ");
            sb.append(p);
            sb.append(" ");
            sb.append(t);

            return sb.toString();
        }
        return "";
    }
}
