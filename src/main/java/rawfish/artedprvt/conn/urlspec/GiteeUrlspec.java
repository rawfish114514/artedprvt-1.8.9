package rawfish.artedprvt.conn.urlspec;

import rawfish.artedprvt.conn.ChatOut;
import rawfish.artedprvt.conn.Urlspec;

public class GiteeUrlspec implements Urlspec {
    public ChatOut chatOut;
    public String repository;
    public String file;

    /**
     * 创建gitee网址
     * @param repository 仓库目录 相对于网站
     * @param file 文件目录 相对于仓库
     */
    public GiteeUrlspec(ChatOut chatOut,String repository,String file){
        this.chatOut=chatOut;
        this.repository=repository;
        this.file=file;

        chatOut.print("download.gitee");
    }

    @Override
    public String getString() {
        return String.format("https://gitee.com/%s/raw/master/%s",repository,file);
    }
}