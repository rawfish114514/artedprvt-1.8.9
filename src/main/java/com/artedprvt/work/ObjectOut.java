package com.artedprvt.work;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

public class ObjectOut extends ObjectOutputStream {
    public ObjectOut(OutputStream out) throws IOException {
        super(out);
    }

    public void writeString(String string) throws IOException {
        writeObject(string);
    }
}