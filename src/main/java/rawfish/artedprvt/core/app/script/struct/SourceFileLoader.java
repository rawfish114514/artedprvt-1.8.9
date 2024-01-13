package rawfish.artedprvt.core.app.script.struct;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * 源文件加载器
 */
public class SourceFileLoader implements FileLoader {
    public String dir;

    public SourceFileLoader(String dir) {
        this.dir = dir;
    }

    @Override
    public String getContent(String path) {
        File file = new File(dir + "/" + path);
        if (!file.isFile()) {
            return null;
        }
        Reader reader;
        StringBuilder sb;
        try {
            reader = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
            sb = new StringBuilder();
            while (true) {
                int n = reader.read();
                if (n == -1) {
                    break;
                }
                sb.append((char) n);
            }
            reader.close();

        } catch (IOException e) {
            return null;
        }
        return sb.toString();
    }

    @Override
    public InputStream getInputStream(String path) {
        File file = new File(dir + "/" + path);
        if (!file.isFile()) {
            return null;
        }
        try {
            return new FileInputStream(file);
        } catch (IOException e) {
            return null;
        }
    }

    @Override
    public boolean isExists(String path) {
        File file = new File(dir + "/" + path);
        return file.exists();
    }

    @Override
    public List<String> getPaths() {
        File directory = new File(dir + "/");
        Path path = directory.toPath();
        List<File> fileList = getAllFile(directory);
        List<String> stringList = new ArrayList<>();
        for (File file : fileList) {
            stringList.add(dediff(path.relativize(file.toPath()).toString()));
        }
        return stringList;
    }

    public List<File> getAllFile(File file) {
        List<File> fileList = new ArrayList<>();
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                fileList.addAll(getAllFile(f));
            } else {
                fileList.add(f);
            }
        }
        return fileList;
    }

    public String dediff(String s) {
        return s.replace("\\", "/");
    }
}
