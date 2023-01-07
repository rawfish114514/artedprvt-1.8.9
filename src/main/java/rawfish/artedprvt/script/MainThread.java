package rawfish.artedprvt.script;

import org.mozilla.javascript.Context;

import java.util.HashMap;
import java.util.Locale;

/**
 * 主线程
 */
public class MainThread extends Thread{
    protected ScriptProcess pro;
    public MainThread(ScriptProcess proIn){
        super();
        pro=proIn;
        setName("Main");

        setUncaughtExceptionHandler(new ScriptExceptionHandler(pro));
        if(pro.pm_value){
            setPriority(10);
        }
    }

    @Override
    public void run(){
        pro.rhino = Context.enter();
        pro.rhino.setOptimizationLevel(-1);
        pro.rhino.setLocale(Locale.ENGLISH);
        pro.sys=new ScriptSystem(pro,pro.sender);
        pro.port=new PortClass(pro);

        pro.env=new HashMap<>();
        ScriptUnit main=new ScriptUnit(pro,pro.readString(pro.pack),pro.pack);
        pro.env.put(main.pack,main);

        pro.begin();

        main.run("<artedprvt>");


        //等待线程
        try {
            for(int i=0;i<pro.tl.size();i++) {
                pro.tl.get(i).join();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }


        pro.end();

    }

    /**
     * 中断所有等待线程和主线程
     */
    public void jstop(ScriptThread st){
        //st是终止源线程 要最后一个终止
        if(st==null) {
            //主线程
            for (int i = 0; i < pro.tl.size(); i++) {
                pro.tl.get(i).stop();
            }
            pro.end();
            stop();
        }else{
            //等待线程
            ScriptThread t;
            for (int i = 0; i < pro.tl.size(); i++) {
                t=pro.tl.get(i);
                if(!t.equals(st)){
                    t.stop();
                }
            }
            pro.end();
            stop();
            st.stop();
        }
    }
}
