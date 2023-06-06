package rawfish.artedprvt.core;

import rawfish.artedprvt.core.engine.ScriptStackParser;

import java.util.ArrayList;
import java.util.List;

public class ScriptExceptionHandler implements Thread.UncaughtExceptionHandler {
    private ScriptProcess scriptProcess;
    public ScriptExceptionHandler(ScriptProcess scriptProcess){
        this.scriptProcess=scriptProcess;
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if(e instanceof ThreadDeath){
            return;
        }
        e.printStackTrace(System.err);
        String message="§4"+e.getMessage();
        List<Throwable> throwables=new ArrayList<>();
        while(e!=null){
            throwables.add(e);
            e=e.getCause();
        }
        List<ScriptStackElement[]> stackElements=new ArrayList<>();
        List<ScriptStackParser> stackParsers=scriptProcess.getStackParsers();
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

        if(stackElements.size()==0){
            return;
        }
        StringBuilder sb=new StringBuilder();
        sb.append(message);
        for(ScriptStackElement[] stackElementArray:stackElements){
            for(ScriptStackElement stackElement:stackElementArray){
                sb.append("\n");
                sb.append(stackElement);
            }
        }

        print(message,sb.toString());
        scriptProcess.hasError();
        scriptProcess.getScriptSystem().exit(ScriptProcess.ERROR);
    }

    public void print(String chat,String hover){
        scriptProcess.getScriptSystem().print(ScriptSystem.DEBUG,chat,hover);
    }
}