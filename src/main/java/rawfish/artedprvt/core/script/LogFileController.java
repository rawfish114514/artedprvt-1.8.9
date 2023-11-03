package rawfish.artedprvt.core.script;

import rawfish.artedprvt.core.SystemProcess;
import rawfish.artedprvt.core.WorkSpace;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class LogFileController extends SystemProcess {
    public static final Function<String[],String> LOG= strings -> "/.artedprvt/logs/"+strings[0];

    private static final Pattern datePattern= Pattern.compile("\\d{4}-\\d{2}-\\d{2}");

    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    private static final DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd");
    private static final DateTimeFormatter yearFormatter = DateTimeFormatter.ofPattern("yyyy");
    private static final DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("MM");


    public LogFileController() {
        super("LogFileController");
    }

    @Override
    public void run() {
        while(true){
            //合并很久以前的日志
            LocalDate currentDate=LocalDate.now();
            File logDir = new File(WorkSpace.derivation(LOG,""));
            File[] files=logDir.listFiles();
            if(files==null||files.length==0){
                continue;
            }

            Map<String, List<File>> map=new HashMap<>();
            for(File file:files){
                String name=file.getName();

                Matcher matcher = datePattern.matcher(name);
                if (matcher.matches()) {
                    String dateStr = matcher.group();
                    LocalDate targetDate = LocalDate.parse(dateStr, dateFormatter);
                    int monthDifference = currentDate.getMonthValue()-targetDate.getMonthValue();
                    if(monthDifference>0){
                        String year=yearFormatter.format(targetDate);
                        String month=monthFormatter.format(targetDate);
                        String key=year+"-"+month;
                        map.computeIfAbsent(key, k -> new ArrayList<>());
                        List<File> list=map.get(key);

                        list.add(file);
                    }
                }
            }

            try {
                Set<String> keys=map.keySet();
                for(String key:keys){
                    List<File> list=map.get(key);
                    File zipFile = new File(WorkSpace.derivation(LOG,key+".zip"));
                    ZipOutputStream zip;
                        zip=new ZipOutputStream(new FileOutputStream(zipFile), Charset.forName("cp437"));


                    for(File dayDir:list){
                        for(File file: Objects.requireNonNull(dayDir.listFiles())){
                            String name=file.toPath().subpath(logDir.toPath().getNameCount(),file.toPath().getNameCount()).toString();
                            ZipEntry entry=new ZipEntry(name);
                            zip.putNextEntry(entry);

                            InputStream input=new FileInputStream(file);
                            int n;
                            while(true){
                                if((n=input.read())==-1){
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


    public FileOutputStream openLog(String name) throws FileNotFoundException {
        return openLog(WorkSpace.currentWorkSpace(),LocalDate.now(),name);
    }

    public FileOutputStream openLog(WorkSpace workSpace,LocalDate localDate, String name) throws FileNotFoundException {
        File logDir = new File(workSpace.toDerivation(LOG,
                yearFormatter.format(localDate)
                        + "-"
                        + monthFormatter.format(localDate)
                        + "-"
                        + dayFormatter.format(localDate)));
        logDir.mkdirs();
        int logFileNumber = logDir.list().length;
        File logFile = new File(logDir.getPath() + "/" + logFileNumber + "." + name + ".txt");
        return new FileOutputStream(logFile);
    }
}
