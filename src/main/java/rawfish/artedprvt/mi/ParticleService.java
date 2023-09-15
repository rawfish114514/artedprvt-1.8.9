package rawfish.artedprvt.mi;

import rawfish.artedprvt.core.ScriptObject;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.core.ScriptThread;

import java.util.ArrayList;
import java.util.List;

public class ParticleService implements ScriptObject {
    public ScriptProcess scriptProcess;
    public ScriptThread scriptThread;

    public List<Particle> particleList;
    public List<ParticleServiceHandler> handlerList;
    public int exit=-1;
    public int tn=10;//每刻毫秒数
    public ParticleService(){
        scriptProcess=up();
        if(scriptProcess==null){
            throw new RuntimeException("null process");
        }
        scriptThread=new ScriptThread(scriptProcess,this::run);

        particleList=new ArrayList<>();
        handlerList =new ArrayList<>();
    }

    public void add(Particle particle){
        particleList.add(particle);
    }

    public void addHandler(ParticleServiceHandler process){
        handlerList.add(process);
    }

    public void setExit(int n){
        exit=n;
    }

    public int getExit(){
        return exit;
    }

    public void setTn(int tn){
        this.tn=tn;
    }

    public int getTn(){
        return tn;
    }

    public void setTime(int time){
        setExit(time/tn);
    }

    public int getTime(){
        return getExit()*tn;
    }

    public void run() {
        try {
            long t = time();
            long at = t;
            int n = 0;
            Particle particle;
            while (true) {
                if(n>=exit&&exit!=-1){
                    break;
                }
                for (int i = 0; i < particleList.size(); i++) {
                    particle = particleList.get(i);
                    if (particle != null && !particle.isDead) {
                        particle.motionUpdate();
                    } else {
                        particleList.remove(i);
                    }
                }
                for (int i = 0; i < handlerList.size(); i++) {
                    handlerList.get(i).handle(n, this);
                }
                long t0 = time();
                at += tn;
                long td = at-t0;
                if (td < 0) {
                    td = 0;
                }
                sleep(td);
                n++;
            }
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public long time(){
        return System.currentTimeMillis();
    }

    public void sleep(long time) throws InterruptedException {
        Thread.sleep(time);
    }

    public void start(){
        scriptThread.start();
    }

    @Override
    public void onClose() {
        scriptProcess=null;
        scriptThread=null;
    }
}