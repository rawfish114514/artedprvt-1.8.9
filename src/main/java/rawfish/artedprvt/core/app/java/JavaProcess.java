package rawfish.artedprvt.core.app.java;

import com.electronwill.toml.Toml;
import rawfish.artedprvt.core.AbstractExceptionHandler;
import rawfish.artedprvt.core.AbstractThread;
import rawfish.artedprvt.core.app.AppProcess;
import rawfish.artedprvt.core.Logger;
import rawfish.artedprvt.core.Logger.VoidLogger;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.List;
import java.util.Map;

public class JavaProcess extends AppProcess<JavaProcess> {
    URLClassLoader classLoader;

    public JavaProcess(String path, List<String> args) {
        super(args);
        try {
            classLoader = new URLClassLoader(new URL[]{new URL("file:///" + path)}, JavaProcess.class.getClassLoader());
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Logger logger() {
        return new VoidLogger();
    }

    @Override
    public AbstractExceptionHandler<JavaProcess> getExceptionHandler() {
        return null;
    }

    @Override
    public AbstractThread<JavaProcess> createThread(Runnable runnable) {
        return new AbstractThread<>(this, runnable);
    }


    @Override
    public void end(int exitCode) {
        try {
            classLoader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        classLoader = null;
        super.end(exitCode);
    }

    @Override
    public void run() {
        try {
            URL url = classLoader.findResource("info.toml");
            InputStream inputStream = url.openStream();

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
