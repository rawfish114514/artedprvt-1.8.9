package rawfish.artedprvt.std.cli;

import org.apache.commons.lang3.StringUtils;
import rawfish.artedprvt.std.cli.util.HandleResult;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.std.minecraft.chat.ChatConsole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CommandHandler {
    private static Map<String, Command> commandMap=new HashMap<>();
    private static List<String> eternalList=new ArrayList<>();

    public static Map<String,Command> getCommandMap(){
        return Collections.unmodifiableMap(commandMap);
    }

    public static boolean register(Command command,Object eternal){
        synchronized (CommandHandler.class) {
            if (register(command)) {
                eternalList.add(command.getName());
                return true;
            }
            return false;
        }
    }

    public static boolean register(Command command){
        synchronized (CommandHandler.class) {
            String name = command.getName();
            if(name.isEmpty()){
                return false;
            }
            if (commandMap.get(name) == null) {
                commandMap.put(name, command);
                return true;
            }
            return false;
        }
    }

    public static boolean unregister(Command command){
        return unregister(command.getName());
    }

    public static boolean unregister(String name){
        synchronized (CommandHandler.class) {
            if (commandMap.get(name) != null&&!eternalList.contains(name)) {
                commandMap.remove(name);
                return true;
            }
            return false;
        }
    }

    public static boolean executeCommand(String input){
        input = StringUtils.normalizeSpace(input);

        if (input.startsWith("/")) {
            input = input.substring(1);
        }

        String[] temp = input.split(" ");
        String[] args = new String[temp.length - 1];
        String commandName = temp[0];
        System.arraycopy(temp, 1, args, 0, args.length);

        Command command=commandMap.get(commandName);
        if(command==null){
            return false;
        }

        try{
            command.process(Arrays.asList(args), new ChatConsole());
        }catch (Throwable throwable){
            throwable.printStackTrace(System.err);
        }

        return true;
    }

    public static List<String> handleComplete(String input, int pos){
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
        Command command= commandMap.get(i0);
        if(command==null){
            return null;
        }
        List<String> args=new ArrayList<>();
        for(int i=1;i<items.size();i++){
            args.add(items.get(i));
        }
        if(args.size()<1){
            return null;
        }

        try{
            return startWith(args,command.complete(args));
        }catch (Throwable throwable){
            throwable.printStackTrace(System.err);
            return Literals.emptyComplete();
        }
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
        Command command= commandMap.get(i0);
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

        List<? extends FormatHandler> formats;
        try{
            formats=command.format(fargs);
        }catch (Throwable throwable){
            throwable.printStackTrace(System.err);
            return new HandleResult();
        }
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
        Command command= commandMap.get(i0);
        if(command==null){
            return new HandleResult();
        }
        List<String> args=new ArrayList<>();
        for(int i=1;i<items.size();i++){
            args.add(items.get(i));
        }

        String lastArg;
        if(args.size()<1){
            lastArg=i0;
        }else{
            lastArg=args.get(args.size()-1);
        }

        String infoText;
        try{
            infoText=command.info(args).handleInfo(lastArg);
        }catch (Throwable throwable){
            throwable.printStackTrace(System.err);
            return new HandleResult();
        }


        return new HandleResult(infoText,sp0);
    }

    public static synchronized void reset() {
        commandMap.values().forEach((Command::reset));
    }

    public static List<String> startWith(List<String> args,List<String> cs){
        List<String> ns=cs.stream().filter(v->v.startsWith(args.get(args.size()-1))).collect(Collectors.toList());
        if(ns.size()>0){
            return ns;
        }
        return cs;
    }

}
