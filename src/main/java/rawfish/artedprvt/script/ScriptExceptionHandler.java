package rawfish.artedprvt.script;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.*;

public class ScriptExceptionHandler implements Thread.UncaughtExceptionHandler {
    protected ScriptProcess pro;
    public ScriptExceptionHandler(ScriptProcess proIn){
        pro=proIn;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(!e.getMessage().equals("null")){
            e.printStackTrace();
            if(ScriptConst.debug) {
                pro.sys.log(pro.pack,"\u00a74" +": " + e.getMessage(),getJsStackTrace(e));
            }
            if(t instanceof ScriptThread){
                ScriptThread st=(ScriptThread)t;
                if(!st.errorHandle){
                    pro.stop(st);
                }
            }else {
                pro.stop(null);
            }
        }
    }

    public String getJsStackTrace(Throwable e){
        StringWriter writer=new StringWriter();
        PrintWriter print=new PrintWriter(writer);
        e.printStackTrace(print);
        String str=writer.toString();

        String[] items=str.split("\\u000d\\u000a\\u0009");
        List<String> list=new ArrayList<>();
        list.add("\u00a74"+e.getMessage());
        for (String item : items) {
            if (item.length()>=9&&item.substring(0,9).equals("at script")) {
                String s=parenthesis(item.trim());

                if(!ignore.contains(s)) {
                    list.add(color(s));
                }
            }
        }

        return String.join("\n  ",list);
    }

    public String parenthesis(String s){
        return s.substring(s.indexOf("(")+1,s.indexOf(")"));
    }

    /**
     * 分配颜色
     * @param s
     * @return
     */
    public String color(String s){
        return "\u00a7rat script(\u00a7b"+s+"\u00a7r)";
    }

    /**
     * 这些字段和初始化程序和脚本单元相关
     */
    public static Set<String> ignore=new HashSet(){{
        add("/init_sys:1");
        add("/init_sys:2");
        add("/init_sys:3");
        add("/init_sys:4");
        add("/init_sys:5");
        add("/init_sys:6");
        add("/init_sys:7");
        add("/init_sys:8");
        add("/init_sys:9");
        add("/init_sys:10");
        add("/init_sys:11");
    }};
}

