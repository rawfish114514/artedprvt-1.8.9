package rawfish.artedprvt.command.commands;

import com.electronwill.toml.Toml;
import org.apache.http.util.ByteArrayBuffer;
import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.formats.FormatHandlerAppend;
import rawfish.artedprvt.command.formats.FormatHandlerSet;
import rawfish.artedprvt.command.infos.InfoHandlerMap;
import rawfish.artedprvt.command.infos.InfoHandlerString;
import rawfish.artedprvt.command.util.CommandMessages;
import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.InfoHandler;
import rawfish.artedprvt.command.formats.FormatHandlerNumber;
import rawfish.artedprvt.command.util.Literals;
import rawfish.artedprvt.core.WorkSpace;
import rawfish.artedprvt.core.localization.types.CMS;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 从储存库安装apkg
 */
public class CommandInstall extends Command {
    public CommandInstall(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if(args.size()==0){
            CommandMessages.exception(getName(), CMS.cms1);
            return;
        }
        if(args.size()>1){
            CommandMessages.exception(getName(),CMS.cms16);
            return;
        }
        File ff=new File(WorkSpace.currentWorkSpace().getDir());
        if(!ff.isDirectory()){
            CommandMessages.exception(getName(),CMS.cms4);
            return;
        }
        String id=args.get(0);
        File file=new File(WorkSpace.currentWorkSpace().getDir()+"/lib/"+id+".apkg");
        install(file,id);
    }

    @Override
    public List<String> complete(List<String> args) {
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        /*temp test*/
        //return getEmptyFormatterList();
        List<FormatHandler> list=new ArrayList<>();
        list.add(new FormatHandlerNumber());
        list.add(new FormatHandlerSet(Literals.stringListBuilder().adds("get","set","put","add","sort"),new FormatHandlerAppend("a"),new FormatHandlerAppend("c")));
        return list;
    }

    @Override
    public InfoHandler info(List<String> args) {
        return new InfoHandlerMap(new HashMap<String,InfoHandler>(){{
            put("get",new InfoHandlerString("获取"));
            put("set",new InfoHandlerString("设置"));
            put("put",new InfoHandlerString("推进"));
            put("add",new InfoHandlerString("添加"));
            put("sort",new InfoHandlerString("排序"));
        }},new InfoHandlerString("错误的"));
        //return infoString("target");
    }

    public void install(File file,String id){
        String server="https://gitee.com/rawfishc/apse/raw/master/";
        new Thread(){
            public void run(){
                //获取表格
                String table=null;
                try {
                    URL tableURL=new URL(server+"table");
                    URLConnection connection=tableURL.openConnection();

                    if(connection instanceof HttpURLConnection){
                        HttpURLConnection conn = (HttpURLConnection)connection;
                        try{
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);
                            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (homo 114514; 1919810)");

                            conn.connect();
                            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                            }else{
                                throw new RuntimeException("响应异常");
                            }

                            InputStream input=conn.getInputStream();
                            table=getString(input);

                        }catch (Exception e){
                            throw new RuntimeException(e);

                        }finally {
                            conn.disconnect();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                //解析目标
                if(table==null){
                    stop();
                }
                Map<String,Object> map= Toml.read(table);
                Object t=map.get(id);
                if(t==null){
                    stop();
                }
                String nt=t.toString();
                ByteArrayBuffer buffer=new ByteArrayBuffer(0);
                try {
                    URL targetURL=new URL(server+nt+id+".apkg");
                    URLConnection connection=targetURL.openConnection();

                    if(connection instanceof HttpURLConnection){
                        HttpURLConnection conn = (HttpURLConnection)connection;
                        try{
                            conn.setRequestMethod("GET");
                            conn.setConnectTimeout(5000);
                            conn.setRequestProperty("User-Agent", "Mozilla/5.0 (homo 114514; 1919810)");

                            conn.connect();
                            if(conn.getResponseCode()==HttpURLConnection.HTTP_OK){
                            }else{
                                throw new RuntimeException("响应异常");
                            }

                            InputStream input=conn.getInputStream();
                            int n;
                            while((n=input.read())!=-1){
                                buffer.append(n);
                            }

                        }catch (Exception e){
                            throw new RuntimeException(e);

                        }finally {
                            conn.disconnect();
                        }
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                byte[] bs=buffer.buffer();
                if(bs.length==0){
                    stop();
                }
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                    OutputStream output=new FileOutputStream(file);
                    output.write(bs);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                CommandMessages.key(CommandInstall.this.getName(),CMS.cms24);
            }
        }.start();
    }

    public String getString(InputStream input) throws Exception{
        StringBuilder sb=new StringBuilder();
        while(true){
            int c=input.read();
            if(c==-1){
                break;
            }
            sb.append((char)c);
        }
        return new String(sb.toString().getBytes(StandardCharsets.ISO_8859_1), StandardCharsets.UTF_8);
    }
}
