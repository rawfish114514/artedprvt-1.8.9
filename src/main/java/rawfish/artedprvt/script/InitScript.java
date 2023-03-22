package rawfish.artedprvt.script;


public class InitScript {
    //put: System Pack Invoker
    public static String script=
                    "var invoker=function(){return _18140;};\n" +
                    "var pack=function(){return _1919810;};\n" +
                    "var args=function(){return _114514.getArgs(_1919810);};\n" +
                    "var print=function(object,hover){_114514.print(_1919810,object,hover);};\n" +
                    "var log=function(object,hover){_114514.log(_1919810,object,hover);};\n" +
                    "var import=function(pack){return _114514.importModule(_1919810,pack);};\n" +
                    "var export=function(object){_114514.exportModule(_1919810,object);};\n" +
                    "var class=function(object){return _114514.getJavaClass(_1919810,object);};\n" +
                    "var thread=function(runnable){return _114514.createThread(_1919810,runnable);};\n" +
                    "var runf=function(func,args){return _114514.runFunction(_1919810,func,args);};\n" +
                    "var sleep=function(millis){_114514.sleep(_1919810,millis);};\n" +
                    "var exit=function(status){_114514.exit(_1919810,status);};\n";

    public static String varSys="_114514";
    public static String varPack="_1919810";
    public static String varInvoker="_18140";



    //put: Client
    public static String clientscript=
                    "var sendChat=function(message){_client.sendChat(message);};\n" +
                    "var getAllEntity=function(){return _client.getAllEntity();};\n" +
                    "var getThisPlayer=function(){return _client.getThisPlayer();};\n";

    public static String varClient="_client";
}
