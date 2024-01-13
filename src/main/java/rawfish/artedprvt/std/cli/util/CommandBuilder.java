package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.Command;
import rawfish.artedprvt.std.cli.CompleteInterface;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.FormatInterface;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.InfoInterface;
import rawfish.artedprvt.std.cli.Messager;
import rawfish.artedprvt.std.cli.ProcessInterface;

import java.util.List;

@Solvable
public class CommandBuilder {
    private String name;
    private ProcessInterface process;
    private CompleteInterface complete;
    private FormatInterface format;
    private InfoInterface info;

    @Solvable
    public CommandBuilder(String name) {
        this.name = name;
    }

    @Solvable
    public CommandBuilder process(ProcessInterface process) {
        this.process = process;
        return this;
    }

    @Solvable
    public CommandBuilder complete(CompleteInterface complete) {
        this.complete = complete;
        return this;
    }

    @Solvable
    public CommandBuilder format(FormatInterface format) {
        this.format = format;
        return this;
    }

    @Solvable
    public CommandBuilder info(InfoInterface info) {
        this.info = info;
        return this;
    }

    @Solvable
    public Command build() {
        return new ProxyCommand(name, process, complete, format, info);
    }

    private static class ProxyCommand extends Command {
        private ProcessInterface process;
        private CompleteInterface complete;
        private FormatInterface format;
        private InfoInterface info;

        public ProxyCommand(String commandName, ProcessInterface process, CompleteInterface complete, FormatInterface format, InfoInterface info) {
            super(commandName);
            this.process = process;
            this.complete = complete;
            this.format = format;
            this.info = info;
            init();
        }

        public void init() {
            if (process == null) {
                process = new EmptyProcess();
            }
            if (complete == null) {
                complete = new EmptyComplete();
            }
            if (format == null) {
                format = new EmptyFormat();
            }
            if (info == null) {
                info = new EmptyInfo();
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

    private static class EmptyProcess implements ProcessInterface {
        @Override
        public void process(List<String> args, Messager messager) {

        }
    }

    private static class EmptyComplete implements CompleteInterface {
        @Override
        public List<String> complete(List<String> args) {
            return Literals.emptyComplete();
        }
    }

    private static class EmptyFormat implements FormatInterface {
        @Override
        public List<? extends FormatHandler> format(List<String> args) {
            return Literals.emptyFormat();
        }
    }

    private static class EmptyInfo implements InfoInterface {
        @Override
        public InfoHandler info(List<String> args) {
            return Literals.emptyInfo();
        }
    }
}
