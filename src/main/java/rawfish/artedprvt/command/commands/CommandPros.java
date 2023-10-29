package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.ProcessController;
import rawfish.artedprvt.core.Process;
import rawfish.artedprvt.core.script.ScriptProcess;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.mi.ChatProvider;

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
    public void process(List<String> args) {
        if(args.size()==0){
            //全部
            List<? extends Process> processList= ProcessController.getProcessList();
            for(Process process:processList){
                printPro(process);
            }
            if(processList.size()==0){
                CommandMessages.exception(getName(), CMS.cms11);
            }
        }else if(args.size()==1){
            //选择
            String arg=args.get(0);
            int n=0;
            List<? extends Process> processList= ProcessController.getProcessList();
            for(Process process:processList){
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
                if(process instanceof ScriptProcess) {
                    ScriptProcess scriptProcess=(ScriptProcess) process;
                    if (scriptProcess.getScriptInfo().getId().equals(arg)) {
                        printPro(process);
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

    public void printPro(Process pro){
        CommandMessages.printChat.print("§6>§f"+pro.getPid() + ": " + pro.getName(), new ChatProvider() {
            public Process p;
            public ChatProvider setP(Process p){
                this.p=p;
                return this;
            }
            @Override
            public String getChat() {
                if(p!=null){/*
                    String s=p.getName()+" ("+p.getScriptInfo().getId()+")";
                    s+="\n";
                    s+="pid: "+p.getPid();
                    s+="\n";
                    s+=p.getStatistics();
                    String c =null;
                    double cpu=p.getCPU();
                    if (cpu >= 0) {
                        c = new DecimalFormat("0.0").format(cpu * 100) + "%";
                    }
                    if(c!=null) {
                        s += "\n";
                        s += "§fcpu: "+c;
                    }
                    return s;*/
                }
                if(p.getRet()== Process.END){
                    p=null;
                }
                return null;
            }
        }.setP(pro));
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1){
            String arg=args.get(0);
            if(arg.trim().isEmpty()){
                List<? extends Process> processList= ProcessController.getProcessList();
                List<String> l=new ArrayList<>();
                for(Process process:processList){
                    l.add(String.valueOf(process.getPid()));
                }
                return l;
            }
            List<? extends Process> processList= ProcessController.getProcessList();
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
        return Literals.formatListBuilder().append("a");
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()>1){
            return Literals.infoBuilder().string(CIS.cis3);
        }
        if(args.get(0).isEmpty()){
            return Literals.infoBuilder().string(CIS.cis7);
        }
        return Literals.emptyInfo();
    }
}
