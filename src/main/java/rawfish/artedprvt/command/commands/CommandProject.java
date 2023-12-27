package rawfish.artedprvt.command.commands;

import rawfish.artedprvt.command.FormatMessager;
import rawfish.artedprvt.command.TaskProcess;
import rawfish.artedprvt.command.BaseCommand;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.util.FormatHandlerListBuilder;
import rawfish.artedprvt.std.cli.util.Literals;
import rawfish.artedprvt.work.Project;
import rawfish.artedprvt.work.ProjectInitializer;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

public class CommandProject extends BaseCommand {
    private static final ReentrantLock lock = new ReentrantLock();

    public CommandProject(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args, FormatMessager messager) {
        if (lock.isLocked()) {
            return;
        }
        new TaskProcess(this) {
            @Override
            public void run() {
                lock.lock();

                if (args.size() == 0) {
                    Project project = Project.project;
                    if (project != null) {
                        messager.gold(project.getDir());
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

                        Project project = Project.project;
                        if (project == null) {
                            messager.red("未打开项目");
                            return;
                        }

                        try {
                            project.initLog();
                        } catch (IOException e) {
                            messager.red("日志初始化失败");
                            return;
                        }

                        try {
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
                                messager.gold("加载成功");
                                return;
                            }


                            try {
                                messager.white("编译");
                                project.compile();
                            } catch (Exception e) {
                                messager.red("编译失败");
                                messager.red(e.getMessage());
                                messager.red("已失败");

                                e.printStackTrace();
                                return;
                            }
                            messager.white("编译成功");

                            try {
                                messager.white("加载");
                                project.load();
                            } catch (Exception e) {
                                messager.red("加载失败");
                                messager.red(e.getMessage());
                                messager.red("已失败");

                                e.printStackTrace();
                                return;
                            }
                            messager.gold("加载成功");

                            return;
                        } finally {
                            project.closeLog();
                        }
                    }
                    messager.red("无效命令");
                }
                if (args.size() == 2) {
                    String arg0 = args.get(0);
                    String arg1 = args.get(1);
                    if (arg0.equals("open")) {
                        File file = new File(arg1);
                        if (!file.isDirectory()) {
                            messager.red("不存在或不是目录");
                            return;
                        }
                        new Project(arg1);
                        messager.gold("已打开项目: " + arg1);
                        return;
                    }
                    if (arg0.equals("init")) {
                        Project project = Project.project;
                        if (project != null) {
                            ProjectInitializer initializer = ProjectInitializer.initializerMap.get(arg1);
                            if (initializer != null) {
                                project.init(initializer);
                            } else {
                                messager.red("无效初始化器");
                            }
                        } else {
                            messager.red("未打开项目");
                        }
                        return;
                    }
                }
                messager.red("参数异常");
            }

            @Override
            public void fil() {
                lock.unlock();
            }
        };
    }

    @Override
    public List<String> complete(List<String> args) {
        if (args.size() == 1) {
            return Literals.stringListBuilder().adds("open", "init", "load");
        }
        if (args.size() == 2) {
            String arg0 = args.get(0);
            if (arg0.equals("open")) {
                //打开项目 补全已知的可用的目录
                return Literals.stringListBuilder().adds("C:\\Users\\Administrator\\Desktop\\ap-test");
            }
            if (arg0.equals("init")) {
                //初始化 补全可用的初始化器
                return Literals.stringListBuilder().adds("script", "java");
            }
            if (arg0.equals("load")) {
                //加载
                return Literals.emptyComplete();
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
                builder.append("6");
                if (args.size() >= 2) {
                    builder.append("7");
                }
            }
            if (arg0.equals("init")) {
                builder.append("6");
                if (args.size() >= 2) {
                    String arg1 = args.get(1);
                    //暂时枚举
                    if (arg1.equals("script") || arg1.equals("java")) {
                        builder.append("a");
                    }
                }
            }
            if (arg0.equals("load")) {
                builder.append("d");
            }
        }
        return builder;
    }

    @Override
    public InfoHandler info(List<String> args) {
        if (args.size() == 1) {
            return Literals.infoFactory().map(
                    Literals.infoMapBuilder()
                            .string("open", "打开项目")
                            .string("init", "初始化项目")
                            .string("load", "加载项目"),
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
                return Literals.infoFactory().map(
                        Literals.infoMapBuilder()
                                .string("", "初始化器")
                                .string("script", "脚本初始化器 用于构建asp")
                                .string("java", "java初始化器 用于构建jar"),
                        Literals.infoFactory().string("找不到初始化器")
                );
            }
        }
        return Literals.emptyInfo();
    }
}
