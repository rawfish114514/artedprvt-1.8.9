package rawfish.artedprvt.script.js;

import org.mozilla.javascript.Scriptable;
import rawfish.artedprvt.script.MainThread;
import rawfish.artedprvt.script.ScriptThread;

/**
 * 类特殊访问相关
 */
public class ClassLevel {
    /**
     * 判断Class是否是 Minecraft 混淆的类
     * @param clas 类
     * @return clas是混淆类
     */
    public static boolean isConfuseClass(Scriptable scope, Class clas){
            //貌似所有net/minecraft下的类都是混淆类且混淆类都在net/minecraft下
            return isReConfuse(scope)&&concla(clas);
    }
    private static boolean concla(Class clas){
        if(clas==null){
            return false;
        }
        if(clas==Object.class){
            return false;
        }
        if(isNetMinecraft(clas)){
            return true;
        }
        Class sup=clas.getSuperclass();
        boolean issup=concla(sup);
        for(Class inf:clas.getInterfaces()){
            if(concla(inf)){
                return true;
            }
        }
        return issup;
    }
    private static boolean isNetMinecraft(Class clas){
        String name=clas.getName();
        if(name.length()<14){
            return false;
        }
        return name.substring(0,14).equals("net.minecraft.");
    }
    private static boolean isReConfuse(Scriptable scope){
        return scope.get(ClassLevel.varRc,scope).equals("true");
    }

    //脚本系统变量
    public static String varRc="__reconfuse__";

    //空映射
    public static String memberNull="0";

    //连接符 区别于Srg名规则
    public static String link="/";
}
