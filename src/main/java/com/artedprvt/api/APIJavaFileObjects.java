package com.artedprvt.api;

import javax.tools.JavaFileObject;
import javax.tools.JavaFileObject.Kind;
import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class APIJavaFileObjects {
    private static Map<String, byte[]> map = new HashMap<>();

    private static byte[] getBytes(String name) {
        if (!map.containsKey(name)) {
            try {
                map.put(name, readBytes(name));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return map.get(name);
    }

    private static byte[] readBytes(String name) throws IOException {
        InputStream inputStream = APIJavaFileObjects.class.getResourceAsStream("/" + name);
        if (inputStream == null) {
            return null;
        }
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int n;
        while ((n = inputStream.read()) != -1) {
            byteArrayOutputStream.write(n);
        }
        return byteArrayOutputStream.toByteArray();
    }


    public static List<JavaFileObject> nativeClasses(String packageName, Set<Kind> kinds, boolean recurse) {
        if (kinds.contains(Kind.CLASS)) {
            List<JavaFileObject> classList = nativeClasses(packageName, recurse);
            if (classList.isEmpty()) {
                return null;
            }
            return classList;
        }
        return null;
    }

    private static List<JavaFileObject> nativeClasses(String packageName, boolean recurse) {
        List<JavaFileObject> list = new ArrayList<>();
        for (String name : CS.strings) {
            String path = packageName;
            if (name.startsWith(path)) {
                if (recurse || (name.length() > path.length() && !name.substring(path.length() + 1).contains("."))) {
                    list.add(new JFO(name, getBytes(name.replace('.','/')+".class"), Kind.CLASS));
                }
            }
        }
        return list;
    }


    public static class JFO extends SimpleJavaFileObject {
        private byte[] bytes;
        private String name;

        protected JFO(String name, byte[] bytes, Kind kind) {
            super(URI.create(name), kind);
            this.bytes = bytes;
            this.name = name;
        }

        @Override
        public InputStream openInputStream() {
            return new ByteArrayInputStream(bytes);
        }

        public String getBinaryName() {
            return name;
        }


    }

    private static class CS{
        public static String[] strings=new String[]{
                "com.electronwill.toml.TomlWriter",
                "com.artedprvt.core.BaseClassGroup",
                "com.artedprvt.work.ProjectTool",
                "com.artedprvt.iv.anno.InterfaceView",
                "com.artedprvt.core.ProcessProvider",
                "com.artedprvt.command.commands.CommandProject",
                "com.artedprvt.core.InProcess",
                "com.artedprvt.work.ProjectInitializer",
                "com.artedprvt.core.app.java.JarClassLoader",
                "com.artedprvt.std.minecraft.client.ClientPlayerEntity",
                "com.artedprvt.std.cli.util.StringListBuilder",
                "com.electronwill.toml.Toml",
                "com.artedprvt.core.localization.types.CMS",
                "com.artedprvt.std.cli.format.FormatHandlerNumber",
                "com.artedprvt.core.Environment",
                "com.artedprvt.std.cli.format.FormatHandlerRegex",
                "com.artedprvt.client.ClientProxy",
                "com.artedprvt.work.ObjectIn",
                "com.artedprvt.work.WorkRuntime",
                "com.artedprvt.Artedprvt",
                "com.artedprvt.std.cli.info.InfoHandlerString",
                "com.artedprvt.core.app.LogFileController",
                "com.artedprvt.std.cli.CommandRegistry",
                "com.artedprvt.core.app.AppType",
                "com.artedprvt.std.minecraft.chat.ChatConsole",
                "com.electronwill.toml.FastStringWriter",
                "com.artedprvt.command.commands.CommandPros",
                "com.artedprvt.std.minecraft.entity.PlayerEntity",
                "com.artedprvt.std.cli.util.Literals",
                "com.artedprvt.command.commands.CommandApf",
                "com.artedprvt.command.FormatMessager",
                "com.artedprvt.std.minecraft.client.ClientMinecraft",
                "com.artedprvt.std.text.Formatting",
                "com.artedprvt.command.commands.CommandStops",
                "com.artedprvt.std.cli.Command",
                "com.artedprvt.command.TaskProcess",
                "com.artedprvt.client.CommandLiteralGuiCommandBlock",
                "com.artedprvt.std.cli.Messager",
                "com.artedprvt.work.anno.Goal",
                "com.artedprvt.core.app.Home",
                "com.artedprvt.work.ObjectOut",
                "com.artedprvt.std.cli.util.InfoHandlerMapBuilder",
                "com.artedprvt.core.SystemProcess",
                "com.artedprvt.core.Logger",
                "com.artedprvt.command.CommandLoader",
                "com.artedprvt.std.cli.util.HandleResult",
                "com.electronwill.toml.TomlException",
                "com.artedprvt.core.AbstractProcess",
                "com.artedprvt.std.cli.ProcessInterface",
                "com.artedprvt.work.LifecycleData",
                "com.artedprvt.client.DebugInfo",
                "com.artedprvt.common.ServerCommandLoader",
                "com.artedprvt.work.GoalHandle",
                "com.electronwill.toml.TomlReader",
                "com.artedprvt.std.cli.util.FormatHandlerFactory",
                "com.artedprvt.core.CoreInitializer",
                "com.artedprvt.command.commands.CommandApp",
                "com.artedprvt.std.cli.util.parser.ArgumentsParserSet",
                "com.artedprvt.std.math.Vector3",
                "com.artedprvt.core.app.java.JavaAppType",
                "com.artedprvt.std.minecraft.client.ClientWorld",
                "com.artedprvt.api.APIJavaFileObjects",
                "com.artedprvt.work.anno.Lifecycle",
                "com.artedprvt.std.cli.format.FormatHandlerSet",
                "com.artedprvt.work.ProjectSystem",
                "com.artedprvt.std.cli.util.CommandBuilder",
                "com.artedprvt.core.app.java.JavaExceptionHandler",
                "com.artedprvt.std.cli.InfoInterface",
                "com.artedprvt.command.BaseCommand",
                "com.artedprvt.work.anno.Command",
                "com.artedprvt.std.cli.info.InfoHandlerEmpty",
                "com.artedprvt.std.cli.FormatInterface",
                "com.artedprvt.work.anno.Phase",
                "com.artedprvt.std.cli.util.InfoHandlerFactory",
                "com.artedprvt.work.ClassByteTool",
                "com.artedprvt.std.minecraft.chat.ChatStyle",
                "com.artedprvt.client.CommandLiteralGuiChat",
                "com.artedprvt.std.cli.InfoHandler",
                "com.artedprvt.std.cli.util.ArgumentsParserInterface",
                "com.artedprvt.core.app.AppTarget",
                "com.artedprvt.std.cli.util.FormatHandlerMapBuilder",
                "com.artedprvt.std.minecraft.world.World",
                "com.artedprvt.core.app.App",
                "com.artedprvt.core.ClassGroupSystem",
                "com.artedprvt.common.CommonProxy",
                "com.artedprvt.core.localization.types.CIS",
                "com.artedprvt.work.anno.ProjectScript",
                "com.artedprvt.std.cli.CompleteInterface",
                "com.artedprvt.std.cli.FormatHandler",
                "com.artedprvt.std.minecraft.client.ClientNetwork",
                "com.artedprvt.work.Project",
                "com.artedprvt.core.app.java.JavaAppTarget",
                "com.artedprvt.std.minecraft.chat.ChatComponent",
                "com.artedprvt.work.CommandData",
                "com.artedprvt.work.anno.Exclude",
                "com.artedprvt.std.math.Vector",
                "com.artedprvt.core.app.BaseSystem",
                "com.artedprvt.std.cli.util.ParseResult",
                "com.artedprvt.std.cli.CommandHandler",
                "com.artedprvt.common.EventLoader",
                "com.artedprvt.core.app.AppLogger",
                "com.artedprvt.core.app.java.JavaProcess",
                "com.artedprvt.core.ProcessController",
                "com.artedprvt.std.minecraft.entity.Entity",
                "com.artedprvt.std.cli.format.FormatHandlerAppend",
                "com.artedprvt.work.CommandHandle",
                "com.artedprvt.core.AbstractExceptionHandler",
                "com.artedprvt.core.Process",
                "com.artedprvt.std.cli.format.FormatHandlerEmpty",
                "com.artedprvt.common.CommandAdapter",
                "com.artedprvt.std.cli.util.parser.ArgumentsParserRegex",
                "com.artedprvt.core.app.java.JavaAppMain",
                "com.artedprvt.std.minecraft.chat.ChatHover",
                "com.artedprvt.std.cli.info.InfoHandlerMap",
                "com.artedprvt.work.PhaseData",
                "com.artedprvt.std.minecraft.chat.ChatClick",
                "com.artedprvt.std.cli.util.FormatHandlerListBuilder",
                "com.artedprvt.core.AbstractThread",
                "com.artedprvt.command.commands.CommandWork",
                "com.artedprvt.work.ProjectAccess",
                "com.artedprvt.core.ClassGroup",
                "com.artedprvt.std.minecraft.chat.ChatHoverString",
                "com.artedprvt.client.EventLoader",
                "com.artedprvt.work.GoalData",
                "com.artedprvt.work.PhaseHandle",
                "com.artedprvt.core.app.AppProcess",
        };
    }
}
