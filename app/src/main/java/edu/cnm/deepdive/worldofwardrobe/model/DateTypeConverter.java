package edu.cnm.deepdive.worldofwardrobe.model;

import android.arch.persistence.room.TypeConverter;
import java.util.Date;

/**
 * This class only convert the date to timestamp or timestamp for the database.
 * The converting was meant to be use with the "last_worn" column.
 */
public class DateTypeConverter {

  /**
   * Convert from timestamp to Date
   * @param value   a Long
   * @return        a Date
   */
  @TypeConverter
  public static Date TimestampToDate(Long value) {
    return value == null ? null : new Date(value);
  }

  /**
   * Convert from date to timestamp
   * @param date    a Date
   * @return        a Long
   */
  @TypeConverter
  public static Long dateToTimestamp(Date date) {
    return date == null ? null : date.getTime();
  }
}
