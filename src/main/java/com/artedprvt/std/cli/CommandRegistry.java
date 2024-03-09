package com.artedprvt.std.cli;

import com.artedprvt.core.InProcess;
import com.artedprvt.iv.anno.InterfaceView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

@InterfaceView
public class CommandRegistry implements InProcess {
    private boolean up;

    private Map<String, Command> commandMap = new HashMap<>();

    @InterfaceView
    public CommandRegistry() {
        up = up() != null;
    }


    @InterfaceView
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

    @InterfaceView
    public void unregister(Command command) {
        unregister(command.getName());
    }

    @InterfaceView
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
    @InterfaceView
    public void close() throws Exception {
        for (String name : new HashSet<>(commandMap.keySet())) {
            this.unregister(name);
        }
    }
}
