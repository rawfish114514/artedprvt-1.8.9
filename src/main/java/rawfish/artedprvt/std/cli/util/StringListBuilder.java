package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;

import java.util.ArrayList;
import java.util.Arrays;

@Solvable
public class StringListBuilder extends ArrayList<String> {
    @Solvable
    public StringListBuilder adds(String string){
        add(string);
        return this;
    }

    @Solvable
    public StringListBuilder adds(String... strings){
        addAll(Arrays.asList(strings));
        return this;
    }
}
