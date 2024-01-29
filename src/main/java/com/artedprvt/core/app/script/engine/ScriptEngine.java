package com.artedprvt.core.app.script.engine;

import com.artedprvt.core.app.script.struct.ScriptModule;

/**
 * 脚本引擎
 * 负责执行脚本和管理上下文
 */
public interface ScriptEngine {
    void init();

    /**
     * 是可处理的
     *
     * @param scriptModule
     * @return
     */
    boolean isExecutable(ScriptModule scriptModule);

    /**
     * 执行
     * 还负责创建内置变量
     *
     * @param scriptModule
     */
    void execute(ScriptModule scriptModule);
}
