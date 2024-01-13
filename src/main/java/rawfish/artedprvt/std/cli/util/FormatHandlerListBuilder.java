package rawfish.artedprvt.std.cli.util;

import rawfish.artedprvt.api.Solvable;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.format.FormatHandlerAppend;
import rawfish.artedprvt.std.cli.format.FormatHandlerEmpty;
import rawfish.artedprvt.std.cli.format.FormatHandlerNumber;
import rawfish.artedprvt.std.cli.format.FormatHandlerRegex;
import rawfish.artedprvt.std.cli.format.FormatHandlerSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;


@Solvable
public class FormatHandlerListBuilder extends ArrayList<FormatHandler> {
    @Solvable
    public FormatHandlerListBuilder adds(FormatHandler formatHandler) {
        add(formatHandler);
        return this;

    }

    @Solvable
    public FormatHandlerListBuilder append(String string) {
        add(new FormatHandlerAppend(string));
        return this;
    }

    @Solvable
    public FormatHandlerListBuilder append(String... strings) {
        for (String string : strings) {
            append(string);
        }
        return this;
    }

    @Solvable
    public FormatHandlerListBuilder empty() {
        add(new FormatHandlerEmpty());
        return this;
    }

    @Solvable
    public FormatHandlerListBuilder number() {
        add(new FormatHandlerNumber());
        return this;
    }

    @Solvable
    public FormatHandlerListBuilder regex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        add(new FormatHandlerRegex(regex, template, groupHandlerMap, falseHandler));
        return this;
    }

    @Solvable
    public FormatHandlerListBuilder regex(
            Pattern regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        add(new FormatHandlerRegex(regex, template, groupHandlerMap, falseHandler));
        return this;
    }

    @Solvable
    public FormatHandlerListBuilder set(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler) {
        add(new FormatHandlerSet(set, trueHandler, falseHandler));
        return this;
    }
}
