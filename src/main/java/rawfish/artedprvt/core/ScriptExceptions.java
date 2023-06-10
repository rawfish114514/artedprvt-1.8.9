package rawfish.artedprvt.core;

import rawfish.artedprvt.core.rhino.SystemMethod;

/**
 * 脚本异常
 */
public class ScriptExceptions {
    public static void exception(String str){
        Thread t=Thread.currentThread();
        t.getUncaughtExceptionHandler().uncaughtException(t,new RuntimeException(str));
    }
    public static void exceptionModuleFullNameFormat(String str){
        exception("模块完整名格式错误: "+str);
    }

    public static void exceptionNoDefindLanguageAbbr(String str){
        exception("未定义的脚本语言缩写: "+str);
    }

    public static void exceptionNoFoundModule(String str){
        exception("找不到模块: "+str);
    }

    public static void exceptionInfoFieldFormat(String str1,String str2){
        exception("脚本信息字段值错误: "+str1+"="+str2);
    }

    public static void exceptionUseIgnorePackageName(String str){
        exception("使用被屏蔽的包名: "+str);
    }


    /*Rhino*/

    public static void exceptionSystemMethodInvoke(SystemMethod systemMethod){
        exception("系统方法调用异常: "+systemMethod.getName());
    }
}
