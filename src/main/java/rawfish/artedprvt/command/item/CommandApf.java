package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;

import java.util.ArrayList;
import java.util.List;

public class CommandApf extends Command {
    public List<Command> commandList;
    public CommandApf(String commandName) {
        super(commandName);
        commandList=new ArrayList<>();

        commandList.add(new CommandAc("ac"));
        commandList.add(new CommandAd("ad"));
        commandList.add(new CommandApkg("apkg"));
        commandList.add(new CommandIn("in"));
        commandList.add(new CommandPros("pros"));
        commandList.add(new CommandScript("script"));
        commandList.add(new CommandStops("stops"));
        commandList.add(new CommandStt("stt"));
        commandList.add(new CommandWorkspace("workspace"));
    }

    @Override
    public void process(List<String> args) {
        if(args.size()<1){
            CommandMessages.exception(getCommandName(),"参数不能为空");
            return;
        }
        for(Command command:commandList){
            if (command.getCommandName().equals(args.get(0))){
                command.process(args.subList(1,args.size()));
                return;
            }
        }
        CommandMessages.exception(getCommandName(),"找不到命令: "+args.get(0));
    }

    @Override
    public List<String> tab(List<String> args) {
        if(args.size()==1){
            List<String> cl=new ArrayList<>();
            String name;
            for(Command command:commandList){
                name=command.getCommandName();
                if(name.startsWith(args.get(0))){
                    cl.add(name);
                }
            }
            return cl;
        }
        //补全子命令参数
        Command c=null;
        for(Command command:commandList){
            if(command.getCommandName().equals(args.get(0))){
                c=command;
            }
        }
        if(c==null){
            return nullTab;
        }
        return c.tab(args.subList(1,args.size()));
    }
}
