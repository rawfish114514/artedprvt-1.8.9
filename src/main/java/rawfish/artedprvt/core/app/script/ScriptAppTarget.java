package rawfish.artedprvt.core.app.script;

import rawfish.artedprvt.core.app.AppTarget;
import rawfish.artedprvt.core.app.AppType;
import rawfish.artedprvt.core.app.script.engine.Engines;
import rawfish.artedprvt.core.app.script.engine.ServiceEngine;
import rawfish.artedprvt.core.app.script.struct.AspFileLoader;
import rawfish.artedprvt.core.app.script.struct.FileLoader;
import rawfish.artedprvt.std.cli.CompleteInterface;
import rawfish.artedprvt.std.cli.FormatHandler;
import rawfish.artedprvt.std.cli.FormatInterface;
import rawfish.artedprvt.std.cli.InfoHandler;
import rawfish.artedprvt.std.cli.InfoInterface;
import rawfish.artedprvt.std.cli.util.FormatHandlerListBuilder;
import rawfish.artedprvt.std.cli.util.Literals;

import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ScriptAppTarget implements AppTarget<ScriptProcess>, CompleteInterface, FormatInterface, InfoInterface {
    private AppType<ScriptProcess> appType;

    private URL url;

    private FileLoader fileLoader;

    public ScriptAppTarget(AppType<ScriptProcess> appType, URL url) {
        this.appType = appType;
        this.url = url;

        try {
            fileLoader = new AspFileLoader(url.openStream());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        literal();
    }

    @Override
    public ScriptProcess open(List<String> args) throws Exception {
        return new ScriptProcess(url.openStream(), args);
    }

    @Override
    public Object request(String request) {
        if (request.startsWith("literal.complete")) {
            return (CompleteInterface) this;
        }
        if (request.startsWith("literal.format")) {
            return (FormatInterface) this;
        }
        if (request.startsWith("literal.info")) {
            return (InfoInterface) this;
        }
        return null;
    }

    @Override
    public AppType<ScriptProcess> getAppType() {
        return appType;
    }

    private String code = null;
    private String abbr = null;


    private void literal() {
        for (ScriptLanguage language : Engines.getLanguages()) {
            String abbr = language.getAbbr();
            String f = readLiteralFile(abbr);
            if (f != null) {
                code = f;
                this.abbr = abbr;
                break;
            }
        }
    }

    private String readLiteralFile(String abbr) {
        String target = "literal." + abbr;
        return fileLoader.getContent(target);
    }

    @Override
    public List<String> complete(List<String> args) {
        if (code != null) {
            ServiceEngine engine = Engines.getService(abbr);
            if (engine != null) {
                try {
                    Object result = engine.unwrap(engine.call(code, "complete", args, Literals.class));
                    if (result instanceof Collection) {
                        List<String> stringList = new ArrayList<>();
                        for (Object obj : (Collection) result) {
                            stringList.add(String.valueOf(obj));
                        }
                        return new ArrayList<>(stringList);
                    } else {
                        return Literals.emptyComplete();
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return Literals.emptyComplete();
    }

    @Override
    public List<? extends FormatHandler> format(List<String> args) {
        if (code != null) {
            FormatHandlerListBuilder builder = Literals.formatListBuilder();
            ServiceEngine engine = Engines.getService(abbr);
            if (engine != null) {
                try {
                    Object result = engine.unwrap(engine.call(code, "format", args, Literals.class));
                    if (result instanceof Collection) {
                        for (Object obj : (Collection) result) {
                            if (obj instanceof FormatHandler) {
                                builder.add((FormatHandler) obj);
                            } else {
                                builder.append(String.valueOf(obj));
                            }
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                return builder;
            }
        }
        return Literals.emptyFormat();
    }

    @Override
    public InfoHandler info(List<String> args) {
        if (code != null) {
            ServiceEngine engine = Engines.getService(abbr);
            if (engine != null) {
                try {
                    Object result = engine.unwrap(engine.call(code, "info", args, Literals.class));
                    if (result != null) {
                        if (result instanceof InfoHandler) {
                            return (InfoHandler) result;
                        }
                        return Literals.infoFactory().string(String.valueOf(result));
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
        return Literals.emptyInfo();
    }
}
