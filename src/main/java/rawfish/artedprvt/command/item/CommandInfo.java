package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.FrameProperties;
import rawfish.artedprvt.core.ScriptInfo;
import rawfish.artedprvt.core.struct.ApkgFileLoader;
import rawfish.artedprvt.core.struct.FileLoader;
import rawfish.artedprvt.core.struct.SourceFileLoader;
import rawfish.artedprvt.mi.ChatProvider;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

/**
 * 查看src或apkg的信息
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
            CommandMessages.exception(getName(), "cms16");
        }catch (Exception e){
            CommandMessages.exception(getName(),"cms30",pack);
        }
    }

    public void processPack(String pack) throws Exception {
        if (pack.equals("<src>")) {
            ScriptInfo info = readSrcInfo();
            printInfo(info,"<src>");
        } else {
            ScriptInfo info=readApkgInfo(pack);
            printInfo(info,pack+".apkg");
        }
    }

    public ScriptInfo readApkgInfo(String pack) throws Exception {
        FileLoader fileLoader=new ApkgFileLoader(FrameProperties.props().get("frame.dir")+"/lib/"+pack+".apkg");
        String apkginfo=fileLoader.getContent("apkg.info");
        ScriptInfo scriptInfo=ScriptInfo.parse(apkginfo);
        ScriptInfo.inspect(scriptInfo);
        return scriptInfo;
    }

    public ScriptInfo readSrcInfo() throws Exception {
        FileLoader fileLoader=new SourceFileLoader(FrameProperties.props().get("frame.dir")+"/src");
        String apkginfo=fileLoader.getContent("apkg.info");
        ScriptInfo scriptInfo=ScriptInfo.parse(apkginfo);
        ScriptInfo.inspect(scriptInfo);
        return scriptInfo;
    }

    public void printInfo(ScriptInfo info,String entry){
        CommandMessages.printChat.print("§6>§f"+entry, new ChatProvider() {
            public ScriptInfo info;
            public ChatProvider setInfo(ScriptInfo info){
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
            File script = new File(FrameProperties.props.get("frame.dir") + "/lib");
            if (script.isDirectory()) {
                List<String> packs = pack(script, "");
                opt.addAll(match(packs, lastArgs));
            }
            opt.sort(Comparator.comparingInt(String::length));
            opt.add("<src>");
            return opt;
        }
        return getEmptyList();
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
}
