package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;

/**
 * 命令
 * 这个接口和item子包的所有实现类都是mc版本无关的
 */
@Solvable
public abstract class Command implements CommandProcess,CommandComplete,CommandFormat,CommandInfo{
    @Solvable
    public Command(String commandName){
        this.commandName=commandName;
    }

    /**
     * 重置
     * 表示命令有机会更新自身的数据
     * 在用户使用聊天栏时 命令通常无法在合适的时机更新自身
     * 此方法保证在合适的时机被聊天控件被关闭时调用
     */
    @Solvable
    public void reset(){}


    private final String commandName;

    @Solvable
    public String getName(){
        return commandName;
    }

}
