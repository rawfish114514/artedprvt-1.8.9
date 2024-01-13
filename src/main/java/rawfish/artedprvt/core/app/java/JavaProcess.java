package rawfish.artedprvt.core.app.java;

import com.electronwill.toml.Toml;
import rawfish.artedprvt.core.AbstractThread;
import rawfish.artedprvt.core.CoreInitializer;
import rawfish.artedprvt.core.Logger;
import rawfish.artedprvt.core.app.AppLogger;
import rawfish.artedprvt.core.app.AppProcess;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public class JavaProcess extends AppProcess<JavaProcess> {
    private ClassLoader classLoader;
    private AppLogger appLogger;
    private JavaExceptionHandler exceptionHandler;

    public JavaProcess(InputStream inputStream, List<String> args) throws Exception {
        super(args);
        classLoader = new JarClassLoader(inputStream);

        appLogger = CoreInitializer.getLogFileController().openLog(this);
    }

    @Override
    public Logger logger() {
        return appLogger;
    }

    @Override
    public JavaExceptionHandler getExceptionHandler() {
        if (exceptionHandler == null) {
            exceptionHandler = new JavaExceptionHandler(this);
        }
        return exceptionHandler;
    }

    @Override
    public AbstractThread<JavaProcess> createThread(Runnable runnable) {
        AbstractThread<JavaProcess> javaThread = new AbstractThread<>(this, runnable);
        threads.add(javaThread);
        return javaThread;
    }


    @Override
    public void end(int exitCode) {
        super.end(exitCode);
        classLoader = null;
        appLogger.close();
    }

    @Override
    public void run() {
        begin();
        try (InputStream inputStream = classLoader.getResourceAsStream("info.toml")) {

            Map<String, Object> info = Toml.read(inputStream);
            String main = info.get("main").toString();

            Class mainClass = classLoader.loadClass(main);
            if (JavaAppMain.class.isAssignableFrom(mainClass)) {
                JavaAppMain javaAppMain = (JavaAppMain) mainClass.newInstance();
                javaAppMain.run();
            } else {
                throw new RuntimeException("主类异常");
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            end(0);
        }
    }

}
