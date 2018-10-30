package com.example.xlc.monkey.volley;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

/**
 * @author:xlc
 * @date:2018/9/28
 * @descirbe: 处理json之类的方法，gson 解析
 */
public class JsonHelper {


    /**
     * json 数据转化成list
     *
     * @param str
     * @param <T>
     * @return
     */
    public static <T> List<T> parseToList(String str) {
        List<T> list = new Gson().fromJson(str, new TypeToken<List<T>>() {
        }.getType());

        return list;
    }

    /**
     * json 数据变成 map
     *
     * @param json
     * @return
     */
    public static HashMap<String, Object> parseToMap(String json) {
        Type type = new TypeToken<HashMap<String, Object>>() {
        }.getType();
        HashMap<String, Object> map = new Gson().fromJson(json, type);
        return map;
    }


    public static Object parseToObject(String json, Class clx) {
        if (TextUtils.isEmpty(json)) {
            return null;
        }

        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        Object obj = gson.fromJson(json, clx);
        return obj;
    }

    public static Object parseToObjectAll(String jsonString, Type type) {
        if (TextUtils.isEmpty(jsonString)) {
            return null;
        }
        //        Gson gson = new Gson();
        Gson gson = new GsonBuilder().registerTypeAdapterFactory(new NullStringToEmptyAdapterFactory()).create();
        Object obj = gson.fromJson(jsonString, type);
        return obj;
    }

    public static class NullStringToEmptyAdapterFactory<T> implements TypeAdapterFactory {
        @SuppressWarnings("unchecked")
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> type) {
            Class<T> rawType = (Class<T>) type.getRawType();
            if (rawType != String.class) {
                return null;
            }
            return (TypeAdapter<T>) new StringNullAdapter();
        }
    }

    public static class StringNullAdapter extends TypeAdapter<String> {
        @Override
        public String read(JsonReader reader) throws IOException {
            // TODO Auto-generated method stub
            if (reader.peek() == JsonToken.NULL) {
                reader.nextNull();
                return "";
            }
            return reader.nextString();
        }

        @Override
        public void write(JsonWriter writer, String value) throws IOException {
            // TODO Auto-generated method stub
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        }
    }


}
