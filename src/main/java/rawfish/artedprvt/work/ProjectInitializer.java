package rawfish.artedprvt.work;

import rawfish.artedprvt.Artedprvt;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
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
    public static final Map<String,ProjectInitializer> initializerMap=new HashMap<String,ProjectInitializer>(){{
        put("script",new ProjectInitializer());
        put("java",new ProjectInitializer());
    }};

    private Map<String,byte[]> map=new HashMap<String,byte[]>();

    public ProjectInitializer(){}
    /**
     * 创建项目初始化器
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
                ByteArrayOutputStream outputStream=new ByteArrayOutputStream();
                while ((n = zip.read()) != -1) {
                    outputStream.write(n);
                }
                map.put(entry.getName().replace(File.separator,"/"),outputStream.toByteArray());
            }
        }
        zip.close();

        check();
        load();
    }

    /**
     * 检查
     * 要求必须存在ap.java和pi.toml
     * 而且在.apf文件夹中不能存在文件和文件夹
     * 但.apf/project文件夹是例外
     * 初始化之后这些文件直接放在项目目录中
     * .apf和ap.java在项目根目录中
     *
     * 项目脚本也不允许操作ap.java文件和.apf文件夹除了.apf/project文件夹
     */
    private void check(){

    }

    private void load(){

    }

    /**
     * 初始化项目
     * @param project 项目
     * @return
     */
    public void initialize(Project project){

    }
}
