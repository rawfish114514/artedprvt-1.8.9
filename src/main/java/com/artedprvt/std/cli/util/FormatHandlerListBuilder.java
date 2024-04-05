package com.artedprvt.std.cli.util;

import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.FormatHandler;
import com.artedprvt.std.cli.format.FormatHandlerAppend;
import com.artedprvt.std.cli.format.FormatHandlerEmpty;
import com.artedprvt.std.cli.format.FormatHandlerNumber;
import com.artedprvt.std.cli.format.FormatHandlerRegex;
import com.artedprvt.std.cli.format.FormatHandlerSet;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;


@InterfaceView
public class FormatHandlerListBuilder extends ArrayList<FormatHandler> {
    @InterfaceView
    public FormatHandlerListBuilder adds(FormatHandler formatHandler) {
        add(formatHandler);
        return this;

    }

    @InterfaceView
    public FormatHandlerListBuilder append(String string) {
        add(new FormatHandlerAppend(string));
        return this;
    }

    @InterfaceView
    public FormatHandlerListBuilder append(String... strings) {
        for (String string : strings) {
            append(string);
        }
        return this;
    }

    @InterfaceView
    public FormatHandlerListBuilder empty() {
        add(new FormatHandlerEmpty());
        return this;
    }

    @InterfaceView
    public FormatHandlerListBuilder number() {
        add(new FormatHandlerNumber());
        return this;
    }

    @InterfaceView
    public FormatHandlerListBuilder regex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        add(new FormatHandlerRegex(regex, template, groupHandlerMap, falseHandler));
        return this;
    }

    @InterfaceView
    public FormatHandlerListBuilder regex(
            Pattern regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        add(new FormatHandlerRegex(regex, template, groupHandlerMap, falseHandler));
        return this;
    }

    @InterfaceView
    public FormatHandlerListBuilder set(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler) {
        add(new FormatHandlerSet(set, trueHandler, falseHandler));
        return this;
    }
}
