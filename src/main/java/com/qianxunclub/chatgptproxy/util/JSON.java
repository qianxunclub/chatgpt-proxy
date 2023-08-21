package com.qianxunclub.chatgptproxy.util;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.qianxunclub.chatgptproxy.util.gson.DateUnixtimeMillisTypeAdapter;
import com.qianxunclub.chatgptproxy.util.gson.NullableTypeAdapter;
import com.qianxunclub.chatgptproxy.util.gson.SqlDateTypeAdapter;

import java.util.Date;

public class JSON {

    public static Gson getGson() {
        return getGsonBuilder().create();
    }

    /**
     * 获取 GsonBuilder
     */
    public static GsonBuilder getGsonBuilder() {
        return new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .registerTypeAdapter(
                        Date.class,
                        new NullableTypeAdapter<>(new DateUnixtimeMillisTypeAdapter(true))
                )
                .registerTypeAdapter(
                        java.sql.Date.class,
                        new NullableTypeAdapter<>(new SqlDateTypeAdapter())
                );
    }
}
