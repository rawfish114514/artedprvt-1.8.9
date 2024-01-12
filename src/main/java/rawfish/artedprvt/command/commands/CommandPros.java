package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.core.CoreInitializer;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.core.app.script.ScriptProcess;
import rawfish.artedprvt.std.text.Formatting;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 展示脚本进程信息
 */
public class CommandPros extends Command {
    public CommandPros(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, Messager messager) {
        if(args.size()==0){
            //全部
            List<ScriptProcess> processList= CoreInitializer.getProcessController().getProcessList(ScriptProcess.class);
            for(ScriptProcess process:processList){
                printPro(process);
            }
            if(processList.size()==0){
                messager.send(Formatting.DARK_RED+getName()+ CMS.cms11);
            }
        }else if(args.size()==1){
            //选择
            String arg=args.get(0);
            int n=0;
            List<ScriptProcess> processList= CoreInitializer.getProcessController().getProcessList(ScriptProcess.class);
            for(ScriptProcess process:processList){
                if(String.valueOf(process.getPid()).equals(arg)){
                    printPro(process);
                    n++;
                    continue;
                }
                if(process.getName().contains(arg)){
                    printPro(process);
                    n++;
                    continue;
                }
                if (process.getScriptInfo().getId().equals(arg)) {
                    printPro(process);
                    n++;
                }
            }
            if(n==0){
                messager.send(Formatting.DARK_RED+getName()+ MessageFormat.format(CMS.cms15,arg));
            }
        }else{
            messager.send(Formatting.DARK_RED+getName()+CMS.cms16);
        }
    }

    public void printPro(ScriptProcess pro){
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1){
            String arg=args.get(0);
            if(arg.trim().isEmpty()){
                List<ScriptProcess> processList= CoreInitializer.getProcessController().getProcessList(ScriptProcess.class);
                List<String> l=new ArrayList<>();
                for(ScriptProcess process:processList){
                    l.add(String.valueOf(process.getPid()));
                }
                return l;
            }
            List<ScriptProcess> processList= CoreInitializer.getProcessController().getProcessList(ScriptProcess.class);
            List<String> l=new ArrayList<>();
            for(ScriptProcess process:processList){
                String s=String.valueOf(process.getPid());
                if(s.contains(arg)){
                    l.add(s);
                }
                s=process.getName();
                if(s.contains(arg)&&!l.contains(s)){
                    l.add(s);
                }
                s=process.getScriptInfo().getId();
                if(s.contains(arg)&&!l.contains(s)){
                    l.add(s);
                }
            }
            return l;
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.formatListBuilder().append("a");
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
