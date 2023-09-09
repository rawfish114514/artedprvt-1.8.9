package rawfish.artedprvt.core.rhino;

import rawfish.artedprvt.core.ScriptLanguage;
import rawfish.artedprvt.core.engine.*;

public class Rhino {
    public static final ScriptLanguage JAVASCRIPT=new ScriptLanguage("js");

    public static void init(){
        ClassCollection.load(McpToSrgString.getMcpToSrgString());
        ScriptLanguage.reg(JAVASCRIPT);
        ScriptEngineFactorys.reg(new RhinoEngineFactory());
        ScriptStackParserFactorys.reg(new RhinoStackParserFactory());
        ServiceEngines.reg(new RhinoServiceEngine());
    }
}
