package rawfish.artedprvt.core.engine;

import java.util.ArrayList;
import java.util.List;

public class ScriptStackParserFactorys {
    private static List<ScriptStackParserFactory<? extends ScriptStackParser>> factoryList=new ArrayList<>();

    public static void reg(ScriptStackParserFactory<? extends ScriptStackParser> scriptStackParserFactory){
        factoryList.add(scriptStackParserFactory);
    }

    public static List<ScriptStackParserFactory<? extends ScriptStackParser>> factories(){
        return new ArrayList<>(factoryList);
    }
}
