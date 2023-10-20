package rawfish.artedprvt.command.util;

import java.util.ArrayList;
import java.util.Arrays;

public class StringListBuilder extends ArrayList<String> {
    public StringListBuilder adds(String string){
        add(string);
        return this;
    }

    public StringListBuilder adds(String... strings){
        addAll(Arrays.asList(strings));
        return this;
    }
}
