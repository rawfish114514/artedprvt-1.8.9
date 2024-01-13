package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.BaseCommand;
import rawfish.artedprvt.command.FormatMessager;
import rawfish.artedprvt.core.app.script.ScriptProcess;
import rawfish.artedprvt.core.app.script.engine.Engines;
import rawfish.artedprvt.core.app.script.struct.SourceFileLoader;
import rawfish.artedprvt.core.localization.types.CMS;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.std.text.Formatting;

import java.io.File;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 从src目录创建脚本进程
 */
public class CommandScript extends BaseCommand {
    public CommandScript(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        List<String> scriptArgs = new ArrayList<>(args);

        try {
            File src = new File("C:/Users/Administrator/Desktop/src");
            if (!src.isDirectory()) {
                messager.white("未在在桌面上找到src目录");
                return;
            }
            ScriptProcess scriptProcess = new ScriptProcess(new SourceFileLoader("C:/Users/Administrator/Desktop/src"), scriptArgs);
            scriptProcess.start();
        } catch (Exception e) {
            e.printStackTrace();
            messager.send(Formatting.DARK_RED + getName() + MessageFormat.format(CMS.cms3, e.getMessage()));
        }
    }

    @Override
    public List<String> complete(List<String> args) {
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        return Literals.emptyFormat();
    }

    @Override
    public InfoHandler info(List<String> args) {
        return Literals.emptyInfo();
    }

    public List<String> pack(File dir, String p) {
        List<String> packs = new ArrayList<>();
        File[] files = dir.listFiles();
        List<File> fileList = new ArrayList<>();
        fileList.addAll(Arrays.asList(files));
        for (int i = 0; i < fileList.size(); i++) {
            File file = fileList.get(i);
            if (file.isFile()) {
                //是文件
                String name = file.getName();
                int ind = name.lastIndexOf('.');
                String abbr = name.substring(ind + 1);
                if (ind > 0 && (Engines.getLanguageOfAbbr(abbr) != null)) {
                    packs.add(abbr + ":" + p + name.substring(0, ind));
                }
            } else {
                //是目录
                packs.addAll(pack(file, p + file.getName() + "."));
            }
        }
        return packs;
    }

    public List<String> match(List<String> packs, String arg) {
        List<String> npacks = new ArrayList<>();
        for (String pack : packs) {
            if (pack.length() >= arg.length()) {
                if (pack.startsWith(arg)) {
                    npacks.add(pack);
                } else {
                    if (pack.contains(":")) {
                        String bpack = pack.substring(pack.indexOf(":") + 1);
                        if (bpack.startsWith(arg)) {
                            npacks.add(pack);
                        }
                    }
                }
            }
        }
        return npacks;
    }
}
