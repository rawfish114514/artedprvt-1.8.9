package rawfish.artedprvt.core;

import rawfish.artedprvt.core.engine.ScriptEngine;

import java.util.List;

public class MainThread extends Thread{
    private ScriptProcess process;
    public MainThread(ScriptProcess process){
        this.process=process;
        setName("Main");
        setUncaughtExceptionHandler(process.getExceptionHandler());
    }

    @Override
    public void run(){
        process.setScriptSystem(new ScriptSystem(process));
        List<ScriptEngine> engines=process.getEngines();
        //初始化脚本引擎
        for(int i=0;i<engines.size();i++){
            engines.get(i).init();
        }
        //准备完成
        process.begin();
        //执行主模块
        process.getScriptSystem().importModule(process.getScriptInfo().getModule());



        //脚本线程
        try {
            List<ScriptThread> threads=process.getThreads();
            for(int i=0;i<threads.size();i++) {
                threads.get(i).join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        process.end(ScriptProcess.EXIT);
    }

    /**
     * 停止
     */
    public void jstop(){
        stop();
    }

    public ScriptProcess getProcess(){
        return process;
    }
}
