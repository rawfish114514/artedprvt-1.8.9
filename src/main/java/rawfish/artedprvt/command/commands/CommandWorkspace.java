package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.Artedprvt;
import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class CommandWorkspace extends Command {
    public CommandWorkspace(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()==0) {
            CommandMessages.exception(getName(), CMS.cms1);
            return;
        }
        if(args.size()==1){
            String arg=args.get(0);
            if(arg.equals("current")){
                //显示当前工作空间
                CommandMessages.key(getName(),"当前工作空间: {0} = {1}",
                        WorkSpace.currentWorkSpace().getName(),
                        WorkSpace.currentWorkSpace().getDir());
                return;
            }

            if(arg.equals("create")){
                create();
                return;
            }
        }
        if(args.size()==2){
            String arg0=args.get(0);
            if(arg0.equals("set")){
                //设置工作空间
                String arg=args.get(1);

                String dir=arg.replace("%user.dir%",System.getProperty("user.dir"));
                WorkSpace.workSpace=new WorkSpace("new_workspace",dir);
            }
        }
    }

    public void create(){
        String dir= WorkSpace.currentWorkSpace().getDir();
        File artedprvt=new File(dir);
        if(artedprvt.isDirectory()){
            CommandMessages.exception(getName(), CMS.cms19);
            return;
        }
        if(!artedprvt.mkdir()){
            CommandMessages.exception(getName(),CMS.cms20);
            return;
        }
        //将资源解压到工作目录
        Map<String,String> files=new HashMap<>();
        InputStream input= Artedprvt.class.getResourceAsStream("/workspace.zip");
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
            CommandMessages.exception(getName(),CMS.cms21);
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
            CommandMessages.exception(getName(),CMS.cms22);
        }
        CommandMessages.key(getName(),CMS.cms23);
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1) {
            return Literals.stringListBuilder().adds("current", "create", "set");
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.formatListBuilder().append("d").append("a");
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()==1){
            return Literals.infoBuilder().map(
                    Literals.infoMapBuilder()
                            .puts("current",Literals.infoBuilder().string("当前"))
                            .puts("create",Literals.infoBuilder().string("构建"))
                            .puts("set",Literals.infoBuilder().string("设置")),
                    Literals.infoBuilder().empty());
        }
        if(args.size()>2&&(!args.get(0).isEmpty())){
            return Literals.infoBuilder().string(CIS.cis3);
        }
        return Literals.emptyInfo();
    }
}
