package rawfish.artedprvt.core.script;

import java.util.*;

public class BaseClassGroup implements ClassGroup{
    private final String name;
    private final Set<Class> set;

    //super
    public BaseClassGroup(Object name){
        this.name=String.valueOf(name);
        set=new HashSet<>();
    }

    //operate
    protected void add(Class clas){
        set.add(clas);
    }

    //operate
    protected void union(BaseClassGroup target){
        if(target.permission()){
            set.addAll(target.getClasses());
        }
    }



    @Override
    public Set<Class> getClasses() {
        return Collections.unmodifiableSet(set);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public boolean permission() {
        return true;
    }
}
