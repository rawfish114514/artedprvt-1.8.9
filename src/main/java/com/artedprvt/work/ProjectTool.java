package com.artedprvt.work;

import com.artedprvt.core.Environment;
import com.artedprvt.std.cli.ProcessInterface;
import com.artedprvt.work.anno.Command;
import com.artedprvt.work.anno.Exclude;
import com.artedprvt.work.anno.Goal;
import com.artedprvt.work.anno.Initializer;
import com.artedprvt.work.anno.Lifecycle;
import com.artedprvt.work.anno.Phase;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OptionalDataException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProjectTool {
    /**
     * 编译源码并输出字节码
     *
     * @param sourceMap   源码
     * @param mainClass   主类完整类名
     * @param bytesOutput 字节码输出流 最后关闭
     * @param log         日志输出流
     * @param args        参数
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     */
    public static void compile(Map<String, String> sourceMap, String mainClass, OutputStream bytesOutput, PrintWriter log, String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        try {
            if (args == null) {
                args = new String[0];
            }

            log.println("[COMPILE]");
            log.println("args: " + String.join(", ", args));

            log.println("<compile> java source");


            Map<String, byte[]> classByteMap = ClassByteTool.compile(sourceMap);
            ClassLoader classLoader = ClassByteTool.inMemoryCreate(classByteMap);
            Map<String, Class> map = new HashMap<>();
            for (String s : classByteMap.keySet()) {
                Class c = classLoader.loadClass(s);
                map.put(c.getName(), c);

                log.println(c);
            }

            log.println("</compile> java source");

            //处理Class ap
            log.println("<handle> main-class");

            String initializer_name = null;
            String initializer_description = null;
            String initializer_author = null;

            List<Class> phaseClasses = new ArrayList<>();
            List<Class> lifecycleClasses = new ArrayList<>();
            List<Class> goalClasses = new ArrayList<>();
            List<Class> commandClasses = new ArrayList<>();

            //验证Class mainClass 有唯一的注解 Initializer 且实现 ProjectAccess
            {
                Class c = map.get(mainClass);
                if (c == null) {
                    throw new RuntimeException("找不到: class " + mainClass);
                }
                if (!initializer(c)) {
                    throw new RuntimeException("class " + mainClass + " 不是 Initializer 单注解");
                }
                if (exclude(c)) {
                    throw new RuntimeException("class " + mainClass + " 排除了");
                }
                if (!ProjectAccess.class.isAssignableFrom(c)) {
                    throw new RuntimeException("class " + mainClass + " 未实现 ProjectAccess 接口");
                }
                Initializer initializer = single(Initializer.class, c);
                initializer_name = initializer.name();
                initializer_description = initializer.description();
                initializer_author = initializer.author();

                map.remove("com.artedprvt.work._0.ap");

                log.println("name: " + initializer_name);
                log.println("description: " + initializer_description);
                log.println("author: " + initializer_author);
            }

            log.println("</handle> main-class");

            log.println("<register>");

            //注册Phase
            {
                log.println("<register> Phase");

                for (Class c : map.values()) {
                    Phase phase = single(Phase.class, c);
                    if (phase != null) {
                        phaseClasses.add(c);

                        log.println(c);
                    }
                }

                log.println("</register> Phase");
            }

            //注册Lifecycle
            {
                log.println("<register> Lifecycle");

                for (Class c : map.values()) {
                    Lifecycle lifecycle = single(Lifecycle.class, c);
                    if (lifecycle != null) {
                        lifecycleClasses.add(c);

                        log.println(c);
                    }
                }

                log.println("</register> Lifecycle");
            }

            //注册Goal
            {
                log.println("<register> Goal");

                for (Class c : map.values()) {
                    Goal goal = single(Goal.class, c);
                    if (goal != null) {
                        goalClasses.add(c);

                        log.println(c);
                    }
                }

                log.println("</register> Goal");
            }

            //注册Command
            {
                log.println("<register> Command");

                for (Class c : map.values()) {
                    Command command = single(Command.class, c);
                    if (command != null) {
                        commandClasses.add(c);

                        log.println(c);
                    }
                }

                log.println("</register> Command");
            }

            log.println("</register>");


            Map<Class, PhaseData> phaseDataMap = new HashMap<>();
            Map<Class, LifecycleData> lifecycleDataMap = new HashMap<>();
            Map<Class, GoalData> goalDataMap = new HashMap<>();
            Map<Class, CommandData> commandDataMap = new HashMap<>();

            //验证并提取数据
            log.println("<data>");

            {
                log.println("<data> Phase");

                for (Class c : phaseClasses) {
                    PhaseData phaseData = new PhaseData();
                    phaseData.className = c.getName();
                    phaseData.phaseName = named("Phase", c);
                    phaseDataMap.put(c, phaseData);

                    log.println(c);
                }

                log.println("</data> Phase");
            }

            {
                log.println("<data> Lifecycle");

                for (Class c : lifecycleClasses) {
                    LifecycleData lifecycleData = new LifecycleData();
                    lifecycleData.className = c.getName();
                    lifecycleData.lifecycleName = named("Lifecycle", c);
                    Lifecycle lifecycle = single(Lifecycle.class, c);
                    Class[] classes = lifecycle.value();
                    lifecycleData.phaseNames = new String[classes.length];
                    for (int i = 0; i < classes.length; i++) {
                        if (phaseClasses.contains(classes[i])) {
                            lifecycleData.phaseNames[i] = phaseDataMap.get(classes[i]).phaseName;
                        } else {
                            throw new RuntimeException(c.getName() + " Lifecycle 注解参数必须是使用 Phase 注解的类");
                        }
                    }
                    lifecycleDataMap.put(c, lifecycleData);

                    log.println(c);
                }

                log.println("</data> Lifecycle");
            }

            {
                log.println("<data> Goal");

                for (Class c : goalClasses) {
                    GoalData goalData = new GoalData();
                    goalData.className = c.getName();
                    goalData.goalName = named("Goal", c);
                    Goal goal = single(Goal.class, c);
                    Class clas = goal.value();
                    if (clas == Goal.class) {
                        goalData.phaseName = "null;";
                    } else {
                        if (phaseClasses.contains(clas)) {
                            goalData.phaseName = phaseDataMap.get(clas).phaseName;
                        } else {
                            throw new RuntimeException(c.getName() + " Goal 注解参数必须是使用 Phase 注解的类或 Goal.class");
                        }
                    }
                    goalDataMap.put(c, goalData);

                    log.println(c);
                }

                log.println("</data> Goal");
            }

            {
                log.println("<data> Command");

                for (Class c : commandClasses) {
                    CommandData commandData = new CommandData();
                    commandData.className = c.getName();
                    commandData.commandName = named("Command", c);

                    commandDataMap.put(c, commandData);

                    log.println(c);
                }

                log.println("</data> Command");
            }

            //验证 Goal 注解的类必须实现 ProcessInterface 接口
            {
                log.println("<interface-goal>");

                for (Class c : goalClasses) {
                    if (!ProcessInterface.class.isAssignableFrom(c)) {
                        throw new RuntimeException(c.getName() + " Goal 必须实现 ProcessInterface 接口");
                    }
                    log.println(c);
                }

                log.println("</interface-goal>");
            }

            //验证 所有 Phase 都被 Lifecycle 包含且没有被重复包含
            {
                log.println("<phase-in-lifecycle>");

                List<String> usedPhases = new ArrayList<>();
                for (LifecycleData lifecycleData : lifecycleDataMap.values()) {
                    log.println("for " + lifecycleData);

                    for (String phaseName : lifecycleData.phaseNames) {
                        if (usedPhases.contains(phaseName)) {
                            throw new RuntimeException(lifecycleData.className + " Lifecycle 包含的 Phase 被包含多次");
                        }
                        usedPhases.add(phaseName);

                        log.println(phaseName);
                    }
                }
                if (usedPhases.size() < phaseClasses.size()) {
                    throw new RuntimeException("存在不被包含的 Phase");
                }

                log.println("</phase-in-lifecycle>");
            }

            //验证 所有 Phase 名和 Command 名不重复
            {
                log.println("<lifecycle-command-name-unequals>");

                List<String> names = new ArrayList<>();
                for (PhaseData phaseData : phaseDataMap.values()) {
                    String name = phaseData.phaseName;
                    if (names.contains(name)) {
                        throw new RuntimeException("Phase 或 Command 名重复: " + name);
                    }
                    names.add(name);

                    log.println(name);
                }
                for (CommandData commandData : commandDataMap.values()) {
                    String name = commandData.commandName;
                    if (names.contains(name)) {
                        throw new RuntimeException("Phase 或 Command 名重复: " + name);
                    }
                    names.add(name);

                    log.println(name);
                }

                log.println("</lifecycle-command-name-unequals>");
            }


            //检查排除
            {
                log.println("<exclude>");

                Set<Class> lifecycleCatchSet = new HashSet<>();
                Set<Class> phaseCatchSet = new HashSet<>();
                Set<Class> goalCatchSet = new HashSet<>();
                Set<Class> commandCatchSet = new HashSet<>();

                for (Class lifecycleClass : lifecycleClasses) {
                    if (exclude(lifecycleClass)) {
                        //排除生命周期
                        LifecycleData lifecycleData = lifecycleDataMap.get(lifecycleClass);
                        lifecycleCatchSet.add(lifecycleClass);

                        log.println(lifecycleData);

                        //排除生命周期的阶段
                        for (String phaseName : lifecycleData.phaseNames) {
                            for (Class phaseClass : phaseClasses) {
                                PhaseData phaseData = phaseDataMap.get(phaseClass);
                                if (phaseData.phaseName.equals(phaseName)) {
                                    phaseCatchSet.add(phaseClass);
                                    break;
                                }
                            }
                        }

                        //排除生命周期的阶段的目标
                        for (Class phaseClass : phaseCatchSet) {
                            PhaseData phaseData = phaseDataMap.get(phaseClass);
                            for (Class goalClass : goalClasses) {
                                GoalData goalData = goalDataMap.get(goalClass);
                                if (goalData.phaseName.equals(phaseData.phaseName)) {
                                    goalCatchSet.add(goalClass);
                                    break;
                                }
                            }
                        }
                    }
                }

                for (Class phaseClass : phaseClasses) {
                    if (exclude(phaseClass)) {
                        //排除阶段
                        phaseCatchSet.add(phaseClass);
                    }
                }

                for (Class lifecycleClass : lifecycleClasses) {
                    //排除阶段的生命周期的此阶段
                    LifecycleData lifecycleData = lifecycleDataMap.get(lifecycleClass);
                    List<String> phaseNameList = new ArrayList<>(Arrays.asList(lifecycleData.phaseNames));
                    for (Class phaseClass : phaseCatchSet) {
                        PhaseData phaseData = phaseDataMap.get(phaseClass);
                        phaseNameList.remove(phaseData.phaseName);
                    }
                    lifecycleData.phaseNames = phaseNameList.toArray(new String[phaseNameList.size()]);
                }

                for (Class goalClass : goalClasses) {
                    if (exclude(goalClass)) {
                        //排除目标
                        goalCatchSet.add(goalClass);
                    }
                }

                for (Class commandClass : commandClasses) {
                    if (exclude(commandClass)) {
                        //排除目标
                        commandCatchSet.add(commandClass);
                    }
                }

                //移除
                lifecycleCatchSet.forEach(e -> log.println(lifecycleDataMap.get(e)));
                phaseCatchSet.forEach(e -> log.println(phaseDataMap.get(e)));
                goalCatchSet.forEach(e -> log.println(goalDataMap.get(e)));
                commandCatchSet.forEach(e -> log.println(commandDataMap.get(e)));

                lifecycleCatchSet.forEach(lifecycleDataMap::remove);
                phaseCatchSet.forEach(phaseDataMap::remove);
                goalCatchSet.forEach(goalDataMap::remove);
                commandCatchSet.forEach(commandDataMap::remove);


                log.println("</exclude>");
            }


            log.println("</data>");


            //构造最终字节数组
            {
                log.println("<block>");

                //类数据块
                ByteArrayOutputStream classBlock = new ByteArrayOutputStream();
                {
                    log.println("<block-class>");

                    ObjectOut out = new ObjectOut(classBlock);

                    //类数据块开始
                    out.writeString("class start");

                    //对于每个类 写入 "class" 写入类名 写入类字节大小 写入类字节
                    for (String name : classByteMap.keySet()) {
                        out.writeString("class");
                        out.writeString(name);
                        byte[] classBytes = classByteMap.get(name);
                        out.writeInt(classBytes.length);
                        out.write(classBytes);

                        log.println(name);
                        log.println(classBytes.length + " byte");
                    }

                    //类数据块结束
                    out.writeString("class end");
                    out.flush();

                    log.println("</block-class>");
                }

                //类注册数据块
                ByteArrayOutputStream registerBlock = new ByteArrayOutputStream();
                {
                    log.println("<block-register>");

                    ObjectOut out = new ObjectOut(registerBlock);

                    //类注册数据块开始
                    out.writeString("register start");

                    //对于每个phase 写入"phase" 写入类名 写入phase名
                    log.println("<block-register> Phase");

                    for (PhaseData phaseData : phaseDataMap.values()) {
                        out.writeString("phase");
                        out.writeString(phaseData.className);
                        out.writeString(phaseData.phaseName);

                        log.println(phaseData.className);
                    }

                    log.println("</block-register> Phase");

                    //对于每个lifecycle 写入"lifecycle" 写入类名 写入lifecycle名 写入phase名数组大小 写入phase名数组
                    log.println("<block-register> Lifecycle");

                    for (LifecycleData lifecycleData : lifecycleDataMap.values()) {
                        out.writeString("lifecycle");
                        out.writeString(lifecycleData.className);
                        out.writeString(lifecycleData.lifecycleName);
                        out.writeInt(lifecycleData.phaseNames.length);
                        for (int i = 0; i < lifecycleData.phaseNames.length; i++) {
                            out.writeString(lifecycleData.phaseNames[i]);
                        }

                        log.println(lifecycleData.className);
                    }

                    log.println("</block-register> Lifecycle");

                    //对于每个goal 写入"goal" 写入类名 写入goal名 写入phase名
                    log.println("<block-register> Goal");

                    for (GoalData goalData : goalDataMap.values()) {
                        out.writeString("goal");
                        out.writeString(goalData.className);
                        out.writeString(goalData.goalName);
                        out.writeString(goalData.phaseName);

                        log.println(goalData.className);
                    }

                    log.println("</block-register> Goal");

                    //对于每个Command 写入"command" 写入类名 写入command名
                    log.println("<block-register> Command");

                    for (CommandData commandData : commandDataMap.values()) {
                        out.writeString("command");
                        out.writeString(commandData.className);
                        out.writeString(commandData.commandName);

                        log.println(commandData.className);
                    }

                    log.println("</block-register> Command");

                    //类注册数据块结束
                    out.writeString("register end");
                    out.flush();

                    log.println("</block-register>");
                }


                byte[] classBlockBytes = classBlock.toByteArray();
                byte[] registerBlockBytes = registerBlock.toByteArray();
                int classBlockByteSize = classBlockBytes.length;
                int registerBlockByteSize = registerBlockBytes.length;
                byte[] checksum;

                //计算核心字节和源码校验和
                {
                    checksum = checksum(unionSource(sourceMap), classBlockBytes, registerBlockBytes);

                    log.println("checksum " + new BigInteger(1, checksum).toString(16));
                }

                byte[] headBlockBytes;
                int headBlockByteSize;


                ByteArrayOutputStream headBlock = new ByteArrayOutputStream();
                //头数据块
                {
                    log.println("<block-head>");

                    //写入文件头 0x1145140019198100

                    headBlock.write(new byte[]{
                            0x11, 0x45, 0x14, 0x00,
                            0x19, 0x19, -0x7f, 0x00,
                    });

                    //写入apf版本校验 64字节
                    byte[] apfcheck = apfcheck();
                    headBlock.write(apfcheck);

                    log.println("apfcheck " + new BigInteger(1, apfcheck).toString(16));

                    //写入虚拟机版本校验 64字节
                    byte[] vmcheck = vmcheck();
                    headBlock.write(vmcheck);

                    log.println("vmcheck " + new BigInteger(1, vmcheck).toString(16));

                    ObjectOut out = new ObjectOut(headBlock);

                    //写入项目脚本的名称描述作者 写入类数据块大小 写入注册数据块大小 写入校验和 写入头数据块大小 写入填充大小
                    out.writeString(initializer_name);
                    out.writeString(initializer_description);
                    out.writeString(initializer_author);
                    out.writeInt(classBlockByteSize);
                    out.writeInt(registerBlockByteSize);
                    out.write(checksum);

                    out.flush();

                    int block = 512;
                    int size = Math.floorDiv(headBlock.size() + 7 + block, block) * block;
                    int comp = size - headBlock.size() - 8;
                    headBlock.write(size >> 24);
                    headBlock.write(size >> 16);
                    headBlock.write(size >> 8);
                    headBlock.write(size);
                    headBlock.write(comp >> 24);
                    headBlock.write(comp >> 16);
                    headBlock.write(comp >> 8);
                    headBlock.write(comp);
                    int _i = comp;
                    while (_i-- > 0) {
                        headBlock.write(0);
                    }

                    out.flush();
                    if (size != headBlock.size()) {
                        throw new RuntimeException("头数据块大小异常");
                    }

                    headBlockBytes = headBlock.toByteArray();
                    headBlockByteSize = headBlockBytes.length;

                    log.println("</block-head>");
                    log.println("size: " + headBlockByteSize + " byte");
                    log.println("comp: " + comp + " byte");
                }

                log.println("</block>");

                byte[] finalBytes = new byte[headBlockByteSize + classBlockByteSize + registerBlockByteSize];
                //最终数据
                {
                    System.arraycopy(headBlockBytes, 0, finalBytes, 0, headBlockByteSize);
                    System.arraycopy(classBlockBytes, 0, finalBytes, headBlockByteSize, classBlockByteSize);
                    System.arraycopy(registerBlockBytes, 0, finalBytes, headBlockByteSize + classBlockByteSize, registerBlockByteSize);
                }

                //写入到输出流
                bytesOutput.write(finalBytes);

                log.println("[COMPILE] SUCCESSFUL");
            }
        } catch (Exception e) {
            e.printStackTrace(log);
            log.println("<[COMPILE] FAILED");
            throw e;
        } finally {
            bytesOutput.close();
            log.flush();
        }
    }

    /**
     * 验证源码和字节码正确性
     *
     * @param sourceMap  源码
     * @param bytesInput 字节码输入流 最后关闭
     * @param log        日志输出流
     * @param args       参数
     * @throws IOException
     * @throws ClassNotFoundException
     * @throws NoSuchAlgorithmException
     */
    public static void verify(Map<String, String> sourceMap, InputStream bytesInput, PrintWriter log, String[] args) throws IOException, ClassNotFoundException, NoSuchAlgorithmException {
        try {
            if (args == null) {
                args = new String[0];
            }

            log.println("[VERIFY]");
            log.println("args: " + String.join(", ", args));

            DataInputStream input = new DataInputStream(bytesInput);

            log.println("<verify>");

            //检查文件头 0x1145140019198100
            {
                if (input.read() == 0x11
                        && input.read() == 0x45
                        && input.read() == 0x14
                        && input.read() == 0x00
                        && input.read() == 0x19
                        && input.read() == 0x19
                        && input.read() == 0x81
                        && input.read() == 0x00
                ) {
                } else {
                    throw new RuntimeException("读取不正确 文件头不正确 期望 0x1145140019198100");
                }
            }

            //检查apf版本校验 64字节
            {
                log.println("verify apfcheck");

                byte[] apfcheck0 = apfcheck();
                byte[] apfcheck1 = new byte[64];
                input.readFully(apfcheck1);

                if (Arrays.equals(apfcheck0, apfcheck1)) {
                } else {
                    throw new RuntimeException("apf校验不一致 apf已发生变化");
                }

                log.println(new BigInteger(1, apfcheck0).toString(16));
            }

            //检查虚拟机版本校验 64字节
            {
                log.println("verify vmcheck");

                byte[] vmcheck0 = vmcheck();
                byte[] vmcheck1 = new byte[64];
                input.readFully(vmcheck1);

                if (Arrays.equals(vmcheck0, vmcheck1)) {
                } else {
                    throw new RuntimeException("虚拟机校验不一致 虚拟机版本已发生变化");
                }

                log.println(new BigInteger(1, vmcheck0).toString(16));
            }

            String initializer_name;
            String initializer_description;
            String initializer_author;

            int classBlockByteSize;
            int registerBlockByteSize;
            byte[] checksum;
            int headBlockByteSize;
            int compSize;

            //读取项目脚本的名称描述作者 读取类数据块大小 读取注册数据块大小 读取校验和 读取头数据块大小 读取填充大小
            {
                ObjectIn in = new ObjectIn(input);

                initializer_name = in.readString();
                initializer_description = in.readString();
                initializer_author = in.readString();

                classBlockByteSize = in.readInt();
                registerBlockByteSize = in.readInt();
                checksum = new byte[128];
                in.readFully(checksum);

                headBlockByteSize =
                        ((input.read() & 0xff) << 24)
                                + ((input.read() & 0xff) << 16)
                                + ((input.read() & 0xff) << 8)
                                + ((input.read() & 0xff));

                compSize =
                        ((input.read() & 0xff) << 24)
                                + ((input.read() & 0xff) << 16)
                                + ((input.read() & 0xff) << 8)
                                + ((input.read() & 0xff));

                if (Math.floorMod(headBlockByteSize, 512) != 0) {
                    throw new RuntimeException("逻辑错误 头数据块 size 不是 512 的整数倍");
                }

                int _i = compSize;
                while (_i-- > 0) {
                    if (input.read() != 0) {
                        throw new RuntimeException("读取不正确 填充字节不是 0");
                    }
                }

                log.println("name: " + initializer_name);
                log.println("description: " + initializer_description);
                log.println("author: " + initializer_author);
                log.println("class: " + classBlockByteSize + " byte");
                log.println("register: " + registerBlockByteSize + " byte");
                log.println("head: " + headBlockByteSize + " byte");
                log.println("comp: " + compSize + " byte");
            }

            byte[] classBlockBytes = new byte[classBlockByteSize];

            //读取类数据块
            {
                log.println("read class block");

                input.readFully(classBlockBytes);
            }

            byte[] registerBlockBytes = new byte[registerBlockByteSize];

            //读取类注册数据块
            {
                log.println("read register block");

                input.readFully(registerBlockBytes);
            }

            if (input.read() != -1) {
                throw new RuntimeException("读取结束但流未结束");
            }

            log.println("read ended");

            //验证核心字节和源码校验和
            {
                log.println("verify checksum");

                byte[] checksum0 = checksum(unionSource(sourceMap), classBlockBytes, registerBlockBytes);
                if (!Arrays.equals(checksum0, checksum)) {
                    throw new RuntimeException("核心字节校验和不一致 源码或字节码发生变化");
                }

                log.println(new BigInteger(1, checksum0).toString(16));
            }

            log.println("</verfiy>");

            log.println("[VERIFY] SUCCESSFUL");
        } catch (Exception e) {
            e.printStackTrace(log);
            log.println("<[VERIFY] FAILED");
            throw e;
        } finally {
            bytesInput.close();
            log.flush();
        }
    }

    /**
     * 加载字节码
     *
     * @param mainClass     主类
     * @param projectSystem 项目系统
     * @param bytesInput    字节码输入流 最后关闭
     * @param log           日志输出流
     * @param args          参数
     * @return
     * @throws IOException
     * @throws NoSuchAlgorithmException
     * @throws ClassNotFoundException
     */
    public static WorkRuntime load(String mainClass, ProjectSystem projectSystem, InputStream bytesInput, PrintWriter log, String[] args) throws IOException, NoSuchAlgorithmException, ClassNotFoundException {
        try {
            if (args == null) {
                args = new String[0];
            }

            log.println("[LOAD]");
            log.println("args: " + String.join(", ", args));

            DataInputStream input = new DataInputStream(bytesInput);

            log.println("<load>");

            //检查文件头 0x1145140019198100
            {
                if (input.read() == 0x11
                        && input.read() == 0x45
                        && input.read() == 0x14
                        && input.read() == 0x00
                        && input.read() == 0x19
                        && input.read() == 0x19
                        && input.read() == 0x81
                        && input.read() == 0x00
                ) {
                } else {
                    throw new RuntimeException("读取不正确 文件头不正确 期望 0x1145140019198100");
                }
            }

            //检查apf版本校验 64字节
            {
                byte[] apfcheck0 = apfcheck();
                byte[] apfcheck1 = new byte[64];
                input.readFully(apfcheck1);

                if (Arrays.equals(apfcheck0, apfcheck1)) {
                } else {
                    throw new RuntimeException("apf校验不一致 apf已发生变化");
                }

                log.println("verify apfcheck " + new BigInteger(1, apfcheck0).toString(16));
            }

            //检查虚拟机版本校验 64字节
            {
                byte[] vmcheck0 = vmcheck();
                byte[] vmcheck1 = new byte[64];
                input.readFully(vmcheck1);

                if (Arrays.equals(vmcheck0, vmcheck1)) {
                } else {
                    throw new RuntimeException("虚拟机校验不一致 虚拟机版本已发生变化 重新编译");
                }

                log.println("verify vmcheck " + new BigInteger(1, vmcheck0).toString(16));
            }

            String initializer_name;
            String initializer_description;
            String initializer_author;

            int classBlockByteSize;
            int registerBlockByteSize;
            byte[] checksum;
            int headBlockByteSize;
            int compSize;

            //读取项目脚本的名称描述作者 读取类数据块大小 读取注册数据块大小 读取校验和 读取头数据块大小 读取填充大小
            {
                ObjectIn in = new ObjectIn(input);

                initializer_name = in.readString();
                initializer_description = in.readString();
                initializer_author = in.readString();

                classBlockByteSize = in.readInt();
                registerBlockByteSize = in.readInt();
                checksum = new byte[128];
                in.readFully(checksum);

                headBlockByteSize =
                        ((input.read() & 0xff) << 24)
                                + ((input.read() & 0xff) << 16)
                                + ((input.read() & 0xff) << 8)
                                + ((input.read() & 0xff));

                compSize =
                        ((input.read() & 0xff) << 24)
                                + ((input.read() & 0xff) << 16)
                                + ((input.read() & 0xff) << 8)
                                + ((input.read() & 0xff));

                if (Math.floorMod(headBlockByteSize, 512) != 0) {
                    throw new RuntimeException("逻辑错误 头数据块 size 不是 512 的整数倍");
                }

                int _i = compSize;
                while (_i-- > 0) {
                    if (input.read() != 0) {
                        throw new RuntimeException("读取不正确 填充字节不是 0");
                    }
                }

                log.println("name: " + initializer_name);
                log.println("description: " + initializer_description);
                log.println("author: " + initializer_author);
                log.println("class: " + classBlockByteSize + " byte");
                log.println("register: " + registerBlockByteSize + " byte");
                log.println("head: " + headBlockByteSize + " byte");
                log.println("comp: " + compSize + " byte");
            }

            byte[] classBlockBytes = new byte[classBlockByteSize];

            Map<String, byte[]> classByteMap = new HashMap<>();

            //读取类数据块
            {
                log.println("<read-block-class>");

                input.readFully(classBlockBytes);
                ObjectIn in = new ObjectIn(new ByteArrayInputStream(classBlockBytes));

                //类数据块开始
                if (!in.readString().equals("class start")) {
                    throw new RuntimeException("读取不正确 类数据块不正确 期望 \"class start\"");
                }
                try {

                    while (true) {
                        String token = in.readString();
                        if (token.equals("class end")) {
                            break;
                        }
                        if (token.equals("class")) {
                            String name = in.readString();
                            int size = in.readInt();
                            byte[] bytes = new byte[size];
                            in.readFully(bytes);

                            classByteMap.put(name, bytes);

                            log.println(name);
                            log.println(size + " byte");
                            continue;
                        }
                        throw new RuntimeException("读取不正确 类数据块不正确 期望 \"class\" 或 \"class end\"");
                    }
                } catch (OptionalDataException e) {
                    e.printStackTrace();
                    throw e;
                }

                log.println("</read-block-class>");
            }

            byte[] registerBlockBytes = new byte[registerBlockByteSize];

            List<PhaseData> phaseDataList = new ArrayList<>();
            List<LifecycleData> lifecycleDataList = new ArrayList<>();
            List<GoalData> goalDataList = new ArrayList<>();
            List<CommandData> commandDataList = new ArrayList<>();

            //读取类注册数据块
            {
                log.println("<read-block-register>");

                input.readFully(registerBlockBytes);
                ObjectIn in = new ObjectIn(new ByteArrayInputStream(registerBlockBytes));

                //类注册数据块块开始
                if (!in.readString().equals("register start")) {
                    throw new RuntimeException("读取不正确 类注册数据块不正确 期望 \"register start\"");
                }

                while (true) {
                    String token = in.readString();
                    if (token.equals("register end")) {
                        break;
                    }
                    if (token.equals("phase")) {
                        PhaseData phaseData = new PhaseData();
                        phaseData.className = in.readString();
                        phaseData.phaseName = in.readString();

                        phaseDataList.add(phaseData);

                        log.println(phaseData);

                        continue;
                    }
                    if (token.equals("lifecycle")) {
                        LifecycleData lifecycleData = new LifecycleData();
                        lifecycleData.className = in.readString();
                        lifecycleData.lifecycleName = in.readString();
                        int length = in.readInt();
                        lifecycleData.phaseNames = new String[length];
                        for (int i = 0; i < length; i++) {
                            lifecycleData.phaseNames[i] = in.readString();
                        }

                        lifecycleDataList.add(lifecycleData);

                        log.println(lifecycleData);

                        continue;
                    }
                    if (token.equals("goal")) {
                        GoalData goalData = new GoalData();
                        goalData.className = in.readString();
                        goalData.goalName = in.readString();
                        goalData.phaseName = in.readString();

                        goalDataList.add(goalData);

                        log.println(goalData);

                        continue;
                    }
                    if (token.equals("command")) {
                        CommandData commandData = new CommandData();
                        commandData.className = in.readString();
                        commandData.commandName = in.readString();

                        commandDataList.add(commandData);

                        log.println(commandData);

                        continue;
                    }
                    throw new RuntimeException("读取不正确 类注册数据块不正确 期望 \"phase\" 或 \"lifecycle\" 或 \"goal\" 或 \"command\" 或\"register end\"");
                }

                log.println("</read-block-register>");
            }

            if (input.read() != -1) {
                throw new RuntimeException("读取结束但流未结束");
            }

            log.println("read ended");

            log.println("</load>");

            WorkRuntime workRuntime = new WorkRuntime(mainClass, projectSystem, ClassByteTool.inMemoryCreate(classByteMap), phaseDataList, lifecycleDataList, goalDataList, commandDataList);

            log.println("[LOAD] SUCCESSFUL");

            return workRuntime;
        } catch (RuntimeException e) {
            e.printStackTrace(log);
            log.println("<[LOAD] FAILED");
            throw e;
        } finally {
            bytesInput.close();
            log.flush();
        }
    }

    /**
     * 计算 apf 版本校验
     * 使用SHA512
     * 64 byte
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] apfcheck() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        String vms = new StringBuilder()
                .append(Environment.MODID).append(';')
                .append(Environment.MODNAME).append(';')
                .append(Environment.MODVERSION).append(';')
                .append(Environment.MCVERSION).append(';').toString();
        return digest.digest(vms.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * 计算虚拟机版本校验
     * 使用SHA512
     * 64 byte
     *
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] vmcheck() throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        String vms = new StringBuilder()
                .append(System.getProperty("java.version")).append(';')
                .append(System.getProperty("java.vendor")).append(';')
                .append(System.getProperty("java.vm.version")).append(';')
                .append(System.getProperty("java.vm.vendor")).append(';')
                .append(System.getProperty("java.vm.name")).append(';').toString();
        return digest.digest(vms.getBytes(StandardCharsets.UTF_8));
    }

    private static String unionSource(Map<String, String> sourceMap) {
        List<String> keyList = new ArrayList<>(sourceMap.keySet());
        Collections.sort(keyList);
        StringBuilder sb = new StringBuilder();
        for (String key : keyList) {
            sb.append(key);
            sb.append(".");
            sb.append(sourceMap.get(key));
        }
        return sb.toString();
    }

    /**
     * 计算核心字节码校验和
     * 使用SHA512
     * 128 byte
     *
     * @param source             源码
     * @param classBlockBytes    类数据块
     * @param registerBlockBytes 类注册数据块
     * @return
     * @throws NoSuchAlgorithmException
     */
    private static byte[] checksum(String source, byte[] classBlockBytes, byte[] registerBlockBytes) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] checkSource = digest.digest(source.getBytes(StandardCharsets.UTF_8));
        digest.update(classBlockBytes);
        digest.update(registerBlockBytes);
        byte[] checkCoreByte = digest.digest();
        byte[] checksum = new byte[128];
        System.arraycopy(checkCoreByte, 0, checksum, 0, checkCoreByte.length);
        System.arraycopy(checkSource, 0, checksum, checkCoreByte.length, checkSource.length);

        return checksum;
    }


    /**
     * 提取注解
     * Phase,Lifecycle,Goal,Command,Initializer
     *
     * @param annotations
     * @return
     */
    private static Annotation[] filter(Annotation[] annotations) {
        List<Annotation> list = new ArrayList<>();
        for (Annotation annotation : annotations) {
            Class c = annotation.annotationType();
            if (c == Phase.class
                    || c == Lifecycle.class
                    || c == Goal.class
                    || c == Command.class
                    || c == Initializer.class) {
                list.add(annotation);
            }
        }
        return list.toArray(new Annotation[0]);
    }

    /**
     * Initializer 单注解
     *
     * @param annotatedElement
     * @return
     */
    private static boolean initializer(AnnotatedElement annotatedElement) {
        Annotation[] annotations = filter(annotatedElement.getAnnotations());
        return annotations.length == 1 && annotations[0].annotationType() == Initializer.class;
    }

    /**
     * 包含 Exclude 注解
     *
     * @param annotatedElement
     * @return
     */
    private static boolean exclude(AnnotatedElement annotatedElement) {
        for (Annotation annotation : annotatedElement.getAnnotations()) {
            Class c = annotation.annotationType();
            if (c == Exclude.class) {
                return true;
            }
        }
        return false;
    }

    /**
     * 单注解
     *
     * @param tClass           注解类型
     * @param annotatedElement
     * @param <T>
     * @return
     */
    private static <T extends Annotation> T single(Class<T> tClass, AnnotatedElement annotatedElement) {
        Annotation[] annotations = filter(annotatedElement.getAnnotations());
        if (annotations.length == 1) {
            if (annotations[0].annotationType() == tClass) {
                return (T) annotations[0];
            }
        } else if (annotations.length > 1) {
            throw new RuntimeException(annotatedElement.toString() + " 不满足单注解");
        }
        return null;
    }

    private static Pattern camelCase = Pattern.compile("([A-Z][a-z]*)+");
    private static Pattern capital = Pattern.compile("[A-Z][a-z]*");

    /**
     * 转换类名的命名规则
     * 要求以pre为前缀
     * 剩余部分从大驼峰转换为蛇并返回
     *
     * @param pre 前缀
     * @param c   类
     * @return
     */
    private static String named(String pre, Class c) {
        String name = c.getSimpleName();
        if (name.startsWith(pre)) {
            name = name.substring(pre.length());
            Matcher matcher = camelCase.matcher(name);
            if (matcher.matches()) {
                matcher = capital.matcher(name);
                StringBuffer sb = new StringBuffer();

                while (matcher.find()) {
                    matcher.appendReplacement(sb, "_" + matcher.group().toLowerCase());
                }
                name = sb.toString().replaceFirst("_", "");
                return name;
            } else {
                throw new RuntimeException(c.getName() + " 名称必须是大驼峰形式的，只包括字母");
            }
        }
        throw new RuntimeException(c.getName() + " 名称必须以 " + pre + " 开头");
    }

}
