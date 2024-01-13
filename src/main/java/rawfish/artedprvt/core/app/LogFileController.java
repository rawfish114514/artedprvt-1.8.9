package rawfish.artedprvt.core.app;

import rawfish.artedprvt.core.SystemProcess;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FilterOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LogFileController extends SystemProcess {
    private static final Pattern datePattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
    private static final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
    private static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");


    public LogFileController() {
        super("LogFileController");
    }

    @Override
    public void run() {
        while (true) {
            //合并很久以前的日志
            LocalDate currentDate = LocalDate.now();
            File logDir = new File(Home.log());
            File[] files = logDir.listFiles();
            if (files == null || files.length == 0) {
                continue;
            }

            Map<String, List<File>> map = new HashMap<>();
            for (File file : files) {
                String name = file.getName();

                Matcher matcher = datePattern.matcher(name);
                if (matcher.matches()) {
                    String dateStr = matcher.group();
                    LocalDate targetDate = LocalDate.parse(dateStr, dateFormatter);
                    int monthDifference = currentDate.getMonthValue() - targetDate.getMonthValue();
                    if (monthDifference > 0) {
                        String year = yearFormatter.format(targetDate);
                        String month = monthFormatter.format(targetDate);
                        String key = year + "-" + month;
                        map.computeIfAbsent(key, k -> new ArrayList<>());
                        List<File> list = map.get(key);

                        list.add(file);
                    }
                }
            }

            try {
                Set<String> keys = map.keySet();
                for (String key : keys) {
                    List<File> list = map.get(key);
                    File zipFile = new File(Home.log(), key + ".zip");
                    ZipOutputStream zip;
                    zip = new ZipOutputStream(new FileOutputStream(zipFile), Charset.forName("cp437"));


                    for (File dayDir : list) {
                        for (File file : Objects.requireNonNull(dayDir.listFiles())) {
                            String name = file.toPath().subpath(logDir.toPath().getNameCount(), file.toPath().getNameCount()).toString();
                            ZipEntry entry = new ZipEntry(name);
                            zip.putNextEntry(entry);

                            InputStream input = new FileInputStream(file);
                            int n;
                            while (true) {
                                if ((n = input.read()) == -1) {
                                    break;
                                }
                                zip.write(n);
                            }
                            zip.closeEntry();
                            input.close();
                            file.delete();
                        }
                        dayDir.delete();
                    }
                    zip.close();
                }
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            try {
                Thread.sleep(3600000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }


    public AppLogger openLog(AppProcess appProcess) throws FileNotFoundException {
        return openLog(appProcess, LocalDate.now());
    }

    public synchronized AppLogger openLog(AppProcess appProcess, LocalDate localDate) throws FileNotFoundException {
        File logDir = new File(Home.log(),
                yearFormatter.format(localDate)
                        + "-"
                        + monthFormatter.format(localDate)
                        + "-"
                        + dayFormatter.format(localDate));
        logDir.mkdirs();
        int logFileNumber = logDir.list().length;
        String processName = appProcess.getName();
        File logFile = new File(logDir, logFileNumber + "." + processName.substring(processName.indexOf(':') + 1) + ".txt");
        OutputStream fileOutputStream = new FileOutputStream(logFile);
        OutputStream systemOutputStream = new FilterOutputStream(System.out) {
            @Override
            public void close() throws IOException {
            }
        };
        return new AppLogger(appProcess, localDate, fileOutputStream, systemOutputStream);
    }
}
