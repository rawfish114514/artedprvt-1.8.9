package com.artedprvt.core.app.script.engine;

import com.artedprvt.core.app.script.ScriptProcess;

public interface ScriptStackParserFactory {
    ScriptStackParser create(ScriptProcess process);
}
