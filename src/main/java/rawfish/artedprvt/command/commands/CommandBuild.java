package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.util.CommandMessages;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.core.script.MetaData;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.core.script.struct.FileLoader;
import rawfish.artedprvt.core.script.struct.SourceFileLoader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * 将src目录的文件构建为aar
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
        File ff=new File(WorkSpace.dir());
        if(!ff.isDirectory()){
            CommandMessages.exception(getName(),CMS.cms4);
            return;
        }
        MetaData metadata;
        try {
            FileLoader fileLoader = new SourceFileLoader(WorkSpace.derivation(WorkSpace.SRC));
            String aarinfo = fileLoader.getContent("aar.toml");
            metadata = MetaData.parse(aarinfo);
            MetaData.inspect(metadata);
        }catch (Exception e){
            CommandMessages.exception(getName(),CMS.cms5);
            return;
        }
        String name= metadata.getId();
        String fd= WorkSpace.dir();
        int code=zip(fd+"/src",fd+"/lib/"+name+".aar");

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
            return Literals.infoFactory().string(CIS.cis3);
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
