package com.artedprvt.core;

import com.artedprvt.Artedprvt;
import com.artedprvt.api.Solvable;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Solvable
public abstract class Process {
    /**
     * 进程状态
     * <p>
     * init()   ->  CREATE
     * start()  ->  START
     * BEGIN
     * stop()   ->  STOP
     * end()    ->  END
     */
    protected int ret;

    /**
     * 进程id
     */
    protected final int pid;

    /**
     * 进程名称
     */
    protected String name;

    /**
     * 开始运行时间戳
     */
    protected long startTime;

    /**
     * 结束运行时间戳
     */
    protected long endTime;

    /**
     * 出错了
     */
    protected boolean hasError;

    /**
     * 图标
     */
    protected BufferedImage icon;

    public Process() {
        ret = CREATE;
        pid = register();
        if (pid == -1) {
            throw new RuntimeException("注册进程失败");
        }
        name = "Process";
        startTime = 0;
        endTime = 0;
        hasError = false;
        icon = loadDefaultIcon();
    }

    protected int register() {
        return CoreInitializer.getProcessController().registerProcess(this, pidLevel());
    }

    public abstract ProcessIdLevel pidLevel();

    @Solvable
    public abstract Logger logger();

    /**
     * 启动进程
     * 外部调用
     */
    public abstract void start();

    /**
     * 终止进程
     * 内部或外部调用
     *
     * @param exitCode
     */
    public abstract void stop(int exitCode);

    /**
     * 结束进程
     * 内部或外部调用
     *
     * @param exitCode
     */
    public abstract void end(int exitCode);

    public abstract void up(InProcess inProcessObject);

    public abstract void down(InProcess inProcessObject);

    /**
     * 获取进程状态
     *
     * @return
     */
    public int getRet() {
        return ret;
    }

    /**
     * 获取进程id
     *
     * @return
     */
    public int getPid() {
        return pid;
    }

    ;

    /**
     * 获取进程名称
     *
     * @return
     */
    public String getName() {
        return name;
    }

    ;


    /**
     * 初始化时间
     */
    public void initTime() {
        startTime = System.currentTimeMillis();
        endTime = -1;
    }

    /**
     * 获取开始时间戳
     *
     * @return
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * 获取结束时间戳
     *
     * @return
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * 获取运行时长
     *
     * @return
     */
    public long runningTime() {
        if (endTime == -1) {
            return System.currentTimeMillis() - startTime;
        }
        return endTime - startTime;
    }

    /**
     * 出错
     */
    public void hasError() {
        hasError = true;
    }

    /**
     * 获取是否出错
     *
     * @return
     */
    public boolean isError() {
        return hasError;
    }

    /**
     * 获取图标
     *
     * @return
     */
    public BufferedImage getIcon() {
        return icon;
    }

    /**
     * 默认图标
     * 加载静态资源
     *
     * @return
     */
    public BufferedImage loadDefaultIcon() {
        InputStream stream = Artedprvt.class.getResourceAsStream("/icon_default.png");
        try {
            BufferedImage image = ImageIO.read(stream);
            return image;
        } catch (IOException ignore) {
        }
        throw new RuntimeException("图标加载失败");
    }


    public int getRunningThreadCount() {
        return ret == END ? 0 : 1;
    }


    /**
     * ret 状态 只能从低到高
     */

    /**
     * 创建
     * 进程对象被创建时的状态
     */
    public static final int CREATE = 0;
    /**
     * 开始
     * 进程准备运行时的状态
     */
    public static final int START = 1;
    /**
     * 开始
     * 进程运行准备完成时的状态 此时进程已经开始运行
     */
    public static final int BEGIN = 2;
    /**
     * 终止
     * 开始终止进程运行
     */
    public static final int STOP = 3;
    /**
     * 结束
     * 进程运行结束
     */
    public static final int END = 7;

    /**
     * 退出代码
     */

    public static final int EXIT = 0;
    public static final int ERROR = -1;
    public static final int STOPS = -2;

    public static class ProcessIdLevel {
        public final int START;
        public final int END;

        public ProcessIdLevel(int START, int END) {
            this.START = START;
            this.END = END;
        }

        public boolean contain(ProcessIdLevel target) {
            return START <= target.START && END >= target.END;
        }
    }
}
