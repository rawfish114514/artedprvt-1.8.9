package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.FrameOptions;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 修改和更新选项
 */
public class CommandOptions extends Command {
    public CommandOptions(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()==0){
            //展示所有选项
            StringBuilder sb=new StringBuilder();
            Map<String,Object> config=new HashMap<>(FrameOptions.options);
            config.forEach((k,v)-> sb.append("\n  ").append(k).append(" = ").append(String.valueOf(v)));
            CommandMessages.key(getName(),"cms28",sb.toString());
            return;
        }
        if(args.size()==1){
            //展示选项
            String key=args.get(0);
            if(key.equals("load")){
                //加载
                FrameOptions.load();
                CommandMessages.key(getName(),"cms27");
                try {
                    FrameOptions.updateFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            Object value= FrameOptions.getValue(key);
            if(value==null){
                CommandMessages.exception(getName(),"cms25",key);
            }else{
                CommandMessages.key(getName(),"cms26",key,value);
            }
            return;
        }
        if(args.size()==2){
            //修改选项
            String key=args.get(0);
            String value=args.get(1);
            Object v= FrameOptions.getValue(key);
            if(v==null){
                CommandMessages.exception(getName(),"cms25",key);
            }else{
                FrameOptions.setValue(key,value);
                try {
                    FrameOptions.updateFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                CommandMessages.key(getName(),"cms26",key,value);
            }
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1) {
            List<String> list= new ArrayList<>(FrameOptions.options.keySet());
            list.add("load");
            return startWith(args,list);
        }else if(args.size()==2) {
            List<String> vs= FrameOptions.getValues(args.get(0));
            if(vs==null){
                return getEmptyList();
            }
            return startWith(args,new ArrayList<>(vs));
        }
        return getEmptyList();
    }

    @Override
    public List<String> format(List<String> args) {
        if(args.size()==0){
            return getEmptyList();
        }
        if(args.get(0).equals("load")){
            return stringList("6");
        }
        if(args.size()==1){
            return stringList("d");
        }
        return stringList("d","a");
    }

    public String info(List<String> args){
        if(args.size()==1){
            String key=args.get(0);
            if(key.isEmpty()){
                return CommandMessages.translate("cis8");
            }
            if(key.equals("load")){
                return CommandMessages.translate("cis11");
            }
            Object value= FrameOptions.getValue(key);
            if(value==null){
                return CommandMessages.translate("cis9");
            }else{
                /*key info*/
                if(key.equals("language")){
                    return "语言";
                }
                if(key.equals("repository")){
                    return "存储库路径";
                }
            }
        }
        if(args.size()==2){

        }
        if(args.size()>2){
            return CommandMessages.translate("cis3");
        }
        return getEmptyString();
    }

    public List<String> startWith(List<String> args,List<String> cs){
        List<String> ns=cs.stream().filter(v->v.startsWith(args.get(args.size()-1))).collect(Collectors.toList());
        if(ns.size()>0){
            return ns;
        }
        return cs;
    }
}