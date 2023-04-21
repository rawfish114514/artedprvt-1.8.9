package rawfish.artedprvt.script;

import org.mozilla.javascript.Context;
import rawfish.artedprvt.script.mi.LifeDepend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

/**
 * 主线程
 */
public class MainThread extends Thread{
    protected ScriptProcess pro;

    protected List<LifeDepend> lo;
    public MainThread(ScriptProcess proIn){
        super();
        pro=proIn;
        setName("Main");

        setUncaughtExceptionHandler(new ScriptExceptionHandler(pro));
        if(pro.getValuePm()){
            setPriority(Thread.MAX_PRIORITY);
        }

        lo=new ArrayList<>();
    }
    public void addld(LifeDepend ld){
        lo.add(ld);
    }
    public void endld(){
        for(LifeDepend l:lo){
            l.end();
        }
    }
    @Override
    public void run(){
        pro.rhino = Context.enter();
        pro.rhino.unseal();
        pro.rhino.setOptimizationLevel(-1);
        pro.rhino.setLocale(Locale.ENGLISH);

        pro.props=ScriptProperties.props();
        pro.sys=new ScriptSystem(pro,pro.sender);
        pro.client=new ScriptClient();
        pro.port=new PortClass(pro);

        pro.env=new HashMap<>();
        ScriptUnit main=new ScriptUnit(pro,pro.readScript(pro.pack),pro.pack);
        pro.env.put(main.pack,main);

        pro.begin();

        main.run("<artedprvt>");


        //脚本线程
        try {
            for(int i=0;i<pro.tl.size();i++) {
                pro.tl.get(i).join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        endld();
        pro.end(1);
        Context.exit();
    }

    /**
     * 中断所有脚本线程和主线程
     */
    public void jstop(ScriptThread st,int exitstatus){
        //st是终止源线程 要最后一个终止
        if(st==null) {
            //主线程
            for (int i = 0; i < pro.tl.size(); i++) {
                pro.tl.get(i).stop();
            }
            endld();
            pro.end(exitstatus);
            stop();
        }else{
            //脚本线程
            ScriptThread t;
            for (int i = 0; i < pro.tl.size(); i++) {
                t=pro.tl.get(i);
                if(!t.equals(st)){
                    t.stop();
                }
            }
            endld();
            pro.end(exitstatus);
            stop();
            st.stop();
        }
    }
    public ScriptProcess getProcess(){
        return pro;
    }
}
