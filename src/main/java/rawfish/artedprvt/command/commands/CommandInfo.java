package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.script.Metadata;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.localization.types.CIS;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.core.script.struct.AarFileLoader;
import rawfish.artedprvt.core.script.struct.FileLoader;
import rawfish.artedprvt.core.script.struct.SourceFileLoader;
import rawfish.artedprvt.mi.ChatProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 查看src或aar的信息
 */
public class CommandInfo extends Command {
    public CommandInfo(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        String pack=null;
        try {
            if (args.size() == 0) {
                //全部
                List<String> all = complete(Arrays.asList(""));
                for (String p : all) {
                    pack=p;
                    processPack(pack);
                }
                return;
            } else if (args.size() == 1) {
                //选择
                pack = args.get(0);
                processPack(pack);
                return;
            }
            CommandMessages.exception(getName(), CMS.cms16);
        }catch (Exception e){
            CommandMessages.exception(getName(),CMS.cms30,pack);
        }
    }

    public void processPack(String pack) throws Exception {
        if (pack.equals("<src>")) {
            Metadata info = readSrcInfo();
            printInfo(info,"<src>");
        } else {
            Metadata info= readAarInfo(pack);
            printInfo(info,pack+".aar");
        }
    }

    public Metadata readAarInfo(String pack) throws Exception {
        FileLoader fileLoader=new AarFileLoader(WorkSpace.derivation(CommandAar.AAR,pack));
        String aarinfo=fileLoader.getContent("aar.toml");
        Metadata metadata = Metadata.parse(aarinfo);
        Metadata.inspect(metadata);
        return metadata;
    }

    public Metadata readSrcInfo() throws Exception {
        FileLoader fileLoader=new SourceFileLoader(WorkSpace.derivation(WorkSpace.SRC));
        String aarinfo=fileLoader.getContent("aar.toml");
        Metadata metadata = Metadata.parse(aarinfo);
        Metadata.inspect(metadata);
        return metadata;
    }

    public void printInfo(Metadata info, String entry){
        CommandMessages.printChat.print("§6>§f"+entry, new ChatProvider() {
            public Metadata info;
            public ChatProvider setInfo(Metadata info){
                this.info=info;
                return this;
            }
            @Override
            public String getChat() {
                if(info!=null){
                    String s=info.getName()+"   --"+info.getAuthor();
                    s+="\n\n";
                    s+=info.getDescription();
                    s+="\n\n";
                    s+="id: "+info.getId();
                    s+="\n";
                    s+="version: "+info.getVersion();
                    s+="\n";
                    s+="mcversion: "+info.getMcversion();
                    return s;
                }
                return null;
            }
        }.setInfo(info));
    }

    @Override
    public List<String> complete(List<String> args) {
        if(args.size()==1) {
            /*补全包名*/
            List<String> opt = new ArrayList<>();
            String lastArgs=args.get(0);
            //包名
            File script = new File(WorkSpace.derivation(WorkSpace.LIB));
            if (script.isDirectory()) {
                List<String> packs = pack(script, "");
                opt.addAll(match(packs, lastArgs));
            }
            opt.sort(Comparator.comparingInt(String::length));
            opt.add("<src>");
            return opt;
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        if(args.get(0).equals("<src>")){
            return Literals.formatListBuilder().append("6");
        }
        File aar = new File(WorkSpace.derivation(CommandAar.AAR,args.get(0)));
        if(aar.isFile()){
            return Literals.formatListBuilder().append("6");
        }else{
            return Literals.formatListBuilder().append("c");
        }
    }

    @Override
    public InfoHandler info(List<String> args) {
        if(args.size()==1){
            if(args.get(0).isEmpty()){
                return Literals.infoBuilder().string(CIS.cis5);
            }
            if(args.get(0).equals("<src>")){
                return Literals.emptyInfo();
            }
            File aar = new File(WorkSpace.derivation(CommandAar.AAR,args.get(0)));
            if(aar.isFile()){
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
                if(ind>0&&abbr.equals("aar")){
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
}
