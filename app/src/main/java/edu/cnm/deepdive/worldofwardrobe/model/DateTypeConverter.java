package edu.cnm.deepdive.worldofwardrobe.model;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

public class DateTypeConverter {

  @TypeConverter
  public static Date TimestampToDate(Long value) {
    return value == null ? null : new Date(value);
  }

  @TypeConverter
  public static Long dateToTimestamp(Date date) {
    return date == null ? null : date.getTime();
  }
}
