package rawfish.artedprvt.work;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 项目，在此类中持有一个静态的项目字段
 * <p>
 * 所有方法都应该同步
 */
public class Project {
    private String dir;
    private File target;
    private PrintWriter log;

    private WorkRuntime runtime;

    public Project(String dir) {
        this.dir = dir.replace(File.separatorChar, '/');
        target = new File(dir);
        if (!target.isDirectory()) {
            throw new RuntimeException("不存在或不是目录");
        }


        if (project == null) {
            project = this;
        } else {
            throw new RuntimeException("已存在打开的项目");
        }
    }

    public String getDir() {
        return dir;
    }

    public File getTarget() {
        return target;
    }

    /**
     * 初始化项目
     *
     * @param initializer
     */
    public void init(ProjectInitializer initializer) throws IOException {
        File[] files = target.listFiles();
        if (files.length == 0) {
            initializer.initialize(this);
        } else {
            throw new RuntimeException("非空目录不能初始化");
        }
    }


    /**
     * 编译 ap.java 并输出 ap.bytes
     */
    public void compile() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        throwLogNull();
        ProjectTool.compile(getSource(), getLibsIn(), getBytesOut(), log, null);
    }

    /**
     * 验证 ap.bytes 是否可用
     */
    public void verify() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        throwLogNull();
        ProjectTool.verify(getSource(), getBytesIn(), log, null);
    }

    /**
     * 加载ap.bytes
     */
    public void load() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        throwLogNull();
        if (runtime != null) {
            runtime.close();
            runtime = null;
        }
        runtime = ProjectTool.load(new ProjectSystem(this), getBytesIn(), log, null);
    }

    public void close() {
        closeLog();
        if (isLoaded()) {
            runtime.close();
        }
        if (project == this) {
            project = null;
        }
    }


    public void initLog() throws IOException {
        if (log != null) {
            log.close();
        }
        File logFile = new File(target, ".apf/log.txt");
        if (!logFile.isFile()) {
            logFile.getParentFile().mkdirs();
            logFile.createNewFile();
        }
        log = new PrintWriter(new OutputStreamWriter(new FileOutputStream(logFile), StandardCharsets.UTF_8));
        log.println(LocalDateTime.now().toString());
        log.println();
    }

    public void closeLog() {
        if (log != null) {
            log.println();
            log.println(LocalDateTime.now().toString());
            log.close();
        }
    }

    public void throwLogNull() {
        if (log == null) {
            throw new RuntimeException("log null");
        }
    }

    public boolean isInit() {
        File file = new File(target, "ap.java");
        return file.isFile();
    }

    public boolean isLoaded() {
        return runtime != null;
    }

    public WorkRuntime getRuntime() {
        return runtime;
    }


    public String getSource() throws IOException {
        File ap_java = new File(target, "ap.java");
        if (!ap_java.isFile()) {
            throw new RuntimeException("找不到ap.java文件");
        }
        Reader reader = new InputStreamReader(new FileInputStream(ap_java), StandardCharsets.UTF_8);

        int n;
        StringBuilder sb = new StringBuilder();
        while ((n = reader.read()) != -1) {
            sb.append((char) n);
        }

        return sb.toString();
    }

    public InputStream getBytesIn() throws FileNotFoundException {
        File ap_bytes = new File(target, ".apf/ap.bytes");
        if (!ap_bytes.isFile()) {
            throw new RuntimeException("找不到ap.bytes文件");
        }
        return new FileInputStream(ap_bytes);
    }

    public OutputStream getBytesOut() throws IOException {
        File ap_bytes = new File(target, ".apf/ap.bytes");
        if (!ap_bytes.isFile()) {
            ap_bytes.createNewFile();
        }
        return new FileOutputStream(ap_bytes);
    }

    public InputStream[] getLibsIn() throws FileNotFoundException {
        File ap_libs = new File(target, ".apf/libs");
        if (!ap_libs.isDirectory()) {
            return new InputStream[0];
        }
        File[] libs = ap_libs.listFiles();
        InputStream[] inputStreams = new InputStream[libs.length];

        for (int i = 0; i < libs.length; i++) {
            inputStreams[i] = new FileInputStream(libs[i]);
        }
        return inputStreams;
    }

    /* 单例和非阻塞同步 */

    public static Project project = null;
    public static final Lock lock = new ReentrantLock();
}
