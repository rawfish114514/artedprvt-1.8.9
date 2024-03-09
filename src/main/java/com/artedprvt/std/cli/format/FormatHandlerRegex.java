package com.artedprvt.std.cli.format;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.util.ParseResult;
import com.artedprvt.std.cli.util.parser.ArgumentsParserRegex;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 格式
 */
@InterfaceView
public class FormatHandlerRegex implements FormatHandler {
    private Pattern pattern;
    private String template;
    private Map<String, FormatHandler> groupHandlerMap;
    private FormatHandler falseHandler;

    /**
     * @param pattern         正则表达式
     * @param template        输出模板 定义group的位置 使用§?group
     * @param groupHandlerMap 方案 定义group的格式处理程序 {groupName:formatter...}
     * @param falseHandler    失败的格式处理程序
     */
    @InterfaceView
    public FormatHandlerRegex(
            Pattern pattern,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        this.pattern = pattern;
        this.template = template;
        this.groupHandlerMap = new HashMap<>(groupHandlerMap);
        this.falseHandler = falseHandler;
    }

    @InterfaceView
    public FormatHandlerRegex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        this(Pattern.compile(regex), template, groupHandlerMap, falseHandler);
    }

    @Override
    @InterfaceView
    public synchronized String handleFormat(String source) {
        ParseResult result = ArgumentsParserRegex.parse(pattern, groupHandlerMap.keySet(), source);
        if (result.isCorrect()) {
            String s = template;
            Set<String> groups = groupHandlerMap.keySet();
            for (String group : groups) {
                if (!group.isEmpty() && group.charAt(0) != '$') {
                    FormatHandler formatter = groupHandlerMap.get(group);
                    s = s.replace("§?" + group,
                            formatter.handleFormat(result.get(group)));
                }
            }
            return s;
        } else {
            return falseHandler.handleFormat(source);
        }
    }
}
