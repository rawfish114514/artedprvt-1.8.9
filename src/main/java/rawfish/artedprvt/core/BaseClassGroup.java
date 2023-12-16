package rawfish.artedprvt.core;

import rawfish.artedprvt.api.Solvable;

import java.util.*;
@Solvable
public class BaseClassGroup implements ClassGroup{
    private final String name;
    private final Set<Class> set;

    //super
    @Solvable
    public BaseClassGroup(Object name){
        this.name=String.valueOf(name);
        set=new HashSet<>();
    }

    @Solvable
    //operate
    protected void add(Class clas){
        set.add(clas);
    }

    @Solvable
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
