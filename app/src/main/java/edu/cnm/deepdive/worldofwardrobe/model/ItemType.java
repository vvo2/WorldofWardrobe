package edu.cnm.deepdive.worldofwardrobe.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;


@Entity(tableName = "item_type")
public class ItemType {

  @ColumnInfo (name = "item_type_id")
  @PrimaryKey(autoGenerate = true)
  private long itemTypeID;

  @ColumnInfo(name = "item_type_name", collate = ColumnInfo.NOCASE)
  private String itemTypeName;

  public long getItemTypeID() {
    return itemTypeID;
  }

  public void setItemTypeID(long itemTypeID) {
    this.itemTypeID = itemTypeID;
  }

  public String getItemTypeName() {
    return itemTypeName;
  }

  public void setItemTypeName(String itemTypeName) {
    this.itemTypeName = itemTypeName;
  }

  @Override
  public String toString() {
    return itemTypeName;
  }
}
