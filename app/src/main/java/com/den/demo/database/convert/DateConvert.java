package com.den.demo.database.convert;

import androidx.room.TypeConverter;

import java.util.Date;

public class DateConvert {
    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }
    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}