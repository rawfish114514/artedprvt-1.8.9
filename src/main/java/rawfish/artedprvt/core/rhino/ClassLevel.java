package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.Artedprvt;

/**
 * 类特殊访问相关
 */
public class ClassLevel {
    /**
     * 判断Class是否是 Minecraft 混淆的类
     *
     * @param clas 类
     * @return clas是混淆类
     */
    public static boolean isObfuscationClass(Class clas){
            //貌似所有net/minecraft下的类都是混淆类且混淆类都在net/minecraft下
            return isObfuscation()&& contain(clas);
    }
    private static boolean contain(Class clas){
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
        boolean issup= contain(sup);
        for(Class inf:clas.getInterfaces()){
            if(contain(inf)){
                return true;
            }
        }
        return issup;
    }
    private static boolean isNetMinecraft(Class clas){
        String name=clas.getName();
        return name.startsWith("net.minecraft.")||name.startsWith("com.mojang.");
    }
    private static boolean isObfuscation(){
        return Artedprvt.instance.isNotDevelopment();
    }
}
