package rawfish.artedprvt.core;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 日志记录器
 * 提供一系列输出方法输出到所有输出流
 */
public class ScriptLogger {
    private List<PrintWriter> printWriters;
    private LocalDate initDate;

    public ScriptLogger(LocalDate date, PrintWriter... printWriters){
        this.printWriters=Arrays.asList(printWriters);
        initDate=date;
    }
    public ScriptLogger(LocalDate date, OutputStream... outputStreams){
        this.printWriters= Arrays.stream(outputStreams).map(PrintWriter::new).collect(Collectors.toList());
        initDate=date;
    }

    /**
     * [date] [info]: message
     * @param message
     */
    public synchronized void info(String message){
        String s=getDay()+"["+getDateStr()+"] [info]: "+message;
        writeAll(s);
    }

    /**
     * [date] [warn]: message
     * @param message
     */
    public synchronized void warn(String message){
        String s=getDay()+"["+getDateStr()+"] [warn]: "+message;
        writeAll(s);
    }

    private final DateTimeFormatter formatter=DateTimeFormatter.ofPattern("HH:mm:ss");
    private String getDateStr(){
        LocalTime currentTime=LocalTime.now();
        return currentTime.format(formatter);
    }

    private String getDay(){
        LocalDate currentDate=LocalDate.now();
        long daysBetween=ChronoUnit.DAYS.between(initDate,currentDate);
        if(daysBetween==0){
            return "";
        }else{
            return "+"+daysBetween+"d ";
        }
    }

    private synchronized void writeAll(String s){
        for(PrintWriter writer:printWriters){
            writer.println(s);
            writer.flush();
        }
    }
}
