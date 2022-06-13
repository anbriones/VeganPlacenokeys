package com.example.veganplace.data.roomdatabase;

import androidx.room.TypeConverter;

import com.example.veganplace.data.modelrecetas.Digest;
import com.google.gson.Gson;

import java.util.List;


public class DigestListConverter {
    // XXX - Consider the string split format might not be able to cater all data sources
    public static final String STRING_SPLIT_REGEX = ";;;";

    @TypeConverter
    public static List<Digest> toStringList(String string) {
        if (string == null) {
            return null;
        } else {
            Gson gson = new Gson();
            List<Digest> ts=gson.fromJson(string , List.class);

            return ts;
        }
    }

    @TypeConverter
    public static String toString(List<Digest> stringList) {
        if (stringList == null) {
            return null;
        } else {
            StringBuilder builder = new StringBuilder();
            for(Digest s : stringList) {
                builder.append(s).append(STRING_SPLIT_REGEX);
            }
            builder.setLength(builder.length() - STRING_SPLIT_REGEX.length());
            return builder.toString();
        }
    }
}