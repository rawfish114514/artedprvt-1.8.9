package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.app.script.ScriptProcess;
import com.artedprvt.core.app.script.engine.ScriptStackParserFactory;

public class RhinoStackParserFactory implements ScriptStackParserFactory {
    @Override
    public RhinoStackParser create(ScriptProcess process) {
        return new RhinoStackParser();
    }
}
