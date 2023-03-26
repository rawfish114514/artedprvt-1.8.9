package rawfish.artedprvt.conn.urlspec;

import rawfish.artedprvt.conn.ChatOut;
import rawfish.artedprvt.conn.Urlspec;

public class GithubUrlspec implements Urlspec {
    public ChatOut chatOut;
    public String repository;
    public String file;

    /**
     * 创建github网址
     * @param repository 仓库目录 相对于网站
     * @param file 文件目录 相对于仓库
     */
    public GithubUrlspec(ChatOut chatOut, String repository, String file){
        this.chatOut=chatOut;
        this.repository=repository;
        this.file=file;

        chatOut.print("download.gitee");
    }

    @Override
    public String getString() {
        return String.format("https://raw.githubusercontent.com/%s/master/%s",repository,file);
    }
}
