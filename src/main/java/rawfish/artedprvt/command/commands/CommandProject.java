package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.BaseCommand;
import rawfish.artedprvt.command.FormatMessager;
import rawfish.artedprvt.command.TaskProcess;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.FormatHandlerListBuilder;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.std.text.Formatting;
import rawfish.artedprvt.work.Project;
import rawfish.artedprvt.work.ProjectInitializer;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class CommandProject extends BaseCommand {
    public CommandProject(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        new TaskProcess(this) {
            @Override
            public void run() {
                if (Project.lock.tryLock()) {
                    Project project = Project.project;
                    boolean isOpen = project != null;

                    if (args.size() == 0) {
                        if (isOpen) {
                            messager.gold("已打开项目: " + project.getDir());
                        } else {
                            messager.white("未打开项目");
                        }
                        return;
                    }
                    if (args.size() == 1) {
                        String arg0 = args.get(0);
                        if (arg0.equals("open")) {
                            messager.red("参数不够，需要文件目录。");
                            return;
                        }
                        if (arg0.equals("init")) {
                            messager.red("参数不够，需要初始化器。");
                            return;
                        }
                        if (arg0.equals("load")) {
                            //load

                            if (!isOpen) {
                                messager.red("未打开项目");
                                return;
                            }

                            if (!project.isInit()) {
                                messager.red("未初始化");
                                project.closeLog();
                                return;
                            }

                            try {
                                project.initLog();
                            } catch (IOException e) {
                                messager.red("日志初始化失败");
                                project.closeLog();
                                return;
                            }

                            try {
                                long time = System.currentTimeMillis();

                                boolean success = false;
                                a:
                                {
                                    try {
                                        messager.white("验证");
                                        project.verify();
                                    } catch (Exception e) {
                                        messager.red("验证失败");

                                        e.printStackTrace();
                                        break a;
                                    }
                                    messager.white("验证成功");

                                    try {
                                        messager.white("加载");
                                        project.load();
                                    } catch (Exception e) {
                                        messager.red("加载失败");

                                        e.printStackTrace();
                                        break a;
                                    }
                                    success = true;
                                }


                                if (!success) a:{
                                    try {
                                        messager.white("编译");
                                        project.compile();
                                    } catch (Exception e) {
                                        messager.red("编译失败");
                                        messager.red(e.getMessage());

                                        e.printStackTrace();
                                        break a;
                                    }
                                    messager.white("编译成功");

                                    try {
                                        messager.white("加载");
                                        project.load();
                                        success = true;
                                    } catch (Exception e) {
                                        messager.red("加载失败");
                                        messager.red(e.getMessage());

                                        e.printStackTrace();
                                    }
                                }

                                time = System.currentTimeMillis() - time;
                                if (success) {
                                    messager.gold("加载成功 (" + time + "ms)");
                                } else {
                                    messager.red("已失败 " + Formatting.GRAY + "(" + time + "ms)");
                                }
                                return;
                            } finally {
                                project.closeLog();
                            }
                        }
                        if (arg0.equals("close")) {
                            if (isOpen) {
                                project.close();
                                messager.white("已关闭项目");
                            } else {
                                messager.red("未打开项目");
                            }

                            return;
                        }
                        messager.red("无效命令");
                        return;
                    }
                    if (args.size() == 2) {
                        String arg0 = args.get(0);
                        String arg1 = args.get(1);
                        if (arg0.equals("open")) {
                            if (isOpen) {
                                messager.red("已存在打开的项目");
                                return;
                            }
                            File file = new File(arg1);
                            if (!file.isDirectory()) {
                                messager.red("不存在或不是目录");
                                return;
                            }
                            Project project1 = new Project(arg1);
                            messager.gold("已打开项目: " + project1.getDir());
                            return;
                        }
                        if (arg0.equals("init")) {
                            if (isOpen) {
                                ProjectInitializer initializer = ProjectInitializer.initializerMap.get(arg1);
                                if (initializer != null) {
                                    try {
                                        project.init(initializer);
                                        messager.gold("初始化完成");
                                    } catch (Exception e) {
                                        messager.red("初始化异常");
                                        messager.red(e.getMessage());
                                        e.printStackTrace();
                                    }
                                } else {

                                    URL url;
                                    try {
                                        url = new URL(arg1);
                                    } catch (MalformedURLException e) {
                                        if (new File(arg1).isFile()) {
                                            try {
                                                url = new URL("file:" + arg1);
                                            } catch (MalformedURLException ex) {
                                                messager.red("找不到初始化器");
                                                e.printStackTrace();
                                                return;
                                            }
                                        } else {
                                            messager.red("找不到初始化器");
                                            return;
                                        }
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream;
                                    try {
                                        messager.white("下载初始化器");
                                        URLConnection connection = url.openConnection();
                                        connection.setReadTimeout(10000);
                                        connection.connect();
                                        InputStream inputStream = connection.getInputStream();
                                        byteArrayOutputStream = new ByteArrayOutputStream();
                                        int n;
                                        while ((n = inputStream.read()) != -1) {
                                            byteArrayOutputStream.write(n);
                                        }
                                        messager.white("下载完成");
                                    } catch (UnknownServiceException e) {
                                        messager.red("协议不支持输入: " + url.getProtocol());
                                        e.printStackTrace();

                                        return;
                                    } catch (SocketTimeoutException e) {
                                        messager.red("连接超时 10000ms");
                                        e.printStackTrace();

                                        return;
                                    } catch (IOException e) {
                                        messager.red("I/O异常");
                                        e.printStackTrace();

                                        return;
                                    }
                                    try {
                                        initializer = new ProjectInitializer(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                                    } catch (Exception e) {
                                        messager.red("初始化器异常");
                                        messager.red(e.getMessage());

                                        return;
                                    }

                                    try {
                                        project.init(initializer);
                                        messager.gold("初始化完成");
                                    } catch (Exception e) {
                                        messager.red("初始化异常");
                                        messager.red(e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                messager.red("未打开项目");
                            }
                            return;
                        }
                    }
                    messager.red("参数异常");
                } else {
                    messager.red("有项目相关的任务正在执行");
                }
            }

            @Override
            public void fil() {
                Project.lock.unlock();
            }
        };
    }

    @Override
    public List<String> complete(List<String> args) {
        Project project = Project.project;
        if (args.size() == 1) {
            if (project != null) {
                return Literals.stringListBuilder().adds("load", "close", "open", "init");
            }
            return Literals.stringListBuilder().adds("open", "load", "close", "init");
        }
        if (args.size() == 2) {
            String arg0 = args.get(0);
            if (arg0.equals("open")) {
                //打开项目 补全已知的可用的目录
                return Literals.stringListBuilder().adds(
                        "C:/Users/Administrator/Desktop/script-test",
                        "C:/Users/Administrator/Desktop/java-test");
            }
            if (arg0.equals("init")) {
                //初始化 补全可用的初始化器
                return new ArrayList<>(ProjectInitializer.initializerMap.keySet());
            }
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        FormatHandlerListBuilder builder = Literals.formatListBuilder();
        if (args.size() >= 1) {
            String arg0 = args.get(0);
            if (arg0.equals("open")) {
                builder.append("a");
                if (args.size() >= 2) {
                    builder.append("7");
                }
            }
            if (arg0.equals("init")) {
                builder.append("6");
                if (args.size() >= 2) {
                    String arg1 = args.get(1);
                    ProjectInitializer initializer = ProjectInitializer.initializerMap.get(arg1);
                    if (initializer == null) {
                        URL url = null;
                        try {
                            url = new URL(arg1);
                        } catch (MalformedURLException e) {
                            if (new File(arg1).isFile()) {
                                try {
                                    url = new URL("file:" + arg1);
                                } catch (MalformedURLException ex) {
                                    ex.printStackTrace();
                                }
                            }
                        }
                        if (url == null) {
                            builder.append("4");
                        } else {
                            builder.add(urlFormatHandler);
                        }

                    } else {
                        builder.append("e");
                    }
                }
            }
            if (arg0.equals("load")) {
                builder.append("d");
            }
            if (arg0.equals("close")) {
                builder.append("c");
            }
        }
        return builder;
    }

    @Override
    public InfoHandler info(List<String> args) {
        Project project = Project.project;
        if (args.size() == 0) {
            if (project != null) {
                if (project.isLoaded()) {
                    return Literals.infoFactory().string("项目管理工具 §d[" + project.getDir() + "]");
                }
                return Literals.infoFactory().string("项目管理工具 §a[" + project.getDir() + "]");
            }
            return Literals.infoFactory().string("项目管理工具 §c未打开项目");
        }
        if (args.size() == 1) {
            return Literals.infoFactory().map(
                    Literals.infoMapBuilder()
                            .string("open", "打开项目")
                            .string("init", "初始化项目")
                            .string("load", "加载项目")
                            .string("close", "关闭项目"),
                    Literals.infoFactory().string("无效命令")
            );
        }
        if (args.size() == 2) {
            String arg0 = args.get(0);
            String arg1 = args.get(1);
            if (arg0.equals("open")) {
                //打开项目
                if (new File(arg1).isDirectory()) {
                    return Literals.emptyInfo();
                }
                return Literals.infoFactory().string("无效目录");
            }
            if (arg0.equals("init")) {
                //初始化
                if (arg1.isEmpty()) {
                    return Literals.infoFactory().string("选择内置初始化器或指定URL");
                }
                ProjectInitializer initializer = ProjectInitializer.initializerMap.get(arg1);
                if (initializer == null) {
                    URL url = null;
                    try {
                        url = new URL(arg1);
                    } catch (MalformedURLException e) {
                        if (new File(arg1).isFile()) {
                            try {
                                url = new URL("file:" + arg1);
                            } catch (MalformedURLException ex) {
                                ex.printStackTrace();
                            }
                        }
                    }
                    if (url == null) {
                        return Literals.infoFactory().string("无效URL");
                    }
                    return Literals.infoFactory().string("从此位置下载初始化器");
                }
                return Literals.infoFactory().string(initializer.getDescription());
            }
        }
        return Literals.emptyInfo();
    }

    public FormatHandler urlFormatHandler = Literals.formatFactory().regex(
            Pattern.compile("(?<protocol>file://|http://|https://|)(?<other>.*)"),
            "§?protocol§?other",
            Literals.formatMapBuilder()
                    .puts("protocol", Literals.formatFactory().append("9"))
                    .puts("other", Literals.formatFactory().append("7")),
            Literals.formatFactory().append("4")
    );
}
