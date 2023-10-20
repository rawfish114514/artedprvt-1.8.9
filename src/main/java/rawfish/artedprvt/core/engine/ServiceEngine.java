package rawfish.artedprvt.core.engine;

import rawfish.artedprvt.core.ScriptLanguage;

public interface ServiceEngine {
    boolean isExecutable(ScriptLanguage language);
    /**
     * 执行脚本并调用函数
     * @param code 代码
     * @param func 函数名
     * @param args 参数列表
     * @return 函数运行返回值
     */
    Object call(String code,String func,Object... args) throws Exception;

    Object unwrap(Object obj);
}
