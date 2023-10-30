package rawfish.artedprvt.command.util;

import java.util.HashMap;
import java.util.Map;

public class ParseResult {
    private boolean correct;

    private Map<String,String> map;

    public ParseResult(boolean correct){
        this.correct=correct;
        map=new HashMap<>();
    }

    public void put(String key,String value){
        map.put(key,value);
    }
    public String get(String key){
        return map.get(key);
    }

    public boolean isCorrect(){
        return correct;
    }
}
