package rawfish.artedprvt.core.app.script;

import rawfish.artedprvt.core.AbstractExceptionHandler;
import rawfish.artedprvt.core.Logger;
import rawfish.artedprvt.core.app.script.engine.ScriptStackParser;

import java.util.ArrayList;
import java.util.List;

public class ScriptExceptionHandler extends AbstractExceptionHandler<ScriptProcess> {
    private Logger logger;
    public ScriptExceptionHandler(ScriptProcess scriptProcess){
        super(scriptProcess);
        logger=process.logger();
    }

    public void uncaughtException(Throwable e){
        uncaughtException(Thread.currentThread(),e);
    }
    @Override
    public void uncaughtException(Thread t, Throwable e) {
        e.printStackTrace(System.err);
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
            //print(message, sb.toString());
            print(message);
        }else{
            print(message);
        }
        process.hasError();
        process.getScriptSystem().exit(ScriptProcess.ERROR);
    }

    public void print(String chat){
        process.getScriptSystem().print(chat);
    }
}
