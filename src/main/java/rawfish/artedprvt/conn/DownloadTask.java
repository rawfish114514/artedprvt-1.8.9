package rawfish.artedprvt.conn;

import net.minecraft.command.ICommandSender;
import net.minecraft.util.ChatComponentText;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 下载任务
 * 1.获取
 * 2.下载
 * 3.保存
 */
public class DownloadTask{
    public ChatOut chatOut;
    public URL url;
    public DownloadTask(ChatOut chatOut,URL url){
        this.chatOut=chatOut;
        this.url=url;
    }

    public void connect(){
        try {
            URLConnection connection=url.openConnection();

            if(connection instanceof HttpURLConnection){
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                try{
                    conn.setRequestMethod("GET");
                    conn.setConnectTimeout(5000);
                    conn.setRequestProperty("User-Agent", "Mozilla/5.0 (homo 114514; 1919810)");

                    conn.connect();

                    System.out.println(conn.getResponseCode());
                    InputStream input=conn.getInputStream();

                    String str=getString(input);
                    String name=new File(url.getFile()).getName();
                    String dir=System.getProperties().get("user.dir").toString()+"/artedprvt";
                    String suf=name.substring(name.lastIndexOf(".")+1);
                    String ft=type.get(suf);
                    if(ft==null){
                        return;
                    }
                    String path=ft.replace("%ap%",dir).replace("%ta%",name);

                    writeFile(path,str);

                    chatOut.print("\u00a72download success: \u00a7f"+name);
                }catch (Exception e){
                    chatOut.print("\u00a74download failure: \u00a77"+e.getClass().getName());
                    throw new RuntimeException(e);
                }finally {
                    conn.disconnect();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void start(){
        new Thread(this::connect).start();
    }

    public int size=0;

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

    public void writeFile(String path,String str) throws Exception {
        File folder=new File(path.substring(0,path.lastIndexOf("/")));
        folder.mkdirs();
        File file=new File(path);
        file.createNewFile();
        Writer write=new OutputStreamWriter(new FileOutputStream(file),StandardCharsets.UTF_8);
        write.write(str);
        write.flush();
        write.close();
    }

    public static Map<String,String> type=new HashMap<String,String>(){{
        put("js","%ap%/src/script/downloads/%ta%");
        put("apkg","%ap%/lib/downloads/%ta%");
    }};
}
