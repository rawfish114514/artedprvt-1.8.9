package rawfish.artedprvt.command.commandimpls;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.command.Formatter;
import rawfish.artedprvt.command.FormatterList;
import rawfish.artedprvt.command.formatterimpls.FormatterAppend;

import java.util.ArrayList;
import java.util.List;

/**
 * 可执行所有这个模组定义的命令 防止命名冲突
 */
public class CommandApf extends Command {
    public List<Command> commandList;
    public CommandApf(String commandName) {
        super(commandName);
        commandList=new ArrayList<>();

        commandList.add(new CommandAc("ac"));
        commandList.add(new CommandAd("ad"));
        commandList.add(new CommandApkg("apkg"));
        commandList.add(new CommandBuild("build"));
        commandList.add(new CommandExtract("extract"));
        commandList.add(new CommandIn("in"));
        commandList.add(new CommandInfo("info"));
        commandList.add(new CommandInstall("install"));
        commandList.add(new CommandOptions("options"));
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
            return getEmptyStringList();
        }
        return c.complete(args.subList(1,args.size()));
    }

    @Override
    public List<? extends Formatter> format(List<String> args) {
        Command c=null;
        for(Command command:commandList){
            if(command.getName().equals(args.get(0))){
                c=command;
            }
        }
        if(c==null){
            return formatterAppendList("c");
        }
        FormatterList fl=new FormatterList();
        fl.add(new FormatterAppend("bo"));
        List<String> sargs=args.subList(1,args.size());
        if(sargs.size()>0) {
            fl.addAll(c.format(sargs));
        }
        return fl;
    }

    @Override
    public String info(List<String> args) {
        if(args.size()==1&&args.get(0).isEmpty()){
            return CommandMessages.translate("cis4");
        }
        Command c=null;
        for(Command command:commandList){
            if(command.getName().equals(args.get(0))){
                c=command;
            }
        }
        if(c==null){
            return CommandMessages.translate("cis0");
        }
        List<String> sargs=args.subList(1,args.size());
        if(sargs.size()>0) {
            return c.info(args.subList(1,args.size()));
        }
        return getEmptyString();
    }
}
