package com.qianxunclub.chatgptproxy.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.sql.Date;

public class SqlDateTypeAdapter extends TypeAdapter<Date> {
    @Override
    public synchronized Date read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return null;
        }
        return new Date(in.nextLong());
    }

    @Override
    public synchronized void write(JsonWriter out, Date value) throws IOException {
        out.value(value == null ? null : value.getTime());
    }
}
