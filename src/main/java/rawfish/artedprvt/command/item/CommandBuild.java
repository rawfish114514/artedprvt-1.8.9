package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.FrameProperties;
import rawfish.artedprvt.core.ScriptInfo;
import rawfish.artedprvt.core.struct.FileLoader;
import rawfish.artedprvt.core.struct.SourceFileLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class CommandBuild extends Command {
    public CommandBuild(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()>0){
            CommandMessages.exception(getName(),"cms0");
            return;
        }
        File ff=new File(FrameProperties.props().get("frame.dir"));
        if(!ff.isDirectory()){
            CommandMessages.exception(getName(),"cms4");
            return;
        }
        ScriptInfo scriptInfo;
        try {
            FileLoader fileLoader = new SourceFileLoader(FrameProperties.props().get("frame.dir") + "/src");
            String apkginfo = fileLoader.getString("apkg.info");
            scriptInfo = ScriptInfo.parse(apkginfo);
            ScriptInfo.inspect(scriptInfo);
        }catch (Exception e){
            CommandMessages.exception(getName(),"cms5");
            return;
        }
        String name=scriptInfo.getName()+"-"+scriptInfo.getVersion();
        String fd=FrameProperties.props.get("frame.dir");
        int code=zip(fd+"/src",fd+"/lib/"+name+".apkg");

        if(code==0){
            CommandMessages.key(getName(),"cms5");
        }
        if(code==-1){
            CommandMessages.exception(getName(),"cms6");
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        return nullTab;
    }

    public int zip(String target,String out){
        try {
            File tf = new File(target);
            List<File> fileList = getAllFile(tf);
            ZipOutputStream zip = new ZipOutputStream(new FileOutputStream(out));
            for(File f:fileList){
                String name=f.toPath().subpath(tf.toPath().getNameCount(),f.toPath().getNameCount()).toString();
                ZipEntry entry=new ZipEntry(name);
                zip.putNextEntry(entry);
                InputStream input=new FileInputStream(f);
                int n;
                while(true){
                    if((n=input.read())==-1){
                        break;
                    }
                    zip.write(n);
                }
                zip.closeEntry();
            }
            zip.close();
        }catch (Exception e){
            return -1;
        }
        return 0;
    }

    public List<File> getAllFile(File file){
        List<File> fileList=new ArrayList<>();
        File[] files=file.listFiles();
        for(File f:files){
            if(f.isDirectory()){
                fileList.addAll(getAllFile(f));
            }else{
                fileList.add(f);
            }
        }
        return fileList;
    }
}
