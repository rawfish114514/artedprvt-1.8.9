package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.FrameConfig;

import java.util.*;

/**
 * 修改和更新模组配置
 */
public class CommandConfig extends Command {
    public CommandConfig(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()==0){
            //展示所有配置
            StringBuilder sb=new StringBuilder();
            Map<String,Object> config=new HashMap<>(FrameConfig.config);
            config.forEach((k,v)-> sb.append("\n  ").append(k).append(" = ").append(String.valueOf(v)));
            CommandMessages.key(getName(),"cms28",sb.toString());
            return;
        }
        if(args.size()==1){
            //展示配置
            String key=args.get(0);
            if(key.equals("load")){
                //加载
                FrameConfig.load();
                CommandMessages.key(getName(),"cms27");
                try {
                    FrameConfig.updateFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return;
            }
            Object value=FrameConfig.getValue(key);
            if(value==null){
                CommandMessages.exception(getName(),"cms25",key);
            }else{
                CommandMessages.key(getName(),"cms26",key,value);
            }
            return;
        }
        if(args.size()==2){
            //修改配置
            String key=args.get(0);
            String value=args.get(1);
            Object v=FrameConfig.getValue(key);
            if(v==null){
                CommandMessages.exception(getName(),"cms25",key);
            }else{
                List<String> values=FrameConfig.getValues(key);
                if(values==null||!values.contains(value)){
                    CommandMessages.exception(getName(),"cms29",value);
                    return;
                }
                FrameConfig.setValue(key,value);
                try {
                    FrameConfig.updateFile();
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
            List<String> list= new ArrayList<>(FrameConfig.config.keySet());
            list.add("load");
            return list;
        }else if(args.size()>1) {
            List<String> vs=FrameConfig.getValues(args.get(0));
            if(vs==null){
                return getNullTab();
            }
            return new ArrayList<>(vs);
        }
        return getNullTab();
    }
}
