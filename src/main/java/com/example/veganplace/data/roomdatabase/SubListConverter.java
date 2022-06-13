package com.example.veganplace.data.roomdatabase;

import androidx.room.TypeConverter;

import com.example.veganplace.data.modelrecetas.Sub;
import com.google.gson.Gson;

import java.util.List;



public class SubListConverter {
    // XXX - Consider the string split format might not be able to cater all data sources
    public static final String STRING_SPLIT_REGEX = ";;;";

    @TypeConverter
    public static List<Sub> toStringList(String string) {
        if (string == null) {
            return null;
        } else {
            Gson gson = new Gson();
            List<Sub> ts=gson.fromJson(string , List.class);

            return ts;
        }
    }

    @TypeConverter
    public static String toString(List<Sub> stringList) {
        if (stringList == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            for(Sub s : stringList) {
                builder.append(s).append(STRING_SPLIT_REGEX);
            }
            builder.setLength(builder.length() - STRING_SPLIT_REGEX.length());
            return builder.toString();
        }
    }
}