package com.artedprvt.core.app.script.rhino;

import com.artedprvt.core.app.script.engine.ScriptEngineFactory;
import com.artedprvt.core.app.script.ScriptProcess;

public class RhinoEngineFactory implements ScriptEngineFactory {
    @Override
    public RhinoEngine create(ScriptProcess process) {
        return new RhinoEngine(process);
    }
}
