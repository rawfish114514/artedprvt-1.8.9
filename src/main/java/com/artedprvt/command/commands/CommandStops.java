package com.artedprvt.command.commands;

import com.artedprvt.command.BaseCommand;
import com.artedprvt.core.CoreInitializer;
import com.artedprvt.core.Process;
import com.artedprvt.core.app.AppProcess;
import com.artedprvt.core.localization.types.CIS;
import com.artedprvt.core.localization.types.CMS;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.util.FormatHandlerListBuilder;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.command.FormatMessager;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 停止应用进程
 */
public class CommandStops extends BaseCommand {
    public CommandStops(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        getAppProcesses();
        if(args.size()==0){
            for(AppProcess appProcess:appProcessMap.values()){
                appProcess.stop(Process.STOPS);
            }
            return;
        }
        if(args.size()==1){
            String arg0=args.get(0);
            AppProcess appProcess=appProcessMap.get(arg0);
            if(appProcess==null){
                messager.red(MessageFormat.format(CMS.cms40,arg0));
                return;
            }
            appProcess.stop(Process.STOPS);
            return;
        }
        messager.red(CMS.cms38);
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
            return Literals.infoFactory().string(CIS.cis18);
        }
        getAppProcesses();

        if(args.size()==1){
            String arg0=args.get(0);
            if(arg0.isEmpty()){
                return Literals.infoFactory().string(CIS.cis17);
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
