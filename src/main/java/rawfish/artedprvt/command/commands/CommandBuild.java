package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.ScriptInfo;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
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

/**
 * 将src目录的文件构建为apkg
 */
public class CommandBuild extends Command {
    public CommandBuild(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()>0){
            CommandMessages.exception(getName(),CMS.cms0);
            return;
        }
        File ff=new File(WorkSpace.currentWorkSpace().getDir());
        if(!ff.isDirectory()){
            CommandMessages.exception(getName(),CMS.cms4);
            return;
        }
        ScriptInfo scriptInfo;
        try {
            FileLoader fileLoader = new SourceFileLoader(WorkSpace.currentWorkSpace().getDir() + "/src");
            String apkginfo = fileLoader.getContent("apkg.info");
            scriptInfo = ScriptInfo.parse(apkginfo);
            ScriptInfo.inspect(scriptInfo);
        }catch (Exception e){
            CommandMessages.exception(getName(),CMS.cms5);
            return;
        }
        String name=scriptInfo.getId();
        String fd= WorkSpace.currentWorkSpace().getDir();
        int code=zip(fd+"/src",fd+"/lib/"+name+".apkg");

        if(code==0){
            CommandMessages.key(getName(), CMS.cms6);
        }
        if(code==-1){
            CommandMessages.exception(getName(),CMS.cms7);
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.emptyFormat();
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()>0&&(!args.get(0).isEmpty())){
            return Literals.infoBuilder().string(CIS.cis3);
        }
        return Literals.emptyInfo();
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
