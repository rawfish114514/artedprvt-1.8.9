package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.CommandComplete;
import rawfish.artedprvt.std.cli.CommandFormat;
import rawfish.artedprvt.std.cli.CommandInfo;
import rawfish.artedprvt.std.cli.CommandProcess;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.Messager;

import java.util.List;

@Solvable
public class CommandBuilder {
    private String name;
    private CommandProcess process;
    private CommandComplete complete;
    private CommandFormat format;
    private CommandInfo info;

    @Solvable
    public CommandBuilder(String name){
        this.name=name;
    }

    @Solvable
    public CommandBuilder process(CommandProcess process){
        this.process=process;
        return this;
    }

    @Solvable
    public CommandBuilder complete(CommandComplete complete){
        this.complete=complete;
        return this;
    }

    @Solvable
    public CommandBuilder format(CommandFormat format){
        this.format=format;
        return this;
    }

    @Solvable
    public CommandBuilder info(CommandInfo info){
        this.info=info;
        return this;
    }

    @Solvable
    public Command build(){
        return new ProxyCommand(name,process,complete,format,info);
    }

    private static class ProxyCommand extends Command{
        private CommandProcess process;
        private CommandComplete complete;
        private CommandFormat format;
        private CommandInfo info;

        public ProxyCommand(String commandName, CommandProcess process, CommandComplete complete, CommandFormat format, CommandInfo info) {
            super(commandName);
            this.process = process;
            this.complete = complete;
            this.format = format;
            this.info = info;
            init();
        }

        public void init(){
            if(process==null){
                process=new EmptyProcess();
            }
            if(complete==null){
                complete=new EmptyComplete();
            }
            if(format==null){
                format=new EmptyFormat();
            }
            if(info==null){
                info=new EmptyInfo();
            }
        }

        @Override
        public void process(List<String> args, Messager messager) {
            process.process(args, messager);
        }

        @Override
        public List<String> complete(List<String> args) {
            return complete.complete(args);
        }

        @Override
        public List<? extends FormatHandler> format(List<String> args) {
            return format.format(args);
        }

        @Override
        public InfoHandler info(List<String> args) {
            return info.info(args);
        }
    }

    private static class EmptyProcess implements CommandProcess{
        @Override
        public void process(List<String> args, Messager messager) {

        }
    }

    private static class EmptyComplete implements CommandComplete{
        @Override
        public List<String> complete(List<String> args) {
            return Literals.emptyComplete();
        }
    }

    private static class EmptyFormat implements CommandFormat{
        @Override
        public List<? extends FormatHandler> format(List<String> args) {
            return Literals.emptyFormat();
        }
    }

    private static class EmptyInfo implements CommandInfo{
        @Override
        public InfoHandler info(List<String> args) {
            return Literals.emptyInfo();
        }
    }
}
