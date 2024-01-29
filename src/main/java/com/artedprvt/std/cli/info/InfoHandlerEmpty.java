package com.artedprvt.std.cli.info;

import com.artedprvt.api.Solvable;
import com.artedprvt.std.cli.InfoHandler;

@Solvable
public class InfoHandlerEmpty implements InfoHandler {
    @Override
    @Solvable
    public String handleInfo(String source) {
        return "";
    }
}
