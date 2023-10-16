package rawfish.artedprvt.command.formats;

import rawfish.artedprvt.command.FormatHandler;
import rawfish.artedprvt.command.util.ArgumentsParser;
import rawfish.artedprvt.command.util.ParseResult;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

public class FormatHandlerRegex implements FormatHandler {
    private Pattern pattern;
    private String template;
    private Map<String, FormatHandler> groupHandlerMap;
    private FormatHandler failureHandler;

    /**
     * 正则表达式格式处理程序
     * @param pattern 正则 groupName作为key
     * @param template 输出模板 定义key的位置 使用§?key
     * @param groupHandlerMap 方案 定义key的格式处理程序 {key:formatter...}
     */
    public FormatHandlerRegex(
            Pattern pattern,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler failureHandler){
        this.pattern=pattern;
        this.template=template;
        this.groupHandlerMap =new HashMap<>(groupHandlerMap);
        this.failureHandler=failureHandler;
    }

    public FormatHandlerRegex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler failureHandler){
        this(Pattern.compile(regex),template, groupHandlerMap,failureHandler);
    }

    @Override
    public synchronized String handleFormat(String source) {
        ParseResult result= ArgumentsParser.parseRegex(pattern,groupHandlerMap.keySet(),source);
        if(result.isCorrect()){
            String s=template;
            Set<String> groups= groupHandlerMap.keySet();
            for(String group:groups){
                if(!group.isEmpty() && group.charAt(0)!='$'){
                    FormatHandler formatter= groupHandlerMap.get(group);
                    s=s.replace("§?"+group,
                            formatter.handleFormat(result.get(group)));
                }
            }
            return s;
        }else{
            return failureHandler.handleFormat(source);
        }
    }
}
