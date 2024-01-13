package rawfish.artedprvt.core.app;

import rawfish.artedprvt.core.Logger;

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
public class AppLogger extends Logger {
    private AppProcess appProcess;
    private List<PrintWriter> printWriters;
    private LocalDate initDate;

    public AppLogger(AppProcess appProcess, LocalDate date, PrintWriter... printWriters) {
        this.appProcess = appProcess;
        this.printWriters = Arrays.asList(printWriters);
        initDate = date;
    }

    public AppLogger(AppProcess appProcess, LocalDate date, OutputStream... outputStreams) {
        this.appProcess = appProcess;
        this.printWriters = Arrays.stream(outputStreams).map(PrintWriter::new).collect(Collectors.toList());
        initDate = date;
    }

    /**
     * [date  info]: message
     *
     * @param message
     */
    public synchronized void info(String message) {
        String s = getDay() + "[" + getDateStr() + "  info]" + nonThread() + ": " + appendLine(removeFormatCode(message));
        writeAll(s);
    }

    /**
     * [date  warn]: message
     *
     * @param message
     */
    public synchronized void warn(String message) {
        String s = getDay() + "[" + getDateStr() + "  warn]" + nonThread() + ": " + appendLine(removeFormatCode(message));
        writeAll(s);
    }

    /**
     * [date error]: message
     *
     * @param message
     */
    public synchronized void error(String message) {
        String s = getDay() + "[" + getDateStr() + " error]" + nonThread() + ": " + appendLine(removeFormatCode(message));
        writeAll(s);
    }

    @Override
    public void close() {
        for (PrintWriter writer : printWriters) {
            writer.close();
        }
    }

    public synchronized void natives(String message) {
        String s = getDay() + "[" + getDateStr() + "  info]: " + appendLine(removeFormatCode(message));
        writeAll(s);
    }


    private String removeFormatCode(String s) {
        return s.replaceAll("\u00a7[0-9a-fk-or]", "");
    }

    private String appendLine(String s) {
        return s.replace("\n", "\n                  ");
    }

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    private String getDateStr() {
        LocalTime currentTime = LocalTime.now();
        return currentTime.format(formatter);
    }

    private String nonThread() {
        Thread thread = Thread.currentThread();
        if (appProcess.isThread(thread)) {
            return "";
        }
        return " [" + thread.getName() + "]";
    }

    private String getDay() {
        LocalDate currentDate = LocalDate.now();
        long daysBetween = ChronoUnit.DAYS.between(initDate, currentDate);
        if (daysBetween == 0) {
            return "";
        } else {
            return "+" + daysBetween + "d ";
        }
    }

    private synchronized void writeAll(String s) {
        for (PrintWriter writer : printWriters) {
            writer.println(s);
            writer.flush();
        }
    }
}
