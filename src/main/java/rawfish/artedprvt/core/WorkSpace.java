package rawfish.artedprvt.core;

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
}
