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
        commandList.add(new CommandBuild("build"));
        commandList.add(new CommandExtract("extract"));
        commandList.add(new CommandApkg("apkg"));
        commandList.add(new CommandIn("in"));
        commandList.add(new CommandPros("pros"));
        commandList.add(new CommandScript("script"));
        commandList.add(new CommandStops("stops"));
        commandList.add(new CommandStt("stt"));
        commandList.add(new CommandTm("tm"));
        commandList.add(new CommandWs("ws"));
    }

    @Override
    public void process(List<String> args) {
        if(args.size()<1){
            CommandMessages.exception(getName(),"cms1");
            return;
        }
        for(Command command:commandList){
            if (command.getName().equals(args.get(0))){
                command.process(args.subList(1,args.size()));
                return;
            }
        }
        CommandMessages.exception(getName(),"cms14",args.get(0));
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1){
            List<String> cl=new ArrayList<>();
            String name;
            for(Command command:commandList){
                name=command.getName();
                if(name.startsWith(args.get(0))){
                    cl.add(name);
                }
            }
            return cl;
        }
        //补全子命令参数
        Command c=null;
        for(Command command:commandList){
            if(command.getName().equals(args.get(0))){
                c=command;
            }
        }
        if(c==null){
            return nullTab;
        }
        return c.complete(args.subList(1,args.size()));
    }
}
