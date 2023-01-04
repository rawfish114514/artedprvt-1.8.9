package rawfish.artedprvt.script;

public class InitScript {
    public static String script=
                    "var invoker=function(){return _18140;};" +
                    "var pack=function(){return _1919810;};" +
                    "var args=function(){return _114514.getArgs(_1919810);};" +
                    "var print=function(object){_114514.print(_1919810,object);};" +
                    "var log=function(object){_114514.log(_1919810,object);};" +
                    "var import=function(pack){return _114514.importModule(_1919810,pack);};" +
                    "var export=function(object){_114514.exportModule(_1919810,object);};" +
                    "var thread=function(runnable){return _114514.createThread(_1919810,runnable);};" +
                    "var runf=function(func,args){return _114514.runFunction(_1919810,func,args);};";

    public static String varSys="_114514";
    public static String varPack="_1919810";
    public static String varInvoker="_18140";
}