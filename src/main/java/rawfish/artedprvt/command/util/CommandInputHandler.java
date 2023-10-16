package rawfish.artedprvt.command.util;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.FormatHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CommandInputHandler {
    private static Map<String, Command> commandMap= new HashMap<String,Command>();

    public static void register(Command command){
        commandMap.put(command.getName(),command);
    }


    public static HandleResult handleFormat(String input, int pos){
        List<String> spaces=new ArrayList<>();
        List<String> items=new ArrayList<>();
        char[] chars=input.toCharArray();
        StringBuilder sb=new StringBuilder();
        boolean isSpace=true;
        for(char c:chars){
            if(isSpace==(c==' ')){
                sb.append(c);
            }else{
                if(isSpace){
                    spaces.add(sb.toString());
                }else{
                    items.add(sb.toString());
                }
                isSpace=!isSpace;
                sb=new StringBuilder();
                sb.append(c);
            }

        }
        if(isSpace){
            spaces.add(sb.toString());
        }else{
            items.add(sb.toString());
        }
        if(items.size()<spaces.size()){
            items.add("");
        }

        boolean isx=false;
        String i0=items.get(0);
        if(i0.length()>0&&i0.charAt(0)=='/'){
            i0=i0.substring(1);
            isx=true;
        }
        Command command=commandMap.get(i0);
        if(command==null){
            return new HandleResult();
        }
        List<String> args=new ArrayList<>();
        for(int i=1;i<items.size();i++){
            args.add(items.get(i));
        }


        List<String> fargs=new ArrayList<>(args);
        String s0;
        if (isx) {
            s0="§c/§b"+command.getName()+"§r";
        }else{
            s0="§b"+command.getName()+"§r";
        }


        if(args.size()<1){
            String handledText=spaces.get(0)+s0;
            return new HandleResult(handledText);
        }

        List<? extends FormatHandler> formats=command.format(fargs);
        for(int i=0;i<formats.size()&&i<args.size();i++){
            fargs.set(i,formats.get(i).handleFormat(args.get(i))+"§r");
        }

        fargs.add(0,s0);
        sb=new StringBuilder();
        for(int i=0;i<fargs.size();i++){
            sb.append(spaces.get(i));
            sb.append(fargs.get(i));
        }
        String handledText=sb.toString();


        return new HandleResult(handledText);
    }

    public static HandleResult handleInfo(String input, int pos){
        List<String> spaces=new ArrayList<>();
        List<String> items=new ArrayList<>();
        String t1=input.substring(0,pos);
        String t2=input.substring(pos);
        int t2si;
        if((t2si=t2.indexOf(" "))!=-1){
            t2=t2.substring(0,t2si);
        }
        char[] chars=(t1+t2.trim()).toCharArray();
        StringBuilder sb=new StringBuilder();
        boolean isSpace=true;
        for(char c:chars){
            if(isSpace==(c==' ')){
                sb.append(c);
            }else{
                if(isSpace){
                    spaces.add(sb.toString());
                }else{
                    items.add(sb.toString());
                }
                isSpace=!isSpace;
                sb=new StringBuilder();
                sb.append(c);
            }

        }
        if(isSpace){
            spaces.add(sb.toString());
        }else{
            items.add(sb.toString());
        }
        if(items.size()<spaces.size()){
            items.add("");
        }

        int sp0=0;
        for(int i=0;i<spaces.size();i++){
            sp0+=spaces.get(i).length();
        }
        for(int i=0;i<items.size()-1;i++){
            sp0+=items.get(i).length();
        }


        String i0=items.get(0);
        if(i0.length()>0&&i0.charAt(0)=='/'){
            i0=i0.substring(1);
        }
        Command command=commandMap.get(i0);
        if(command==null){
            return new HandleResult();
        }
        List<String> args=new ArrayList<>();
        for(int i=1;i<items.size();i++){
            args.add(items.get(i));
        }
        if(args.size()<1){
            return new HandleResult();
        }

        String infoText=command.info(args).handleInfo(args.get(args.size()-1));


        return new HandleResult(infoText,sp0);
    }

}
