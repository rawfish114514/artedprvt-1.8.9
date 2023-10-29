package rawfish.artedprvt.mi;

import rawfish.artedprvt.core.script.ScriptObject;
import rawfish.artedprvt.core.script.ScriptProcess;
import rawfish.artedprvt.core.script.ScriptThread;

import java.util.ArrayList;
import java.util.List;

public class ParticleService implements ScriptObject {
    public ScriptProcess scriptProcess;
    public ScriptThread scriptThread;

    public List<Particle> particleList;
    public List<ParticleServiceHandler> handlerList;
    public double exit=-1;
    public double tn=10;//每刻毫秒数
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

    public void setExit(double n){
        exit=n;
    }

    public double getExit(){
        return exit;
    }

    public void setTn(double tn){
        this.tn=tn;
    }

    public double getTn(){
        return tn;
    }

    public void setTime(double time){
        setExit(time/tn);
    }

    public double getTime(){
        return getExit()*tn;
    }

    public void run() {
        try {
            long at = time();
            int n = 0;
            Particle particle;
            while (!(n >= exit) || exit <0) {
                for (int i = 0; i < particleList.size(); i++) {
                    particle = particleList.get(i);
                    if (particle != null && !particle.isDead) {
                        particle.motionUpdate();
                    } else {
                        particleList.remove(i);
                        i--;
                    }
                }
                for (int i = 0; i < handlerList.size(); i++) {
                    if (handlerList.get(i).handle(n, this)) {
                        handlerList.remove(i--);
                    }
                }
                long t0 = time();
                at += tn;
                long td = at - t0;
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
