package rawfish.artedprvt.core;

import rawfish.artedprvt.api.Solvable;

@Solvable
public abstract class Logger {
    @rawfish.artedprvt.dev.FutureWork("注册this到日志记录器控制进程")
    protected Logger(){

    }

    /**
     * 记录INFO级别的日志消息
     * @param message
     */
    @Solvable
    public abstract void info(String message);

    /**
     * 记录WARN级别的日志消息
     * @param message
     */
    @Solvable
    public abstract void warn(String message);

    /**
     * 记录ERROR级别的日志消息
     * @param message
     */
    @Solvable
    public abstract void error(String message);

    /**
     * 记录日志消息
     * @param level 日志消息级别
     * @param message
     */
    @Solvable
    public void log(Level level,String message){
        switch (level){
            case INFO:
                info(message);
                break;
            case WARN:
                warn(message);
                break;
            case ERROR:
                error(message);
                break;
            default:
        }
    }

    /**
     * 逻辑刻 由日志记录器控制进程调用 时间是不确定的
     */
    @rawfish.artedprvt.dev.FutureWork("逻辑刻")
    public void logicTick(){

    }

    /**
     * 关闭日志记录器
     */
    @rawfish.artedprvt.dev.FutureWork("注销")
    public void close(){
        //注销
    }

    /**
     * 日志消息级别
     */
    @Solvable
    enum Level{
        @Solvable
        INFO,
        @Solvable
        WARN,
        @Solvable
        ERROR,
    }

    static class VoidLogger extends Logger{
        public static final VoidLogger INSTANCE= new VoidLogger();

        public VoidLogger(){
            super();
        }

        @Override
        public void info(String message) {

        }

        @Override
        public void warn(String message) {

        }

        @Override
        public void error(String message) {

        }
    }

}

