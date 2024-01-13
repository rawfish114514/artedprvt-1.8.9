package rawfish.artedprvt.core;

import rawfish.artedprvt.api.Solvable;

@Solvable
public abstract class Logger {
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
     * 关闭日志记录器
     */
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

    public static class VoidLogger extends Logger{
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

