package com.artedprvt.command.commands;

import com.artedprvt.core.app.AppProcess;
import com.artedprvt.command.BaseCommand;
import com.artedprvt.command.FormatMessager;
import com.artedprvt.core.app.App;
import com.artedprvt.core.app.AppTarget;
import com.artedprvt.core.app.AppType;
import com.artedprvt.core.app.Home;
import com.artedprvt.core.localization.types.CIS;
import com.artedprvt.core.localization.types.CMS;
import com.artedprvt.std.cli.CompleteInterface;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.FormatInterface;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.InfoInterface;
import com.artedprvt.std.cli.util.FormatHandlerListBuilder;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.std.text.Formatting;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class CommandApp extends BaseCommand {
    public CommandApp(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        if (args.size() == 0) {
            messager.red(CMS.cms2);
            return;
        }

        try {
            loadAppType(Home.app());
        } catch (Exception e) {
            e.printStackTrace();
        }
        String appPath = args.get(0);
        AppTarget<?> appTarget = nameAppTargetMap.get(appPath);
        if (appTarget == null) {
            messager.red(CMS.cms3);
            return;
        }
        try {
            AppProcess<?> appProcess = appTarget.open(args.subList(1, args.size()));
            appProcess.start();
        } catch (Exception e) {
            e.printStackTrace();
            messager.red(CMS.cms4);
        }

    }

    @Override
    public List<String> complete(List<String> args) {
        try {
            loadAppType(Home.app());
        } catch (Exception e) {
            e.printStackTrace();
            return Literals.emptyComplete();
        }
        if (args.size() == 1) {
            return new ArrayList<>(nameAppTargetMap.keySet());
        }
        String appPath = args.get(0);
        AppTarget<?> appTarget = nameAppTargetMap.get(appPath);
        if (appTarget == null) {
            return Literals.emptyComplete();
        }
        if (args.size() > 1) {
            Object result = appTarget.request("literal.complete");
            if (result instanceof CompleteInterface) {
                return ((CompleteInterface) result).complete(args.subList(1, args.size()));
            }
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        try {
            loadAppType(Home.app());
        } catch (Exception e) {
            e.printStackTrace();
            return Literals.emptyFormat();
        }
        FormatHandlerListBuilder builder = Literals.formatListBuilder();
        if (args.size() > 0) {
            String appPath = args.get(0);
            AppTarget<?> appTarget = nameAppTargetMap.get(appPath);
            if (appTarget == null) {
                return builder;
            }
            builder.empty();
            if (args.size() > 1) {
                Object result = appTarget.request("literal.format");
                if (result instanceof FormatInterface) {
                    builder.addAll(((FormatInterface) result).format(args.subList(1, args.size())));
                }
            }
        }
        return builder;
    }

    @Override
    public InfoHandler info(List<String> args) {
        try {
            loadAppType(Home.app());
        } catch (Exception e) {
            e.printStackTrace();
            return Literals.emptyInfo();
        }
        if (args.size() == 0) {
            return Literals.infoFactory().string(CIS.cis3);
        }
        String appPath = args.get(0);
        if (appPath.isEmpty()) {
            return Literals.infoFactory().string(CIS.cis4);
        }
        AppTarget<?> appTarget = nameAppTargetMap.get(appPath);
        if (appTarget == null) {
            return Literals.infoFactory().string(Formatting.RED+CIS.cis5);
        }
        Object result = appTarget.request("literal.info");
        if (result instanceof InfoInterface) {
            return ((InfoInterface) result).info(args.subList(1, args.size()));
        }

        return Literals.emptyInfo();
    }


    private Map<String, AppTarget<?>> nameAppTargetMap = new HashMap<>();

    private Map<String, Long> nameLastModifiedMap = new HashMap<>();

    private long time = 0;

    private synchronized void loadAppType(String appDir) throws Exception {
        long t = System.currentTimeMillis();
        if (t - time < 256) {
            return;
        }
        time = t;
        File app = new File(appDir);
        List<File> files = getAllFile(app);
        List<AppType<?>> appTypes = App.getAppTypes();
        Map<String, AppTarget<?>> nameAppTargetMap0 = new HashMap<>();
        Map<String, Long> nameLastModifiedMap0 = new HashMap<>();
        for (File file : files) {
            String name = getRelName(file, app);
            AppTarget<?> appTarget;
            long lastModified = file.lastModified();
            if ((appTarget = nameAppTargetMap.get(name)) != null) {
                long old = nameLastModifiedMap.get(name);
                if (old == lastModified) {
                    nameAppTargetMap0.put(name, appTarget);
                } else {
                    nameAppTargetMap0.put(name, appTarget.getAppType().target(file.toURI().toURL()));
                }
                nameLastModifiedMap0.put(name, lastModified);
                continue;
            }

            String e = getExtension(name);
            for (AppType<?> appType : appTypes) {
                if (appType.extensions().contains(e)) {
                    appTarget = appType.target(file.toURI().toURL());
                    nameAppTargetMap0.put(name, appTarget);
                    nameLastModifiedMap0.put(name, lastModified);
                    break;
                }
            }
        }

        nameAppTargetMap = nameAppTargetMap0;
        nameLastModifiedMap = nameLastModifiedMap0;
    }

    public List<File> getAllFile(File file) {
        List<File> files = new ArrayList<>();
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                files.addAll(getAllFile(f));
            }
        } else {
            files.add(file);
        }
        return files;
    }

    public String getRelName(File full, File base) {
        return base.toPath().relativize(full.toPath()).toString().replace(File.separatorChar, '/');
    }

    public File getFullFile(String relName, File base) {
        return new File(base, relName);
    }

    public String getExtension(String name) {
        int i = name.lastIndexOf('.');
        if (i < 0) {
            return "";
        }
        return name.substring(i + 1);
    }

    public FormatHandler appPathFormatHandler = Literals.formatFactory().regex(
            Pattern.compile("(?<name>.*)(?<dot>\\.)(?<ext>.*?)"),
            "§?name§?dot§?ext",
            Literals.formatMapBuilder()
                    .puts("name", Literals.formatFactory().append("7"))
                    .puts("dot", Literals.formatFactory().append("b"))
                    .puts("ext", Literals.formatFactory().append("7")),
            Literals.formatFactory().append("4")
    );
}
