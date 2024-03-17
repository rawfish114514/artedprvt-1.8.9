package com.artedprvt.work;

import com.electronwill.toml.Toml;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
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
        initializer.initialize(this);
    }


    /**
     * 编译
     */
    public void compile() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        throwLogNull();
        ProjectTool.compile(getSourceMap(), getMainClass(), getBytesOut(), log, null);
    }

    /**
     * 验证
     */
    public void verify() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        throwLogNull();
        ProjectTool.verify(getSourceMap(), getBytesIn(), log, null);
    }

    /**
     * 加载
     */
    public void load() throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        throwLogNull();
        if (runtime != null) {
            runtime.close();
            runtime = null;
        }
        runtime = ProjectTool.load(getMainClass(), new ProjectSystem(this), getBytesIn(), log, null);
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
        File file = new File(target, ".apf");
        return file.isDirectory();
    }

    public boolean isLoaded() {
        return runtime != null;
    }

    public WorkRuntime getRuntime() {
        return runtime;
    }


    public Map<String, String> getSourceMap() throws IOException {
        Map<String, String> map = new HashMap<>();
        File java = new File(target, ".apf/script/java");
        Files.walkFileTree(java.toPath(), new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                String name = target.toPath().relativize(file).toString().replace(File.separatorChar, '/');
                map.put(name, new String(Files.readAllBytes(file), StandardCharsets.UTF_8));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });

        return map;
    }

    public InputStream getBytesIn() throws FileNotFoundException {
        File ap_bytes = new File(target, ".apf/.bytes");
        if (!ap_bytes.isFile()) {
            throw new RuntimeException("找不到.bytes文件");
        }
        return new FileInputStream(ap_bytes);
    }

    public OutputStream getBytesOut() throws IOException {
        File ap_bytes = new File(target, ".apf/.bytes");
        if (!ap_bytes.isFile()) {
            ap_bytes.createNewFile();
        }
        return new FileOutputStream(ap_bytes);
    }

    private String mainClass;

    public String getMainClass() throws IOException {
        if (mainClass == null) {
            File init = new File(target, ".apf/script/init.toml");
            byte[] bytes = Files.readAllBytes(init.toPath());
            Map<String, Object> map = Toml.read(new String(bytes, StandardCharsets.UTF_8));
            mainClass = map.get("main-class").toString();
        }
        return mainClass;
    }

    /* 单例和非阻塞同步 */

    public static Project project = null;
    public static final Lock lock = new ReentrantLock();
}
