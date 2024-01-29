package com.artedprvt.core.app.java;

import com.artedprvt.core.app.AppTarget;
import com.artedprvt.core.app.AppType;

import java.net.URL;
import java.util.List;

public class JavaAppTarget implements AppTarget<JavaProcess> {
    private AppType<JavaProcess> appType;

    private URL url;

    public JavaAppTarget(AppType<JavaProcess> appType, URL url) {
        this.appType = appType;
        this.url = url;
    }

    @Override
    public JavaProcess open(List<String> args) throws Exception {
        return new JavaProcess(url.openStream(), args);
    }

    @Override
    public Object request(String request) {
        return null;
    }

    @Override
    public AppType<JavaProcess> getAppType() {
        return appType;
    }
}
