package rawfish.artedprvt.work;

import com.electronwill.toml.Toml;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigInteger;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 项目初始化器
 * 从zip文件输入流中读取
 * 包含了初始化器的所有信息
 * 因为仅ap.java源码无法提供所有有必要的信息
 * (依赖项，配置)
 */
public class ProjectInitializer {
    public static final Map<String, ProjectInitializer> initializerMap = new HashMap<String, ProjectInitializer>() {{
        try {
            put("test", new ProjectInitializer(new FileInputStream(new File("C:/Users/Administrator/Desktop/init.zip"))));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }};

    private Map<String, byte[]> map = new HashMap<String, byte[]>();

    public ProjectInitializer() {
    }

    /**
     * 创建项目初始化器
     *
     * @param input 数据源
     */
    public ProjectInitializer(InputStream input) throws Exception {
        ZipInputStream zip = new ZipInputStream(
                input,
                Charset.forName("cp437"));

        ZipEntry entry = null;
        while ((entry = zip.getNextEntry()) != null) {
            if (!entry.isDirectory()) {
                int n;
                ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                while ((n = zip.read()) != -1) {
                    outputStream.write(n);
                }
                map.put(entry.getName().replace(File.separator, "/"), outputStream.toByteArray());
            }
        }
        zip.close();

        check();
        load();
    }

    private String apjava;

    private List<String> libs;

    private String description;


    private List<String> moveing;

    /**
     * 检查
     * 要求必须存在ap.java和pi.toml
     * 而且在.apf文件夹中不能存在文件和文件夹
     * 但.apf/project文件夹是例外
     * 初始化之后这些文件直接放在项目目录中
     * .apf和ap.java在项目根目录中
     * <p>
     * 项目脚本也不允许操作ap.java文件和.apf文件夹除了.apf/project文件夹
     * <p>
     * pi.toml定义了依赖项的位置 依赖项被特殊处理
     */
    private void check() {
        for (String s : map.keySet()) {
            String[] c = s.split("/");
            if (c.length > 1) {
                if (c[0].equals(".apf")) {
                    if (!(c.length > 2 && c[1].equals("project"))) {
                        throw new RuntimeException("此位置不能有文件 " + s);
                    }
                }
            }
        }

        if (map.get("ap.java") == null) {
            throw new RuntimeException("找不到文件 ap.java");
        }
        if (map.get("pi.toml") == null) {
            throw new RuntimeException("找不到文件 pi.toml");
        }

        String pitoml = new String(map.get("pi.toml"), StandardCharsets.UTF_8);
        Map<String, Object> toml = Toml.read(pitoml);
        List<Object> dependList = (List) toml.get("depend");

        for (Object dependObject : dependList) {
            Map<String, Object> depend = (Map<String, Object>) dependObject;
            String target = depend.get("target").toString();
            if (map.get(target) == null) {
                throw new RuntimeException("找不到依赖项 " + target);
            }
        }
    }

    private void load() {
        moveing = new ArrayList<>(map.keySet());

        moveing.remove("ap.java");
        apjava = new String(map.get("ap.java"), StandardCharsets.UTF_8);
        moveing.remove("pi.toml");
        String pitoml = new String(map.get("pi.toml"), StandardCharsets.UTF_8);
        Map<String, Object> toml = Toml.read(pitoml);
        description = String.valueOf(toml.get("description"));
        if (description.equals("null")) {
            description = "";
        }
        List<Object> dependList = (List) toml.get("depend");

        libs = new ArrayList<>();

        for (Object dependObject : dependList) {
            Map<String, Object> depend = (Map<String, Object>) dependObject;
            String target = depend.get("target").toString();

            libs.add(target);
            moveing.remove(target);
        }

    }

    /**
     * 初始化项目
     *
     * @param project 项目
     * @return
     */
    public void initialize(Project project) throws IOException {
        File file = project.getTarget();
        File apjavaFile = new File(file, "ap.java");
        apjavaFile.createNewFile();
        Writer writer = new OutputStreamWriter(new FileOutputStream(apjavaFile), StandardCharsets.UTF_8);
        writer.write(apjava);
        writer.close();

        Random random = new Random();

        for (String lib : libs) {
            byte[] bytes = new byte[32];
            random.nextBytes(bytes);

            File aplibsFile = new File(file, ".apf/libs/" +
                    new BigInteger(1, bytes).abs().toString(16) + ".jar");
            aplibsFile.getParentFile().mkdirs();
            apjavaFile.createNewFile();
            OutputStream outputStream = new FileOutputStream(aplibsFile);
            outputStream.write(map.get(lib));
            outputStream.close();
        }

        for (String moveitem : moveing) {
            File mf = new File(file, moveitem);
            mf.getParentFile().mkdirs();
            OutputStream outputStream = new FileOutputStream(mf);
            outputStream.write(map.get(moveitem));
            outputStream.close();
        }
    }

    public String getDescription() {
        return description;
    }
}
