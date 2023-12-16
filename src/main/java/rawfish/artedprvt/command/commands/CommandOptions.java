package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.CommandMessages;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.core.UserOptions;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;

import java.util.*;

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
            Map<String,Object> config=new HashMap<>(UserOptions.options);
            config.forEach((k,v)-> sb.append("\n  ").append(k).append(" = ").append(String.valueOf(v)));
            CommandMessages.key(getName(), CMS.cms28,sb.toString());
            return;
        }
        if(args.size()==1){
            //展示选项
            String key=args.get(0);
            if(key.equals("load")){
                //加载
                UserOptions.load();
                CommandMessages.key(getName(),CMS.cms27);
                return;
            }
            Object value= UserOptions.getValue(key);
            if(value==null){
                CommandMessages.exception(getName(),CMS.cms25,key);
            }else{
                CommandMessages.key(getName(),CMS.cms26,key,value);
            }
            return;
        }
        if(args.size()==2){
            //修改选项
            String key=args.get(0);
            String value=args.get(1);
            Object v= UserOptions.getValue(key);
            if(v==null){
                CommandMessages.exception(getName(),CMS.cms25,key);
            }else{
                UserOptions.setValue(key,value);
                try {
                    UserOptions.writeToFile();
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                CommandMessages.key(getName(),CMS.cms26,key,value);
            }
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1) {
            List<String> list= new ArrayList<>(UserOptions.options.keySet());
            list.add("load");
            return list;
        }else if(args.size()==2) {
            List<String> vs= UserOptions.getValues(args.get(0));
            if(vs==null){
                return Literals.emptyComplete();
            }
            return new ArrayList<>(vs);
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        if(args.size()==0){
            return Literals.emptyFormat();
        }
        if(args.get(0).equals("load")){
            return Literals.formatListBuilder().append("6");
        }
        if(args.size()==1){
            return Literals.formatListBuilder().append("d");
        }
        return Literals.formatListBuilder().append("d").append("a");
    }

    public InfoHandler info(List<String> args){
        if(args.size()==1){
            String key=args.get(0);
            if(key.isEmpty()){
                return Literals.infoFactory().string(CIS.cis8);
            }
            if(key.equals("load")){
                return Literals.infoFactory().string(CIS.cis11);
            }
            Object value= UserOptions.getValue(key);
            if(value==null){
                return Literals.infoFactory().string(CIS.cis9);
            }else{
                /*key info*/
                if(key.equals("language")){
                    return Literals.infoFactory().string("语言");
                }
                if(key.equals("repository")){
                    return Literals.infoFactory().string("存储库路径");
                }
            }
        }
        if(args.size()==2){

        }
        if(args.size()>2){
            return Literals.infoFactory().string(CIS.cis3);
        }
        return Literals.emptyInfo();
    }
}
