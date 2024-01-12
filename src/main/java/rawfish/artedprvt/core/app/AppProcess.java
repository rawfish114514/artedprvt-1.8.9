package rawfish.artedprvt.core.app;

import rawfish.artedprvt.core.AbstractProcess;
import rawfish.artedprvt.core.ProcessController;

import java.lang.reflect.Array;
import java.util.Collections;
import java.util.List;

public abstract class AppProcess<T extends AppProcess<T>> extends AbstractProcess<T> {
    protected List<String> args;
    public AppProcess(List<String> args){
        super();
        this.args=args;
    }
    @Override
    public ProcessIdLevel pidLevel() {
        return ProcessController.APP_PROCESS_ID_LEVEL;
    }

    public List<String> getArgs(){
        return Collections.unmodifiableList(args);
    }
}
