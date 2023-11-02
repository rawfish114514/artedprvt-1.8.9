package rawfish.artedprvt.core;

import java.util.function.Function;

public class WorkSpace {
    private String name;
    private String dir;

    public WorkSpace(String name,String dir){
        this.name=name.replaceAll("\\s","");
        this.dir=dir;
    }

    public String getName(){
        return name;
    }

    public String getDir(){
        return dir;
    }

    public String toDerivation(String path){
        return getDir()+path;
    }

    public String toDerivation(Function<String[],String> f,String... args){
        return getDir()+f.apply(args);
    }

    public static String name(){
        return currentWorkSpace().getName();
    }

    public static String dir(){
        return currentWorkSpace().getDir();
    }

    public static String derivation(String path){
        return currentWorkSpace().toDerivation(path);
    }

    public static String derivation(Function<String[],String> f,String... args){
        return currentWorkSpace().toDerivation(f,args);
    }



    public static final Function<String[],String> LIB=strings -> "/lib";

    public static final Function<String[],String> SRC=strings -> "/src";


    public static void init(){
        defaultWorkSpace=new WorkSpace(
                "default_workspace",
                System.getProperties().get("user.dir").toString()+"/artedprvt"
        );
        workSpace=defaultWorkSpace;
    }
    private static WorkSpace defaultWorkSpace;
    public static WorkSpace workSpace;
    public static WorkSpace currentWorkSpace(){
        return workSpace;
    }

    public static WorkSpace defaultWorkSpace(){
        return defaultWorkSpace;
    }
}
