package rawfish.artedprvt.std.cli;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.core.InProcess;

import java.util.HashMap;
import java.util.Map;

@Solvable
public class CommandRegistry implements InProcess {
    private boolean up;

    private Map<String, Command> commandMap = new HashMap<>();

    @Solvable
    public CommandRegistry() {
        up = up() != null;
    }


    @Solvable
    public void register(Command command) {
        if (up) {
            if (CommandHandler.register(command)) {
                commandMap.put(command.getName(), command);
            } else {
                throw new RuntimeException("注册失败");
            }
        } else {
            throw new RuntimeException("此注册表没有正确up");
        }
    }

    @Solvable
    public void unregister(Command command) {
        unregister(command.getName());
    }

    @Solvable
    public void unregister(String name) {
        if (up) {
            if (commandMap.get(name) != null) {
                if (CommandHandler.unregister(name)) {
                    commandMap.remove(name);
                } else {
                    throw new RuntimeException("注销失败");
                }
            } else {
                throw new RuntimeException("没有此命令");
            }
        } else {
            throw new RuntimeException("此注册表没有正确up");
        }
    }

    @Override
    @Solvable
    public void close() {
        for (String name : commandMap.keySet()) {
            this.unregister(name);
        }
    }
}
