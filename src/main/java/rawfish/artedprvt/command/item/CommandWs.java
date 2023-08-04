package rawfish.artedprvt.command.item;

import rawfish.artedprvt.Artedprvt;
import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.FrameProperties;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CommandWs extends Command {
    public CommandWs(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()>0){
            CommandMessages.exception(getCommandName(),"不能有参数");
            return;
        }
        String dir= FrameProperties.props.get("frame.dir");
        File artedprvt=new File(dir);
        if(artedprvt.isDirectory()){
            CommandMessages.exception(getCommandName(),"/artedprvt目录已经存在");
            return;
        }
        if(!artedprvt.mkdir()){
            CommandMessages.exception(getCommandName(),"/artedprvt目录创建失败");
            return;
        }
        //将资源解压到工作目录
        Map<String,String> files=new HashMap<>();
        InputStream input=Artedprvt.class.getResourceAsStream("/workspace.zip");
        ZipInputStream zip = new ZipInputStream(
                input,
                Charset.forName("cp437"));
        Reader reader=new InputStreamReader(zip, StandardCharsets.UTF_8);
        ZipEntry entry;
        try {
            while ((entry = zip.getNextEntry()) != null) {
                int n;
                int i = 0;
                StringBuilder sb = new StringBuilder(0);
                while ((n = reader.read()) != -1) {
                    sb.append((char) n);
                }
                if(entry.isDirectory()) {
                    files.put(";"+entry.getName(), sb.toString());
                }else{
                    files.put(entry.getName(), sb.toString());
                }
            }
            reader.close();
        }catch (Throwable e){
            e.printStackTrace(System.err);
            CommandMessages.exception(getCommandName(),"资源解压失败");
            return;
        }
        Set<String> fileNames=files.keySet();
        try {
            for (String fileName : fileNames) {
                String fileData = files.get(fileName);
                if(fileName.charAt(0)==';'){
                    File file = new File(dir + "/" + fileName.substring(1));
                    file.mkdirs();
                    file.mkdir();
                }else {
                    File file = new File(dir + "/" + fileName);
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    BufferedWriter writer = new BufferedWriter(
                            new OutputStreamWriter(
                                    new FileOutputStream(file), StandardCharsets.UTF_8));
                    writer.write(fileData);
                    writer.close();
                }
            }
        }catch (Throwable e){
            e.printStackTrace(System.err);
            CommandMessages.exception(getCommandName(),"资源写入异常");
        }
        CommandMessages.key(getCommandName(),"工作空间构建成功");
    }

    @Override
    public List<String> complete(List<String> args) {
        return nullTab;
    }
}
