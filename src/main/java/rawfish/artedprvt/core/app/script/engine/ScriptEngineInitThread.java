package rawfish.artedprvt.core.app.script.engine;

import rawfish.artedprvt.core.Environment;

public class ScriptEngineInitThread extends Thread {
    private ScriptEngine scriptEngine;

    public ScriptEngineInitThread(ScriptEngine scriptEngine) {
        this.scriptEngine = scriptEngine;
    }

    @Override
    public void run() {
        Environment.MODLOGGER.info("初始化脚本引擎类: " + scriptEngine.getClass().getName());
        scriptEngine.init();
        Environment.MODLOGGER.info("初始化脚本引擎类完成: " + scriptEngine.getClass().getName());
    }
}
