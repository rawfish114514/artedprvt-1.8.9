package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.CoreInitializer;
import rawfish.artedprvt.core.Process;
import rawfish.artedprvt.core.script.ScriptProcess;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;

import java.util.ArrayList;
import java.util.List;

/**
 * 停止脚本进程
 */
public class CommandStops extends Command {
    public CommandStops(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()==0){
            //全部
            List<? extends Process> processList= CoreInitializer.getProcessController().getProcessList();
            for(Process process:processList){
                process.stop(Process.STOPS);
            }
            if(processList.size()==0){
                CommandMessages.exception(getName(), CMS.cms18);
            }
        }else if(args.size()==1){
            //选择
            String arg=args.get(0);
            int n=0;
            List<? extends Process> processList= CoreInitializer.getProcessController().getProcessList();
            for(Process process:processList){
                if(String.valueOf(process.getPid()).equals(arg)){
                    process.stop(Process.STOPS);
                    n++;
                    continue;
                }
                if(process.getName().contains(arg)){
                    process.stop(Process.STOPS);
                    n++;
                    continue;
                }
                if(process instanceof ScriptProcess) {
                    ScriptProcess scriptProcess=(ScriptProcess) process;
                    if (scriptProcess.getScriptInfo().getId().equals(arg)) {
                        process.stop(Process.STOPS);
                        n++;
                    }
                }
            }
            if(n==0){
                CommandMessages.exception(getName(),CMS.cms15,arg);
            }
        }else{
            CommandMessages.exception(getName(),CMS.cms16);
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1){
            String arg=args.get(0);
            if(arg.trim().isEmpty()){
                List<? extends Process> processList= CoreInitializer.getProcessController().getProcessList();
                List<String> l=new ArrayList<>();
                for(Process process:processList){
                    l.add(String.valueOf(process.getPid()));
                }
                return l;
            }
            List<? extends Process> processList= CoreInitializer.getProcessController().getProcessList();
            List<String> l=new ArrayList<>();
            for(Process process:processList){
                String s=String.valueOf(process.getPid());
                if(s.contains(arg)){
                    l.add(s);
                }
                s=process.getName();
                if(s.contains(arg)&&!l.contains(s)){
                    l.add(s);
                }
                /*
                s=process.getScriptInfo().getId();
                if(s.contains(arg)&&!l.contains(s)){
                    l.add(s);
                }*/
            }
            return l;
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.formatListBuilder().append("d");
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()>1){
            return Literals.infoFactory().string(CIS.cis3);
        }
        if(args.get(0).isEmpty()){
            return Literals.infoFactory().string(CIS.cis7);
        }
        return Literals.emptyInfo();
    }
}
