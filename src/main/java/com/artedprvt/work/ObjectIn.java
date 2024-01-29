package com.artedprvt.work;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

public class ObjectIn extends ObjectInputStream {
    public ObjectIn(InputStream in) throws IOException {
        super(in);
    }

    public String readString() throws IOException, ClassNotFoundException {
        return (String) readObject();
    }
}