package com.example.android.popularmoviemvvmproject.data.local;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anamika Tripathi on 12/10/18.
 */
public class ArrayListConverter {
    @TypeConverter
    public static List<Integer> fromString(String value) {

        if (value == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.fromJson(value, type);
    }



    @TypeConverter
    public static String fromArrayList(List<Integer> list) {
        if (list == null) {
            return (null);
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Integer>>() {
        }.getType();
        return gson.toJson(list, type);
    }
}
