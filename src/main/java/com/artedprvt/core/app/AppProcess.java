package com.artedprvt.core.app;

import com.artedprvt.core.AbstractProcess;
import com.artedprvt.core.ProcessController;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AppProcess<T extends AppProcess<T>> extends AbstractProcess<T> {
    protected List<String> args;

    public AppProcess(List<String> args) {
        super();
        this.args = new ArrayList<>(args);
    }

    @Override
    public ProcessIdLevel pidLevel() {
        return ProcessController.APP_PROCESS_ID_LEVEL;
    }

    public List<String> getArgs() {
        return Collections.unmodifiableList(args);
    }
}
