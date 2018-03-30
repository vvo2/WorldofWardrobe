package edu.cnm.deepdive.worldofwardrobe.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * the wardrobe entity for the database, have a unique ID, name for the wardrobe
 * and description of the wardrobe, getter and setter of name and description
 * and a overridden toString method to make the string useful.
 */
@Entity(tableName = "wardrobe")
public class Wardrobe {

  @ColumnInfo(name = "wardrobe_id")
  @PrimaryKey(autoGenerate = true)
  private long wardrobeID;

  @ColumnInfo(name = "wardrobe_name", collate = ColumnInfo.NOCASE)
  private String wardrobeName;

  @ColumnInfo(name = "description")
  private String description;

  public long getWardrobeID() {
    return wardrobeID;
  }

  public void setWardrobeID(long wardrobeID) {
    this.wardrobeID = wardrobeID;
  }

  public String getWardrobeName() {
    return wardrobeName;
  }

  public void setWardrobeName(String wardrobeName) {
    this.wardrobeName = wardrobeName;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  /**
   * Make wardrobeName return a name string instead of long path string.
   * @return    a name of the wardrobe
   */
  @Override
  public String toString() {
    return wardrobeName;
  }
}
