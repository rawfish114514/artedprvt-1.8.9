package com.artedprvt.std.cli.util;

import com.artedprvt.std.cli.format.FormatHandlerAppend;
import com.artedprvt.std.cli.format.FormatHandlerEmpty;
import com.artedprvt.std.cli.format.FormatHandlerNumber;
import com.artedprvt.std.cli.format.FormatHandlerRegex;
import com.artedprvt.std.cli.format.FormatHandlerSet;
import com.artedprvt.iv.anno.InterfaceView;
import com.artedprvt.std.cli.FormatHandler;

import java.util.Collection;
import java.util.Map;
import java.util.regex.Pattern;

@InterfaceView
public class FormatHandlerFactory {
    @InterfaceView
    public FormatHandler append(String string) {
        return new FormatHandlerAppend(string);
    }

    @InterfaceView
    public FormatHandler empty() {
        return new FormatHandlerEmpty();
    }

    @InterfaceView
    public FormatHandler number() {
        return new FormatHandlerNumber();
    }

    @InterfaceView
    public FormatHandler regex(
            String regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        return new FormatHandlerRegex(regex, template, groupHandlerMap, falseHandler);
    }

    @InterfaceView
    public FormatHandler regex(
            Pattern regex,
            String template,
            Map<String, FormatHandler> groupHandlerMap,
            FormatHandler falseHandler) {
        return new FormatHandlerRegex(regex, template, groupHandlerMap, falseHandler);
    }

    @InterfaceView
    public FormatHandler set(
            Collection<String> set,
            FormatHandler trueHandler,
            FormatHandler falseHandler) {
        return new FormatHandlerSet(set, trueHandler, falseHandler);
    }
}
