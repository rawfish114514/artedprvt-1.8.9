package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.*;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.util.FormatHandlerListBuilder;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.FrameProperties;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.mi.PrintChat;

import java.io.*;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * 将apkg文件内容提取到src目录
 */
public class CommandExtract extends Command {
    public Pattern packPattern=Pattern.compile("(([^\\/]+\\/)*)([^\\/]+)");
    public CommandExtract(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()<1){
            CommandMessages.exception(getName(),CMS.cms1);
            return;
        }
        String pack=args.get(0);
        Matcher matcher=packPattern.matcher(pack);
        if(!matcher.matches()){
            CommandMessages.exception(getName(),CMS.cms2);
            return;
        }
        File ff=new File(FrameProperties.props().get("frame.dir"));
        if(!ff.isDirectory()){
            CommandMessages.exception(getName(),CMS.cms4);
            return;
        }
        File srcf=new File(FrameProperties.props().get("frame.dir")+"/src");
        srcf.mkdirs();
        List<File> fs=getAllFile(srcf);
        if(fs.size()>0){
            CommandMessages.exception(getName(),CMS.cms8);
            return;
        }
        String fd=FrameProperties.props.get("frame.dir");
        int code=unzip(fd+"/lib/"+pack+".apkg",fd+"/src");

        if(code==0){
            CommandMessages.key(getName(), CMS.cms9);
        }
        if(code==-1){
            CommandMessages.exception(getName(),CMS.cms10);
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1) {
            /*补全包名*/
            List<String> opt = new ArrayList<>();
            String lastArgs=args.get(0);
            //包名
            File script = new File(FrameProperties.props.get("frame.dir") + "/lib");
            if (script.isDirectory()) {
                List<String> packs = pack(script, "");
                opt.addAll(match(packs, lastArgs));
            }
            opt.sort(Comparator.comparingInt(String::length));
            return opt;
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        File apkg = new File(FrameProperties.props.get("frame.dir") + "/lib/"+args.get(0)+".apkg");
        FormatHandlerListBuilder fl=Literals.formatListBuilder();
        if(apkg.isFile()){
            fl.append("6");
        }else{
            fl.append("c");
            return fl;
        }
        return fl;
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()==1){
            if(args.get(0).isEmpty()){
                return Literals.infoBuilder().string(CIS.cis5);
            }
            File apkg = new File(FrameProperties.props.get("frame.dir") + "/lib/"+args.get(0)+".apkg");
            if(apkg.isFile()){
                return Literals.emptyInfo();
            }
            return Literals.infoBuilder().string(CIS.cis1);
        }
        return Literals.emptyInfo();
    }

    public List<String> pack(File dir,String p){
        List<String> packs=new ArrayList<>();
        File[] files=dir.listFiles();
        List<File> fileList=new ArrayList<>();
        fileList.addAll(Arrays.asList(files));
        for (int i = 0; i < fileList.size(); i++) {
            File file=fileList.get(i);
            if(file.isFile()){
                //是文件
                String name=file.getName();
                int ind=name.lastIndexOf('.');
                String abbr=name.substring(ind+1);
                if(ind>0&&abbr.equals("apkg")){
                    packs.add(p+name.substring(0,ind));
                }
            }else{
                //是目录
                packs.addAll(pack(file,p+file.getName()+"/"));
            }
        }
        return packs;
    }

    public List<String> match(List<String> packs,String arg){
        List<String> npacks=new ArrayList<>();
        for(String pack:packs){
            if(pack.length()>=arg.length()){
                if(pack.startsWith(arg)){
                    npacks.add(pack);
                }
            }
        }
        return npacks;
    }

    public int unzip(String target,String out){
        try{
            ZipInputStream zip=new ZipInputStream(new FileInputStream(target), Charset.forName("cp437"));
            ZipEntry entry = null;
            while ((entry = zip.getNextEntry()) != null) {
                if (!entry.isDirectory()) {
                    int n;
                    File file=new File(out+"/"+entry.getName());
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    OutputStream output=new FileOutputStream(file);
                    while ((n = zip.read()) != -1) {
                        output.write(n);
                    }
                    output.flush();
                    output.close();
                }
            }
            zip.close();
        }catch (Exception e){
            new PrintChat().print(e.toString());
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