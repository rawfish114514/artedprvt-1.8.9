package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.Artedprvt;

public class ScriptEngineInitThread extends Thread{
    private ScriptEngine scriptEngine;
    public ScriptEngineInitThread(ScriptEngine scriptEngine){
        this.scriptEngine=scriptEngine;
    }

    @Override
    public void run(){
        Artedprvt.logger.info("初始化脚本引擎类: "+scriptEngine.getClass().getName());
        scriptEngine.init();
        Artedprvt.logger.info("初始化脚本引擎类完成: "+scriptEngine.getClass().getName());
    }
}
