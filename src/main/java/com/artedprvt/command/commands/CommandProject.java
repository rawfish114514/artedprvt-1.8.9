package com.artedprvt.command.commands;

import com.artedprvt.command.TaskProcess;
import com.artedprvt.core.localization.types.CIS;
import com.artedprvt.core.localization.types.CMS;
import com.artedprvt.std.cli.util.Literals;
import com.artedprvt.work.Project;
import com.artedprvt.work.ProjectInitializer;
import com.artedprvt.command.BaseCommand;
import com.artedprvt.command.FormatMessager;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.util.FormatHandlerListBuilder;
import com.artedprvt.std.text.Formatting;

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
import java.text.MessageFormat;
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
                            messager.gold(MessageFormat.format(CMS.cms5,project.getDir()));
                        } else {
                            messager.white(CMS.cms6);
                        }
                        return;
                    }
                    if (args.size() == 1) {
                        String arg0 = args.get(0);
                        if (arg0.equals("open")) {
                            messager.red(CMS.cms7);
                            return;
                        }
                        if (arg0.equals("init")) {
                            messager.red(CMS.cms8);
                            return;
                        }
                        if (arg0.equals("load")) {
                            //load

                            if (!isOpen) {
                                messager.red(CMS.cms6);
                                return;
                            }

                            if (!project.isInit()) {
                                messager.red(CMS.cms10);
                                project.closeLog();
                                return;
                            }

                            try {
                                project.initLog();
                            } catch (IOException e) {
                                messager.red(CMS.cms11);
                                project.closeLog();
                                return;
                            }

                            try {
                                long time = System.currentTimeMillis();

                                boolean success = false;
                                a:
                                {
                                    try {
                                        messager.white(CMS.cms12);
                                        project.verify();
                                    } catch (Exception e) {
                                        messager.red(CMS.cms13);

                                        e.printStackTrace();
                                        break a;
                                    }
                                    messager.white(CMS.cms14);

                                    try {
                                        messager.white(CMS.cms15);
                                        project.load();
                                    } catch (Exception e) {
                                        messager.red(CMS.cms16);

                                        e.printStackTrace();
                                        break a;
                                    }
                                    success = true;
                                }


                                if (!success) a:{
                                    try {
                                        messager.white(CMS.cms17);
                                        project.compile();
                                    } catch (Exception e) {
                                        messager.red(CMS.cms18);
                                        messager.red(e.getMessage());

                                        e.printStackTrace();
                                        break a;
                                    }
                                    messager.white(CMS.cms19);

                                    try {
                                        messager.white(CMS.cms20);
                                        project.load();
                                        success = true;
                                    } catch (Exception e) {
                                        messager.red(CMS.cms21);
                                        messager.red(e.getMessage());

                                        e.printStackTrace();
                                    }
                                }

                                time = System.currentTimeMillis() - time;
                                if (success) {
                                    messager.gold(MessageFormat.format(CMS.cms22,time));
                                } else {
                                    messager.red(MessageFormat.format(CMS.cms23,Formatting.GRAY+time));
                                }
                                return;
                            } finally {
                                project.closeLog();
                            }
                        }
                        if (arg0.equals("close")) {
                            if (isOpen) {
                                project.close();
                                messager.white(CMS.cms24);
                            } else {
                                messager.red(CMS.cms6);
                            }

                            return;
                        }
                        messager.red(CMS.cms26);
                        return;
                    }
                    if (args.size() == 2) {
                        String arg0 = args.get(0);
                        String arg1 = args.get(1);
                        if (arg0.equals("open")) {
                            if (isOpen) {
                                messager.red(CMS.cms27);
                                return;
                            }
                            File file = new File(arg1);
                            if (!file.isDirectory()) {
                                messager.red(CMS.cms28);
                                return;
                            }
                            Project project1 = new Project(arg1);
                            messager.gold(MessageFormat.format(CMS.cms5,project1.getDir()));
                            return;
                        }
                        if (arg0.equals("init")) {
                            if (isOpen) {
                                ProjectInitializer initializer = ProjectInitializer.initializerMap.get(arg1);
                                if (initializer != null) {
                                    try {
                                        project.init(initializer);
                                        messager.gold(CMS.cms29);
                                    } catch (Exception e) {
                                        messager.red(CMS.cms30);
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
                                                messager.red(CMS.cms31);
                                                e.printStackTrace();
                                                return;
                                            }
                                        } else {
                                            messager.red(CMS.cms31);
                                            return;
                                        }
                                    }
                                    ByteArrayOutputStream byteArrayOutputStream;
                                    try {
                                        messager.white(CMS.cms32);
                                        URLConnection connection = url.openConnection();
                                        connection.setReadTimeout(10000);
                                        connection.connect();
                                        InputStream inputStream = connection.getInputStream();
                                        byteArrayOutputStream = new ByteArrayOutputStream();
                                        int n;
                                        while ((n = inputStream.read()) != -1) {
                                            byteArrayOutputStream.write(n);
                                        }
                                        messager.white(CMS.cms33);
                                    } catch (UnknownServiceException e) {
                                        messager.red(MessageFormat.format(CMS.cms34,url.getProtocol()));
                                        e.printStackTrace();

                                        return;
                                    } catch (SocketTimeoutException e) {
                                        messager.red(CMS.cms35);
                                        e.printStackTrace();

                                        return;
                                    } catch (IOException e) {
                                        messager.red(CMS.cms36);
                                        e.printStackTrace();

                                        return;
                                    }
                                    try {
                                        initializer = new ProjectInitializer(new ByteArrayInputStream(byteArrayOutputStream.toByteArray()));
                                    } catch (Exception e) {
                                        messager.red(CMS.cms37);
                                        messager.red(e.getMessage());

                                        return;
                                    }

                                    try {
                                        project.init(initializer);
                                        messager.gold(CMS.cms29);
                                    } catch (Exception e) {
                                        messager.red(CMS.cms30);
                                        messager.red(e.getMessage());
                                        e.printStackTrace();
                                    }
                                }
                            } else {
                                messager.red(CMS.cms6);
                            }
                            return;
                        }
                    }
                    messager.red(CMS.cms38);
                } else {
                    messager.red(CMS.cms39);
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
                        "C:/Users/rawfish/Desktop/ideaProjects/i");
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
                    return Literals.infoFactory().string(MessageFormat.format(CIS.cis6,Formatting.LIGHT_PURPLE,project.getDir()));
                }
                return Literals.infoFactory().string(MessageFormat.format(CIS.cis6,Formatting.GREEN,project.getDir()));
            }
            return Literals.infoFactory().string(MessageFormat.format(CIS.cis6,Formatting.RED,CIS.cis7));
        }
        if (args.size() == 1) {
            return Literals.infoFactory().map(
                    Literals.infoMapBuilder()
                            .string("open", CIS.cis8)
                            .string("init", CIS.cis9)
                            .string("load", CIS.cis10)
                            .string("close", CIS.cis11),
                    Literals.infoFactory().string(CIS.cis2)
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
                return Literals.infoFactory().string(CIS.cis12);
            }
            if (arg0.equals("init")) {
                //初始化
                if (arg1.isEmpty()) {
                    return Literals.infoFactory().string(CIS.cis13);
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
                        return Literals.infoFactory().string(CIS.cis14);
                    }
                    return Literals.infoFactory().string(CIS.cis15);
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
