package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.engine.*;
import rawfish.artedprvt.mi.group.ClassGroupLoader;

public class Rhino {
    public static final ScriptLanguage JAVASCRIPT=new ScriptLanguage("js");

    public static void init(){
        ClassGroupLoader.reg();
        ClassCollection.load(McpToSrgString.getMcpToSrgString());
        ScriptLanguage.reg(JAVASCRIPT);
        ScriptEngineFactorys.reg(new RhinoEngineFactory());
        ScriptStackParserFactorys.reg(new RhinoStackParserFactory());
        ServiceEngines.reg(new RhinoServiceEngine());
    }
}
