package com.artedprvt.core.app.script;

import com.artedprvt.core.app.AppProcess;
import com.artedprvt.core.app.script.engine.ScriptEngine;
import com.artedprvt.core.app.script.engine.ScriptStackParser;
import com.artedprvt.core.app.script.rhino.RhinoStackParser;
import com.artedprvt.core.app.script.struct.AspFileLoader;
import com.artedprvt.core.app.script.struct.FileLoader;
import com.artedprvt.core.app.script.struct.ScriptLoader;
import com.artedprvt.core.AbstractThread;
import com.artedprvt.core.CoreInitializer;
import com.artedprvt.core.InProcess;
import com.artedprvt.core.Logger;
import com.artedprvt.core.app.AppLogger;
import com.artedprvt.core.app.script.rhino.RhinoEngine;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 进程
 */
public class ScriptProcess extends AppProcess<ScriptProcess> {
    private FileLoader fileLoader;//文件加载器
    private ScriptLoader scriptLoader;//脚本加载器
    private MetaData metadata;//脚本配置
    private AppLogger appLogger;//脚本日志记录器
    private List<ScriptEngine> engines;//引擎列表
    private List<ScriptStackParser> stackParsers;//脚本堆栈解析器
    private ScriptExceptionHandler exceptionHandler;
    private ScriptSystem scriptSystem;//脚本系统
    private int inProcessCount;//InProcess对象创建数


    /**
     * 从输入流创建进程
     * 创建asp文件加载器
     *
     * @param inputStream
     * @param scriptArgument
     * @throws Exception
     */
    public ScriptProcess(
            InputStream inputStream,
            List<String> scriptArgument) throws Exception {
        this(new AspFileLoader(inputStream), scriptArgument);
    }

    /**
     * 指定主模块
     *
     * @param module
     * @param fileLoader
     * @param scriptArgument
     * @throws Exception
     */
    public ScriptProcess(String module, FileLoader fileLoader, List<String> scriptArgument) throws Exception {
        this(fileLoader, scriptArgument);
        metadata.setModule(module);
        metadata.setName(module);
        MetaData.inspect(metadata);
        name = metadata.getName();
    }

    public ScriptProcess(FileLoader fileLoader, List<String> scriptArgument) throws Exception {
        super(scriptArgument);

        this.fileLoader = fileLoader;
        scriptLoader = new ScriptLoader(fileLoader);

        String aarinfo = fileLoader.getContent("info.toml");
        metadata = MetaData.parse(aarinfo);
        MetaData.inspect(metadata);

        name = metadata.getName();
        icon = loadIcon(fileLoader.getInputStream("icon.png"));

        appLogger = CoreInitializer.getLogFileController().openLog(this);


        engines = new ArrayList<>();
        engines.add(new RhinoEngine(this));

        stackParsers = new ArrayList<>();
        stackParsers.add(new RhinoStackParser());

        inProcessCount = 0;
    }


    private BufferedImage loadIcon(InputStream stream) {
        if (stream == null) {
            return loadDefaultIcon();
        }
        try {
            BufferedImage image = ImageIO.read(stream);
            if (image == null || image.getHeight() != 16 || image.getWidth() != 16) {
            } else {
                return image;
            }
        } catch (IOException ignored) {
        }
        return loadDefaultIcon();
    }


    @Override
    public Logger logger() {
        return appLogger;
    }

    @Override
    public void start() {
        super.start();
        mainThread.setName("Main");
    }


    /**
     * 准备工作完成
     * 由主线程调用
     */
    public void begin() {
        super.begin();
        initTime();
        printStart();
    }

    /**
     * 进程结束
     * 由外部调用或运行结束自动调用
     *
     * @param exitCode
     */
    @Override
    public synchronized void end(int exitCode) {
        super.end(exitCode);
        printEnd(exitCode, runningTime());
        appLogger.close();
    }

    private void printStart() {
        String s = "§3run:§r " + name;
        appLogger.natives(s);
    }

    private void printEnd(int status, long runtime) {
        String s;
        sw:
        {
            if (status == EXIT) {
                s = "§2end:§r " + name + "§7(" + runtime + "ms)";
                break sw;
            }
            if (status == ERROR) {
                s = "§4break:§r " + name + "§7(" + runtime + "ms)";
                break sw;
            }
            if (status == STOPS) {
                s = "§4stop:§r " + name + "§7(" + runtime + "ms)";
                break sw;
            }
            if (status >= 0) {
                s = "§2exit:§r " + name + "§7(" + runtime + "ms) " + status;
            } else {
                s = "§4exit:§r " + name + "§7(" + runtime + "ms) " + status;
            }
        }
        appLogger.natives(s);
    }

    @Override
    public void up(InProcess inProcessObject) {
        super.up(inProcessObject);
        inProcessCount++;
    }

    public String getStatistics() {
        long time = runningTime();

        String line1 = "[";
        for (String arg : args) {
            line1 += arg;
            line1 += "§7, §r";
        }
        if (line1.length() > 1) {
            line1 = line1.substring(0, line1.length() - 6);
        }
        line1 += "]";
        String line2 = "ret: " + ret;
        String line3 = "object: " + inProcessCount;
        String stime = String.valueOf(time);
        if (stime.length() > 3) {
            stime = stime.substring(0, stime.length() - 3) + "§7" + stime.substring(stime.length() - 3);
        } else {
            stime = "§7" + stime;
        }
        String line4 = "runtime: " + stime;

        String str = "";
        if (!line1.equals("[]")) {
            str += line1 + "\n";
        }
        str += line2 + "\n";
        str += line3 + "\n";
        str += line4;
        return str;
    }

    public FileLoader getFileLoader() {
        return fileLoader;
    }

    public ScriptLoader getScriptLoader() {
        return scriptLoader;
    }

    public MetaData getScriptInfo() {
        return metadata;
    }

    public List<ScriptEngine> getEngines() {
        return engines;
    }

    public List<ScriptStackParser> getStackParsers() {
        return stackParsers;
    }

    public ScriptSystem getScriptSystem() {
        return scriptSystem;
    }

    public void setScriptSystem(ScriptSystem scriptSystem) {
        this.scriptSystem = scriptSystem;
    }

    @Override
    public ScriptExceptionHandler getExceptionHandler() {
        if (exceptionHandler == null) {
            exceptionHandler = new ScriptExceptionHandler(this);
        }
        return exceptionHandler;
    }

    @Override
    public ScriptThread createThread(Runnable runnable) {
        ScriptThread scriptThread = new ScriptThread(this, runnable);
        threads.add(scriptThread);
        return scriptThread;
    }

    @Override
    public void run() {
        //先设置开始时间 begin有可能无法被执行
        initTime();
        setScriptSystem(new ScriptSystem(this));
        List<ScriptEngine> engines = getEngines();
        //初始化脚本引擎
        for (int i = 0; i < engines.size(); i++) {
            engines.get(i).init();
        }
        //加载模块
        getScriptLoader().loadAll();
        //准备完成
        begin();
        //执行主模块
        getScriptSystem().importModule(getScriptInfo().getModule());


        //脚本线程
        try {
            List<AbstractThread<ScriptProcess>> threads = getThreads();
            AbstractThread<ScriptProcess> t;
            for (int i = 0; i < threads.size(); i++) {
                t = threads.get(i);
                if (t != mainThread) {
                    t.join();
                }
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        end(ScriptProcess.EXIT);
    }
}
