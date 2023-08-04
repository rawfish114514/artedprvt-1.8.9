package rawfish.artedprvt.core;

import rawfish.artedprvt.core.engine.ScriptStackParser;

import java.util.ArrayList;
import java.util.List;

public class ScriptExceptionHandler implements Thread.UncaughtExceptionHandler {
    private ScriptProcess process;
    private ScriptLogger logger;
    public ScriptExceptionHandler(ScriptProcess scriptProcess){
        this.process =scriptProcess;
        logger=process.getScriptLogger();
    }

    public void uncaughtException(Throwable e){
        uncaughtException(Thread.currentThread(),e);
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(e instanceof ThreadDeath){
            return;
        }
        String message="§4"+e.getMessage();
        String description="§4"+e.toString();
        List<Throwable> throwables=new ArrayList<>();
        while(e!=null){
            throwables.add(e);
            e=e.getCause();
        }
        List<ScriptStackElement[]> stackElements=new ArrayList<>();
        List<ScriptStackParser> stackParsers= process.getStackParsers();
        ScriptStackParser parser;
        for(Throwable throwable:throwables) {
            for (int i = 0; i < stackParsers.size(); i++) {
                parser = stackParsers.get(i);
                if(parser.parseable(throwable)){
                    stackElements.add(parser.parse(throwable));
                    break;
                }
            }
        }

        StringBuilder sb=new StringBuilder();
        sb.append(description);
        for(ScriptStackElement[] stackElementArray:stackElements){
            for(ScriptStackElement stackElement:stackElementArray){
                sb.append("\n");
                sb.append(stackElement);
            }
        }

        logger.error(sb.toString());
        if(sb.length()>0) {
            print(message, sb.toString());
        }else{
            print(message);
        }
        process.hasError();
        process.getScriptSystem().exit(ScriptProcess.ERROR);
    }

    public void print(String chat){
        process.getScriptSystem().print(ScriptSystem.DEBUG,chat);
    }

    public void print(String chat,String hover){
        process.getScriptSystem().print(ScriptSystem.DEBUG,chat,hover);
    }
}
