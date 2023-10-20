package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.ScriptProcess;
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
            List<ScriptProcess> proList=ScriptProcess.getProList();
            int n=proList.size();
            ScriptProcess pro;
            while(proList.size()>0){
                pro=proList.get(0);
                pro.stop(ScriptProcess.STOPS);
                proList.remove(pro);
            }
            if(n==0){
                CommandMessages.exception(getName(), CMS.cms18);
            }
        }else if(args.size()==1){
            //选择
            int n=0;
            String arg=args.get(0);
            List<ScriptProcess> proList=ScriptProcess.getProList();
            ScriptProcess pro;
            for(int i=0;i<proList.size();i++){
                pro=proList.get(i);
                if(String.valueOf(pro.getPid()).equals(arg)
                        ||pro.getName().equals(arg)
                        ||pro.getScriptInfo().getId().equals(arg)){
                    pro.stop(ScriptProcess.STOPS);
                    n++;
                    i--;
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
                List<ScriptProcess> proList=ScriptProcess.getProList();
                List<String> l=new ArrayList<>();
                ScriptProcess process;
                for(int i=0;i<proList.size();i++){
                    process=proList.get(i);
                    l.add(String.valueOf(process.getPid()));
                }
                return l;
            }
            List<ScriptProcess> proList=ScriptProcess.getProList();
            List<String> l=new ArrayList<>();
            ScriptProcess process;
            for(int i=0;i<proList.size();i++){
                process=proList.get(i);
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
        return Literals.formatListBuilder().append("d");
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
