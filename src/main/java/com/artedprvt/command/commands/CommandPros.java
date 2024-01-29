package com.artedprvt.command.commands;

import com.artedprvt.command.BaseCommand;
import com.artedprvt.core.CoreInitializer;
import com.artedprvt.core.app.AppProcess;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.util.FormatHandlerListBuilder;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.command.FormatMessager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 展示应用进程信息
 */
public class CommandPros extends BaseCommand {
    public CommandPros(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        getAppProcesses();
        if(args.size()==0){
            for(AppProcess appProcess:appProcessMap.values()){
                messager.send(getAppProcessInfo(appProcess));
            }
            return;
        }
        if(args.size()==1){
            String arg0=args.get(0);
            AppProcess appProcess=appProcessMap.get(arg0);
            if(appProcess==null){
                messager.red("找不到 pid 为 "+arg0+" 的应用进程");
                return;
            }
            messager.send(getAppProcessInfo(appProcess));
            return;
        }
        messager.red("参数异常");
    }

    @Override
    public List<String> complete(List<String> args) {
        getAppProcesses();

        return new ArrayList<>(appProcessMap.keySet());
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        getAppProcesses();

        FormatHandlerListBuilder builder= Literals.formatListBuilder();

        if(args.size()>0) {
            String arg0=args.get(0);
            if(appProcessMap.containsKey(arg0)){
                builder.append("a");
            }else{
                builder.append("c");
            }
        }

        return builder;
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()==0){
            return Literals.infoFactory().string("查看运行中的应用进程");
        }
        getAppProcesses();

        if(args.size()==1){
            String arg0=args.get(0);
            if(arg0.isEmpty()){
                return Literals.infoFactory().string("pid");
            }
            AppProcess appProcess=appProcessMap.get(arg0);
            if(appProcess!=null){
                return Literals.infoFactory().string(getAppProcessInfo(appProcess));
            }
        }

        return Literals.emptyInfo();
    }

    @Override
    public boolean frequent(){
        return true;
    }

    public String getAppProcessInfo(AppProcess appProcess){
        return ""+appProcess.getName()+" "+appProcess.getPid();
    }

    private Map<String,AppProcess> appProcessMap=new HashMap<>();

    private long time=0;

    private synchronized void getAppProcesses(){
        long t = System.currentTimeMillis();
        if (t - time < 256) {
            return;
        }
        time = t;
        List<AppProcess> appProcesses= CoreInitializer.getProcessController().getProcessList(AppProcess.class);
        appProcessMap=new HashMap<>();
        for(AppProcess appProcess:appProcesses){
            appProcessMap.put(String.valueOf(appProcess.getPid()),appProcess);
        }
    }
}
