package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.InfoHandler;
import com.artedprvt.std.cli.info.InfoHandlerEmpty;

import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 封装使用处理程序的方法
 */
@InterfaceView
public class Literals {
    private static List<String> emptyComplete = Collections.emptyList();

    @InterfaceView
    public static List<String> emptyComplete() {
        return emptyComplete;
    }

    private static List<FormatHandler> emptyFormat = Collections.emptyList();

    @InterfaceView
    public static List<FormatHandler> emptyFormat() {
        return emptyFormat;
    }

    private static InfoHandler emptyInfo = new InfoHandlerEmpty();

    @InterfaceView
    public static InfoHandler emptyInfo() {
        return emptyInfo;
    }

    @InterfaceView
    public static StringListBuilder stringListBuilder() {
        return new StringListBuilder();
    }

    @InterfaceView
    public static FormatHandlerListBuilder formatListBuilder() {
        return new FormatHandlerListBuilder();
    }

    @InterfaceView
    public static FormatHandlerMapBuilder formatMapBuilder() {
        return new FormatHandlerMapBuilder();
    }

    @InterfaceView
    public static InfoHandlerMapBuilder infoMapBuilder() {
        return new InfoHandlerMapBuilder();
    }

    private static InfoHandlerFactory infoHandlerFactory = new InfoHandlerFactory();

    @InterfaceView
    public static InfoHandlerFactory infoFactory() {
        return infoHandlerFactory;
    }

    private static FormatHandlerFactory formatHandlerFactory = new FormatHandlerFactory();

    @InterfaceView
    public static FormatHandlerFactory formatFactory() {
        return formatHandlerFactory;
    }

    @InterfaceView
    public static FormatHandler format(FormatHandler formatHandler) {
        return formatHandler;
    }

    @InterfaceView
    public static InfoHandler info(InfoHandler infoHandler) {
        return infoHandler;
    }

    public static final Pattern fc = Pattern.compile("[0-9a-fkm-or]*");

    @InterfaceView
    public static String fcToString(String value) {
        Matcher matcher = fc.matcher(value);
        if (matcher.matches()) {
            char[] chars = value.toCharArray();
            StringBuilder sb = new StringBuilder();
            for (char c : chars) {
                sb.append('§');
                sb.append(c);
            }
            return sb.toString();
        }
        return "";
    }

    @InterfaceView
    public static String fcSubstring(String s, int begin, int end) {
        String formats = "123456789abcdeflmnor";
        int index = 0;
        char[] chars = s.toCharArray();
        StringBuilder sb = new StringBuilder();
        int format = 0;
        int lastFormat = 0;
        for (int i = 0; i < chars.length; i++) {
            if (i + 1 < chars.length && chars[i] == '§' && formats.indexOf(chars[i + 1]) >= 0) {
                //format
                tf:
                {
                    char f = chars[i + 1];
                    if (f > 48 && f < 58) {
                        format = f - 48;
                        break tf;
                    }
                    if (f > 96 && f < 103) {
                        format = f - 87;
                        break tf;
                    }
                    if (f == 'l') {
                        format |= 0b10000;
                        break tf;
                    }
                    if (f == 'm') {
                        format |= 0b100000;
                        break tf;
                    }
                    if (f == 'n') {
                        format |= 0b1000000;
                        break tf;
                    }
                    if (f == 'o') {
                        format |= 0b10000000;
                        break tf;
                    }
                    if (f == 'r') {
                        format = 0;
                    }
                }

                i++;
            } else {
                if (index >= begin && index < end) {
                    if (format != lastFormat) {
                        lastFormat = format;
                        int a;
                        if ((a = format & 0b1111) > 0) {
                            if (a < 10) {
                                sb.append('§');
                                sb.append((char) (a + 48));
                            } else {
                                sb.append('§');
                                sb.append((char) (a + 87));
                            }
                        }
                        if ((format >> 4 & 1) == 1) {
                            sb.append('§');
                            sb.append('l');
                        }
                        if ((format >> 5 & 1) == 1) {
                            sb.append('§');
                            sb.append('m');
                        }
                        if ((format >> 6 & 1) == 1) {
                            sb.append('§');
                            sb.append('n');
                        }
                        if ((format >> 7 & 1) == 1) {
                            sb.append('§');
                            sb.append('o');
                        }
                        if (format == 0) {
                            sb.append('§');
                            sb.append('r');
                        }
                    }
                    sb.append(chars[i]);
                }
                index++;
            }
        }
        return sb.toString();
    }

    @InterfaceView
    public static String fcSubstring(String s, int begin) {
        return fcSubstring(s, begin, fcLength(s));
    }

    @InterfaceView
    public static int fcLength(String s) {
        return fcClear(s).length();
    }

    @InterfaceView
    public static String fcClear(String s) {
        return s.replaceAll("§[1-9a-flmnor]", "");
    }

}
