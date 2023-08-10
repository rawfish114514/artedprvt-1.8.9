package rawfish.artedprvt.command.item;

import rawfish.artedprvt.command.Command;
import rawfish.artedprvt.command.CommandMessages;
import rawfish.artedprvt.core.ProcessController;
import rawfish.artedprvt.core.ScriptProcess;
import rawfish.artedprvt.id.Local;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandTm extends Command {
    public CommandTm(String commandName) {
        super(commandName);
    }

    @Override
    public void process(List<String> args) {
        if (args.size() > 0) {
            CommandMessages.exception(getName(), "cms0");
            return;
        }
        openWindow();
    }

    private TaskManagerFrame window = null;
    private Thread updateThread = null;
    private boolean v = false;

    private void setV(boolean b) {
        v = b;
    }

    public void openWindow() {
        if (window == null) {
            window = new TaskManagerFrame();
            setV(true);
        }
        window.setVisible(true);
        if (updateThread != null && updateThread.getState() == Thread.State.TERMINATED) {
            try {
                updateThread.stop();
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        updateThread = new Thread() {
            public void run() {
                setName("TaskManagerRepaintThread");
                while (true) {
                    if (window == null || !window.isVisible()) {
                        if (v) {
                            try {
                                Thread.sleep(20);
                            } catch (InterruptedException e) {
                                System.out.println(e);
                            }
                            continue;
                        }
                        break;
                    }
                    v = false;
                    updateData();
                    try {
                        for (int i = 0; i < 62; i++) {
                            window.updateBuffer();
                            window.repaint(40);
                            Thread.sleep(16);
                        }
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }

            public void updateData() {
                window.updateData();
            }
        };
        updateThread.start();
    }

    @Override
    public List<String> complete(List<String> args) {
        return nullTab;
    }

    static class Translate {
        public static String get(String key, Object... args) {
            return Local.getTranslate(key, args);
        }
    }

    static class TaskManagerFrame extends JFrame {
        public MainPanel mainPanel = new MainPanel();
        public Bar mainMenuBar = new Bar(this);

        public TaskManagerFrame() {
            setDefaultCloseOperation(DISPOSE_ON_CLOSE);
            setMinimumSize(new Dimension(400, 300));
            setSize(600, 400);
            setLocationRelativeTo(null);
            setTitle(Translate.get("tms0"));
            setName("task manager");
            setLayout(null);
            add(mainPanel);

            setMenuBar(mainMenuBar);

            addComponentListener(new ComponentAdapter() {
                @Override
                public void componentResized(ComponentEvent e) {
                    updateSize();
                }
            });


        }

        public void updateSize() {
            mainPanel.updateSize(getSize());
        }

        public void updateData() {
            mainPanel.updateData();
            updateSize();
        }

        public void updateBuffer() {
            mainPanel.updateBuffer();
        }
    }

    abstract static class BufferPanel extends JPanel {
        protected BufferedImage bufferedImage = null;


        protected BufferedImage getBufferedImage() {
            if (bufferedImage == null) {
                paintBufferedImage();
            }
            return bufferedImage;
        }

        protected void paintBufferedImage() {
            if (getWidth() == 0 || getHeight() == 0) {
                return;
            }
            BufferedImage image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);
            paintBufferedImage(image.getGraphics());
            bufferedImage = image;
        }

        abstract void paintBufferedImage(Graphics g);

        @Override
        protected void paintComponent(Graphics g) {
            BufferedImage image = getBufferedImage();
            if (image != null) {
                g.drawImage(image, 0, 0, null);
            }
        }
    }

    static class MainPanel extends BufferPanel {
        public StatePanel statePanel = new StatePanel();
        public EntryPanel entryPanel = new EntryPanel();

        //public SlidePanel slidePanel=new SlidePanel();
        public MainPanel() {
            setLayout(null);
            add(statePanel);
            add(entryPanel);

        }

        public void updateSize(Dimension dimension) {
            setSize(dimension);
            statePanel.updateSize(dimension);
            entryPanel.updateSize(new Dimension(dimension.width, dimension.height - statePanel.getHeight()));
        }

        public void updateData() {
            statePanel.updateData();
            entryPanel.updateData();
            entryPanel.setLocation(0, statePanel.getHeight());
        }

        @Override
        void paintBufferedImage(Graphics g) {

        }

        public void updateBuffer() {
            statePanel.updateBuffer();
            entryPanel.updateBuffer();
        }
    }


    static class StatePanel extends BufferPanel {
        public Font font1 = new Font("Small Fonts", Font.PLAIN, 15);
        public Font font2 = new Font("Small Fonts", Font.PLAIN, 17);

        public void updateSize(Dimension dimension) {
            setSize(dimension.width, 45);
        }

        public void updateData() {
        }

        public String name = Translate.get("tms1");
        public String memory = Translate.get("tms2");
        public String cpu = Translate.get("tms3");
        public String pid = Translate.get("tms4");

        void paintBufferedImage(Graphics g) {
            g.setFont(getFont());
            g.setColor(Color.lightGray);
            g.drawLine(0, 0, 4095, 1);
            g.setColor(Color.darkGray);
            g.drawLine(0, getHeight() - 1, 4095, getHeight());
            g.drawLine(getWidth() - 16, 15, getWidth() - 16, getHeight());
            g.drawLine(getWidth() - 76, 15, getWidth() - 76, getHeight());
            g.drawLine(getWidth() - 176, 15, getWidth() - 176, getHeight());
            g.drawLine(getWidth() - 266, 15, getWidth() - 266, getHeight());
            g.setColor(Color.gray);
            g.drawString(name, 0, 37);
            g.drawString(pid, getWidth() - 18 - stringWidth(pid), 37);
            g.drawString(memory, getWidth() - 78 - stringWidth(memory), 37);
            g.drawString(cpu, getWidth() - 178 - stringWidth(cpu), 37);
        }

        public int stringWidth(String s) {
            return getFontMetrics(getFont()).stringWidth(s);
        }


        public void updateBuffer() {
            paintBufferedImage();
        }
    }

    static class EntryPanel extends BufferPanel {
        public List<ProcessEntry> processEntryList;
        public List<Entry> entryList;

        public EntryPanel() {
            setLayout(null);
            //维护条目列表并更新他们
            processEntryList = new ArrayList<>();
            entryList = new ArrayList<>();

            addMouseWheelListener(new MouseWheelListener() {
                @Override
                public void mouseWheelMoved(MouseWheelEvent e) {
                    int notches = e.getWheelRotation();
                    by -= notches * 20;
                    if (by > 0) {
                        by = 0;
                    }
                    updateLocation();
                }
            });
        }

        public void updateSize(Dimension dimension) {
            for (int i = 0; i < entryList.size(); i++) {
                entryList.get(i).updateSize(dimension);
            }
            setSize(dimension);
        }

        public void updateData() {
            removeAll();

            processEntryList = pros();
            entryList = classify();
            Entry entry;
            for (int i = 0; i < entryList.size(); i++) {
                entry = entryList.get(i);
                entry.updateData();
                entry.updateSize(getSize());
            }
            updateLocation();

            for (int i = 0; i < entryList.size(); i++) {
                entry = entryList.get(i);
                add(entry);
            }
        }

        public int by = 0;

        public void updateLocation() {
            Entry entry;
            int y = 0;
            for (int i = 0; i < entryList.size(); i++) {
                entry = entryList.get(i);
                y += entry.getHeight();
            }

            if (by < 0 && (y + by) < getHeight() - 100) {
                by = getHeight() - y - 100;
                if (by > 0) {
                    by = 0;
                }
            }

            y = 0;
            for (int i = 0; i < entryList.size(); i++) {
                entry = entryList.get(i);
                entry.setLocation(0, y + by);
                y += entry.getHeight();
                entry.updateBuffer();
            }

        }

        public List<Entry> classify() {
            List<ProcessEntry> scriptProcessEntryList = new ArrayList<>();
            List<ProcessEntry> otherProcessEntryList = new ArrayList<>();
            ProcessEntry entry;
            for (int i = 0; i < processEntryList.size(); i++) {
                entry = processEntryList.get(i);
                if (entry.process instanceof ScriptsProcess) {
                    scriptProcessEntryList.add(entry);
                    continue;
                }
                if (entry.process instanceof OtherProcess) {
                    otherProcessEntryList.add(entry);
                }
            }

            List<Entry> classifyEntryList = new ArrayList<>();
            if (scriptProcessEntryList.size() > 0) {
                classifyEntryList.add(new ClassifyEntry(Translate.get("tms5")));
                scriptProcessEntryList.forEach((p) -> classifyEntryList.add(p));
            }
            if (otherProcessEntryList.size() > 0) {
                classifyEntryList.add(new ClassifyEntry(Translate.get("tms6")));
                otherProcessEntryList.forEach((p) -> classifyEntryList.add(p));
            }

            return classifyEntryList;
        }

        public List<ProcessEntry> pros() {
            List<ScriptProcess> scriptProcessList = ScriptProcess.getProList();
            List<Process> processList = new ArrayList<>();

            for (int i = 0; i < scriptProcessList.size(); i++) {
                processList.add(new ScriptsProcess(scriptProcessList.get(i)));
            }

            //任务管理器
            Container c;
            if ((c = getTopLevelAncestor()) != null) {
                if (c instanceof JFrame) {
                    OtherProcess taskManager = new OtherProcess(((JFrame) c).getTitle());
                    processList.add(taskManager);
                }
            }

            //进程控制器
            OtherProcess processController = new OtherProcess(Translate.get("tms7"));
            processController.thread = ProcessController.getThread();
            processList.add(processController);

            List<ProcessEntry> processEntryList1 = new ArrayList<>();
            ProcessEntry processEntry = null;
            for (int i = 0; i < processList.size(); i++) {
                processEntry = new ProcessEntry(processList.get(i));
                if (processEntryList.size() > i && processEntryList.get(i) != null) {
                    //按位继承
                    processEntry.extend(processEntryList.get(i));
                }
                processEntryList1.add(processEntry);
            }

            return processEntryList1;
        }

        @Override
        void paintBufferedImage(Graphics g) {

        }

        public void updateBuffer() {
            Entry entry;
            for (int i = 0; i < entryList.size(); i++) {
                entry = entryList.get(i);
                entry.updateBuffer();
            }
        }
    }

    static class Bar extends MenuBar {
        public JFrame frame;
        public CommandDialog runApkgDialog;
        public CommandDialog runScriptDialog;

        public Bar(JFrame frame) {
            this.frame = frame;
            Menu fileMenu = new Menu(Translate.get("tms8"));
            MenuItem runApkgMenuItem = new MenuItem(Translate.get("tms9"));
            runApkgMenuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    runApkg();
                }
            });
            MenuItem runScriptMenuItem = new MenuItem(Translate.get("tms10"));
            runScriptMenuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    runScript();
                }
            });
            MenuItem closeMenuItem = new MenuItem(Translate.get("tms11"));
            closeMenuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    close();
                }
            });

            fileMenu.add(runApkgMenuItem);
            fileMenu.add(runScriptMenuItem);
            fileMenu.add(closeMenuItem);

            Menu helpMenu = new Menu(Translate.get("tms12"));
            MenuItem aboutMenuItem = new MenuItem(Translate.get("tms13"));
            aboutMenuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    about();
                }
            });

            helpMenu.add(aboutMenuItem);

            add(fileMenu);
            add(helpMenu);

            runApkgDialog = new CommandDialog(Translate.get("tms14"), frame, new CommandApkg("tm-apkg"),
                    Translate.get("tms15"));
            runScriptDialog = new CommandDialog(Translate.get("tms16"), frame, new CommandScript("tm-script"),
                    Translate.get("tms17"));
        }

        public void runApkg() {
            runApkgDialog.setVisible(true);
        }

        public void runScript() {
            runScriptDialog.setVisible(true);
        }

        public void close() {
            frame.setVisible(false);
        }

        public JDialog aboutDialog = null;

        public void about() {
            if (aboutDialog == null) {
                aboutDialog = new JDialog(frame, Translate.get("tms18"), true);
                aboutDialog.setSize(320, 120);
                aboutDialog.setResizable(false);
                aboutDialog.setLayout(null);
                aboutDialog.setLocationRelativeTo(frame);
                JLabel label = new JLabel(Translate.get("tms19"));
                label.setFont(new Font("Small Fonts", Font.PLAIN, 18));
                label.setLayout(null);
                label.setSize(260, 80);
                label.setLocation(30, 5);
                aboutDialog.add(label);
            }
            aboutDialog.setVisible(true);
        }
    }

    static class CommandDialog extends JDialog {
        public String title;
        public JFrame frame;
        public Command command;
        public String description;
        public JTextField text;
        public JLabel des;

        public CommandDialog(String title, JFrame frame, Command command, String description) {
            super(frame, title, true);
            this.title = title;
            this.frame = frame;
            this.command = command;
            this.description = description;

            setSize(420, 160);
            setResizable(false);
            setLayout(null);

            text = new JTextField(32500);
            text.setLayout(null);
            text.setSize(300, 24);
            text.setLocation(60, 80);

            add(text);

            des = new JLabel(description);
            des.setLayout(null);
            des.setSize(300, 60);
            des.setLocation(60, 10);
            des.setFont(new Font("Small Fonts", Font.PLAIN, 15));
            des.setAutoscrolls(true);
            add(des);


            KeyListener keyListener = (new KeyAdapter() {
                public List<String> complete = null;
                public int cindex = 0;

                @Override
                public void keyReleased(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_TAB) {
                        //自动补全
                        String str = text.getText();
                        if (str == null) {
                            str = "";
                        }
                        String[] stringArray = str.split("\\s+");
                        List<String> args = Arrays.asList(stringArray);
                        if (str.endsWith(" ")) {
                            args = new ArrayList<>(args);
                            args.add("");
                        }
                        if (args.size() == 0) {
                            args = new ArrayList<>();
                            args.add("");
                        }
                        if (complete == null) {
                            //获取选项
                            complete = complete(args);
                        }

                        List<String> uc = useComplete(args);
                        String ns = String.join(" ", uc);

                        text.setText(ns);
                        return;
                    }

                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        //完成
                        List<String> args;
                        String str = text.getText();
                        if (str == null) {
                            args = new ArrayList<>();
                        } else {
                            String[] stringArray = str.split("\\s+");
                            args = Arrays.asList(stringArray);
                        }

                        command.process(args);
                        close();

                        return;
                    }

                    //其他输入
                    complete = null;
                    cindex = 0;
                }

                public List<String> useComplete(List<String> args) {
                    if (complete != null) {
                        List<String> sl = new ArrayList<>(args);
                        String lastArg = sl.get(sl.size() - 1);
                        List<String> c = new ArrayList<>();
                        complete.forEach(s -> {
                            if (sl.contains(lastArg)) {
                                c.add(s);
                            }
                        });
                        if (cindex >= c.size()) {
                            cindex = 0;
                        }
                        if (c.size() > 0) {
                            sl.set(sl.size() - 1, c.get(cindex));
                        }
                        cindex++;
                        return sl;
                    }
                    return new ArrayList<>();
                }
            });

            requestFocus();
            addKeyListener(keyListener);
            text.addKeyListener(keyListener);
            setFocusTraversalKeysEnabled(false);
            text.setFocusTraversalKeysEnabled(false);

            addWindowListener(new WindowAdapter() {
                @Override
                public void windowClosing(WindowEvent e) {
                    close();
                }
            });

            setLocationRelativeTo(frame);
        }


        @Override
        protected void processEvent(AWTEvent e) {
            if (e instanceof KeyEvent) {
                KeyEvent keyEvent = (KeyEvent) e;
                if (keyEvent.getKeyCode() == KeyEvent.VK_TAB) {

                }
            }
            super.processEvent(e);
        }

        public void process(List<String> args) {

        }

        public List<String> complete(List<String> args) {
            return command.complete(args);
        }

        public void close() {
            text.setText("");
            setVisible(false);
        }
    }

    abstract static class Entry extends BufferPanel {
        public Entry() {
            setLayout(null);
        }

        public void updateSize(Dimension dimension) {
            setSize(dimension.width, 30);
        }

        abstract void updateData();

        public void updateBuffer() {
        }
    }

    static class ProcessEntry extends Entry {
        public Process process;
        public String name;
        public double cpu;
        public long memory;
        public int pid;

        public boolean hasMouses = false;

        public ProcessEntry(Process process) {
            this.process = process;
            setFont(new Font("Small Fonts", Font.PLAIN, 15));

            ProcessEntry that = this;
            addMouseListener(new MouseAdapter() {
                @Override
                public void mouseReleased(MouseEvent e) {
                    if (e.isPopupTrigger()) {
                        new ProcessPopupMenu(process).show(that, e.getX(), e.getY());
                    }
                }

                @Override
                public void mouseEntered(MouseEvent e) {
                    setHasMouses(true);
                }

                @Override
                public void mouseExited(MouseEvent e) {
                    setHasMouses(false);
                }
            });

        }

        public void setHasMouses(boolean b) {
            hasMouses = b;
        }

        @Override
        void updateData() {
            name = process.getName();
            cpu = process.getCPU();
            memory = process.getMemory();
            pid = process.getPid();
        }

        @Override
        protected void paintBufferedImage(Graphics g) {
            //w*30

            g.setFont(getFont());
            //背景
            if (hasMouses) {
                g.setColor(Color.pink);
                g.fillRect(0, 0, getWidth(), getHeight());
            }

            //文字
            g.setColor(Color.black);

            g.drawString(name, 50, 20);
            String c = "";
            if (cpu >= 0) {
                c = new DecimalFormat("0.0").format(cpu * 100) + "%";
            }
            String m = "";
            DecimalFormat md = new DecimalFormat("0.00");
            if (memory >= 0 && memory < 1024) {
                m = memory + "B";
            }
            if (memory >= 1024 && memory < 1024 * 1024) {
                m = md.format(memory / 1024.0) + "KB";
            }
            if (memory >= 1024 * 1024) {
                m = md.format(memory / 1024.0 / 1024) + "MB";
            }
            String p = "";
            if (pid != -1) {
                p = String.valueOf(pid);
            }
            g.drawString(c, getWidth() - 178 - stringWidth(c), 20);
            g.drawString(m, getWidth() - 78 - stringWidth(m), 20);
            g.drawString(p, getWidth() - 18 - stringWidth(p), 20);

            Image image = process.getIcon();
            if (image != null) {
                g.drawImage(image, 30, 6, null);
            }
        }


        public void updateBuffer() {
            paintBufferedImage();
        }

        public int stringWidth(String s) {
            return getFontMetrics(getFont()).stringWidth(s);
        }

        public void extend(ProcessEntry processEntry) {
            if (processEntry != null) {
                hasMouses = processEntry.hasMouses;
            }
        }
    }

    interface Process {
        /**
         * 获取名称
         *
         * @return 返回这个任务的名称
         */
        String getName();

        /**
         * 获取图标
         *
         * @return 返回这个任务的图标 没有则返回null
         */
        Image getIcon();

        /**
         * 获取CPU利用率
         *
         * @return 返回这个任务的CPU利用率 无法计算则返回-1
         */
        double getCPU();

        /**
         * 获取内存占用
         *
         * @return 返回这个任务的内存占用 无法计算则返回-1
         */
        long getMemory();

        /**
         * 获取进程id
         *
         * @return
         */
        int getPid();

        /**
         * 判断是否可以停止 返回值影响菜单栏的状态
         *
         * @return
         */
        boolean menuStop();

        /**
         * 判断是否可以重启 返回值影响菜单栏的状态
         *
         * @return
         */
        boolean menuRestart();

        /**
         * 判断是否可以查看属性 返回值影响菜单栏的状态
         *
         * @return
         */
        boolean menuProperties();

        /**
         * 停止 由菜单栏监听器调用
         */
        void stop();

        /**
         * 重启 由菜单栏监听器调用
         */
        void restart();

        /**
         * 属性 由菜单栏监听器调用
         *
         * @return 返回字符串或面板或null
         */
        Object properties();
    }

    static class ProcessAdapter implements Process {

        @Override
        public String getName() {
            return "";
        }

        @Override
        public Image getIcon() {
            return null;
        }

        @Override
        public double getCPU() {
            return -1;
        }

        @Override
        public long getMemory() {
            return -1;
        }

        @Override
        public int getPid() {
            return -1;
        }

        @Override
        public boolean menuStop() {
            return false;
        }

        @Override
        public boolean menuRestart() {
            return false;
        }

        @Override
        public boolean menuProperties() {
            return false;
        }

        @Override
        public void stop() {

        }

        @Override
        public void restart() {

        }

        @Override
        public Object properties() {
            return null;
        }
    }

    static class ProcessPopupMenu extends JPopupMenu {
        public Process process;

        public ProcessPopupMenu(Process process) {
            this.process = process;

            JMenuItem stopMenuItem = new JMenuItem(Translate.get("tms20"));
            stopMenuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    process.stop();
                }
            });
            stopMenuItem.setEnabled(process.menuStop());
            JMenuItem restartMenuItem = new JMenuItem(Translate.get("tms21"));
            restartMenuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    process.restart();
                }
            });
            restartMenuItem.setEnabled(process.menuRestart());
            JMenuItem propertiesMenuItem = new JMenuItem(Translate.get("tms22"));
            propertiesMenuItem.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    properties(process.properties());
                }
            });
            propertiesMenuItem.setEnabled(process.menuProperties());

            add(stopMenuItem);
            add(restartMenuItem);
            add(propertiesMenuItem);
        }

        public void properties(Object pro) {

        }
    }

    static class ScriptsProcess extends ProcessAdapter {
        public ScriptProcess scriptProcess;

        public ScriptsProcess(ScriptProcess scriptProcess) {
            this.scriptProcess = scriptProcess;
        }

        @Override
        public String getName() {
            return scriptProcess.getName();
        }

        @Override
        public Image getIcon() {
            return scriptProcess.getIcon();
        }

        @Override
        public double getCPU() {
            return scriptProcess.getCPU();
        }

        @Override
        public long getMemory() {
            return scriptProcess.getMemory();
        }

        @Override
        public int getPid() {
            return scriptProcess.getPid();
        }

        @Override
        public boolean menuStop() {
            return true;
        }

        @Override
        public boolean menuProperties() {
            return true;
        }

        @Override
        public void stop() {
            scriptProcess.stop(ScriptProcess.STOPS);
        }

        @Override
        public Object properties() {
            return null;
        }
    }

    static class OtherProcess extends ProcessAdapter {
        public String name;
        public Thread thread;
        public Image image = null;

        public OtherProcess(String name) {
            this.name = name;
            this.thread = Thread.currentThread();
        }

        @Override
        public String getName() {
            return name;
        }

        @Override
        public Image getIcon() {
            return image;
        }

        @Override
        public double getCPU() {
            return getCPU(thread);
        }

        @Override
        public long getMemory() {
            return -1;
        }

        private long gclastCpuTime = -1;
        private long gclastTime = -1;
        private double oldCPU = 0;

        public double getCPU(Thread t) {
            if (t == null || t.getState() == Thread.State.TERMINATED) {
                return 0;
            }
            if (gclastCpuTime == 0) {
                gclastCpuTime = 0;
                gclastTime = System.currentTimeMillis();
                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                long cpu = threadMXBean.getThreadCpuTime(t.getId());
                gclastCpuTime += cpu;
                return 0;
            }
            long cpuTime = 0;
            long time = System.currentTimeMillis();
            ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
            long cpu = threadMXBean.getThreadCpuTime(t.getId());
            cpuTime += cpu;


            long cpuIncrement = cpuTime - gclastCpuTime;
            long timeIncrement = time - gclastTime;
            if (timeIncrement < 200) {
                return oldCPU;
            }
            gclastCpuTime = cpuTime;
            gclastTime = time;
            if (timeIncrement > 10000) {
                return 0;
            }
            oldCPU = cpuIncrement / 1.0e6 / Runtime.getRuntime().availableProcessors() / timeIncrement;
            return oldCPU;
        }

        @Override
        public boolean menuProperties() {
            return true;
        }

        @Override
        public Object properties() {
            return null;
        }
    }

    static class ClassifyEntry extends Entry {
        public String classify;

        public ClassifyEntry(String classify) {
            this.classify = classify;
            setFont(new Font("Small Fonts", Font.PLAIN, 18));
        }

        @Override
        public void updateSize(Dimension dimension) {
            setSize(dimension.width, 35);
        }

        @Override
        void updateData() {
        }

        @Override
        protected void paintBufferedImage(Graphics g) {
            g.setFont(getFont());
            g.setColor(Color.blue);
            g.drawString(classify, 0, 28);
        }

        public void updateBuffer() {
            paintBufferedImage();
        }
    }

}