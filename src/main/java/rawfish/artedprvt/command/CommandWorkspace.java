package rawfish.artedprvt.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.WrongUsageException;
import net.minecraft.util.ChatComponentText;
import rawfish.artedprvt.id.FormatCode;
import rawfish.artedprvt.script.ScriptConst;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

public class CommandWorkspace extends CommandBase {
    public CommandWorkspace(String nameIn){
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
        return "commands.workspace.usage";
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) throws CommandException
    {
        String dir=System.getProperties().get("user.dir").toString()+"/artedprvt";//项目目录
        File file;
        file=new File(dir);
        if(file.exists()){
            throw new CommandException("目录/artedprvt已经存在");
        }
        file.mkdir();

        file=new File(dir+"/lib");
        file.mkdir();

        file=new File(dir+"/src");
        file.mkdir();

        file=new File(dir+"/src/script");
        file.mkdir();

        file=new File(dir+"/src/config.json");
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try {
            Writer writer=new FileWriter(file);
            writer.write(fileConfig());
            writer.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        sender.addChatMessage(new ChatComponentText(FormatCode.COLOR_2+"创建成功: "+dir));
    }
    public String fileConfig(){
        return "{\n" +
                "  \"options\": [\n" +
                "    \"-pm\",\"-al\"\n" +
                "  ],\n" +
                "  \"pkg\": {\n" +
                "    \"pack\": \"main\"\n" +
                "  }\n" +
                "}";
    }
    @Override
    public int getRequiredPermissionLevel()
    {
        return 0;
    }
}