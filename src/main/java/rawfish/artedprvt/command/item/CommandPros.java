package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.mi.ChatProvider;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CommandPros extends Command {
    public CommandPros(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()==0){
            //全部
            List<ScriptProcess> proList=ScriptProcess.getProList();
            int n=proList.size();
            for(int i=0;i<n;i++){
                ScriptProcess pro=proList.get(i);
                printPro(pro);
            }
            if(n==0){
                CommandMessages.exception(getName(),"cms11");
            }
        }else if(args.size()==1){
            //选择
            int n=0;
            String arg=args.get(0);
            List<ScriptProcess> proList=ScriptProcess.getProList();
            for(int i=0;i<proList.size();i++){
                ScriptProcess pro=proList.get(i);
                if(String.valueOf(pro.getPid()).equals(arg)
                        ||pro.getName().contains(arg)
                        ||pro.getScriptInfo().getId().equals(arg)){
                    printPro(pro);
                    n++;
                }
            }
            if(n==0){
                CommandMessages.exception(getName(),"cms15",arg);
            }
        }else{
            CommandMessages.exception(getName(),"cms16");
        }
    }

    public void printPro(ScriptProcess pro){
        CommandMessages.printChat.print("§6>§f"+pro.getPid() + ": " + pro.getName(), new ChatProvider() {
            public ScriptProcess p;
            public ChatProvider setP(ScriptProcess p){
                this.p=p;
                return this;
            }
            @Override
            public String getChat() {
                if(p!=null){
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
                    return s;
                }
                if(p.getRet()==ScriptProcess.END){
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
        return nullTab;
    }
}
