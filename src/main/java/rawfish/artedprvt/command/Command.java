package rawfish.artedprvt.command;

import java.util.Collections;
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
     * @return 最后一个参数的补全参数列表
     */
    public abstract List<String> complete(List<String> args);

    private final String commandName;

    public String getName(){
        return commandName;
    }

    private static final List<String> emptyList=Collections.emptyList();

    /**
     * 返回空列表
     * @return
     */
    public static List<String> getEmptyList() {
        return emptyList;
    }
}
