package rawfish.artedprvt.command;

import java.util.List;

/**
 * 命令
 * 这个接口和item子包的所有实现类都是mc版本无关的
 */
public abstract class Command{
    public Command(String commandName){
        this.commandName=commandName;
    }

    /**
     * 执行这个命令
     * @param args 参数列表
     */
    public abstract void process(List<String> args);

    /**
     * 补全参数
     * @param args 光标之前的参数列表
     * @return 最后一个参数的补全参数列表 不能为null
     */
    public abstract List<String> complete(List<String> args);

    /**
     * 参数格式
     * 在参数前添加格式代码
     * 只能包含格式代码 0-f kmnor
     * 否则被解释为无格式
     * 不写 小节
     *
     * @param args 完整的参数列表
     * @return 返回参数的格式列表 不能为null
     */
    public abstract List<? extends FormatHandler> format(List<String> args);

    /**
     * 参数信息
     *
     * @param args 光标之前的参数列表
     * @return 对参数的解释或提示 不能为null
     */
    public abstract InfoHandler info(List<String> args);

    /**
     * 重置
     * 在聊天栏关闭时调用
     */
    public void reset(){}


    private final String commandName;

    public String getName(){
        return commandName;
    }

}
