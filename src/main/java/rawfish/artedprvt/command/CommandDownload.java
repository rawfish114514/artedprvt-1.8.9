package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import rawfish.artedprvt.conn.ChatOut;
import rawfish.artedprvt.conn.DownloadTask;
import rawfish.artedprvt.conn.urlspec.GiteeUrlspec;
import rawfish.artedprvt.conn.urlspec.GithubUrlspec;

import java.net.MalformedURLException;
import java.net.URL;

public class CommandDownload extends CommandBase {
    public CommandDownload(String nameIn){
        name=nameIn;
    }

    public final String name;
    @Override
    public String getCommandName() {
        return name;
    }

    @Override
    public String getCommandUsage(ICommandSender sender)
    {
        return "commands.download.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        if(args.length==0){
            throw new CommandException("commands.download.usage");
        }

        ChatOut chatOut=new ChatOut(sender);
        URL url;
        try {
            if(args[0].equals("-github")){
                if(args.length<3){
                    throw new CommandException("commands.download.usage");
                }
                url=new URL(new GithubUrlspec(chatOut,args[1],args[2]).getString());
            }else if(args[0].equals("-gitee")){
                if(args.length<3){
                    throw new CommandException("commands.download.usage");
                }
                url=new URL(new GiteeUrlspec(chatOut,args[1],args[2]).getString());
            }else{
                url=new URL(args[0]);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        DownloadTask downloadTask=new DownloadTask(chatOut,url);
        downloadTask.start();
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}