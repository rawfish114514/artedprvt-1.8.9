package rawfish.artedprvt.mi;

public interface ParticleServiceHandler {
    /**
     * 返回true表示可以关闭
     * @param n
     * @param service
     * @return
     */
    boolean handle(int n,ParticleService service);
}
