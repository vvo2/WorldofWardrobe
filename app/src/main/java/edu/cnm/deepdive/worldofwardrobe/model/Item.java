package edu.cnm.deepdive.worldofwardrobe.model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

/**
 * Item entity with wardrobe ID and item type ID as foreign key,
 * last worn and worn counter are not yet coded in this app.
 */
@Entity(tableName = "item", foreignKeys = {@ForeignKey(entity = Wardrobe.class,
    parentColumns = "wardrobe_id",
    childColumns = "wardrobe_id",
    onDelete = ForeignKey.CASCADE), @ForeignKey(entity = ItemType.class,
    parentColumns = "item_type_id",
    childColumns = "item_type_id",
    onDelete = ForeignKey.CASCADE)})
public class Item {

  @PrimaryKey(autoGenerate = true)
  private long itemID;

  @ColumnInfo(name = "wardrobe_id")
  private long wardrobeID;

  @ColumnInfo(name = "item_type_id")
  private long itemTypeID;

  @ColumnInfo(name = "item_name", collate = ColumnInfo.NOCASE)
  private String itemName;

  @ColumnInfo(name = "price")
  private double itemPrice;

  @ColumnInfo(name = "location")
  private String location;

  @ColumnInfo(name = "last_worn")
  private String lastWorn;

  @ColumnInfo(name = "worn_count")
  private int wornCount;

  public long getItemID() {
    return itemID;
  }

  public void setItemID(long itemID) {
    this.itemID = itemID;
  }

  public long getWardrobeID() {
    return wardrobeID;
  }

  public void setWardrobeID(long wardrobeID) {
    this.wardrobeID = wardrobeID;
  }

  public long getItemTypeID() {
    return itemTypeID;
  }

  public void setItemTypeID(long itemTypeID) {
    this.itemTypeID = itemTypeID;
  }

  public String getItemName() {
    return itemName;
  }

  public void setItemName(String itemName) {
    this.itemName = itemName;
  }

  public double getItemPrice() {
    return itemPrice;
  }

  public void setItemPrice(double itemPrice) {
    this.itemPrice = itemPrice;
  }

  public String getLocation() {
    return location;
  }

  public void setLocation(String location) {
    this.location = location;
  }

  public String getLastWorn() {
    return lastWorn;
  }

  public void setLastWorn(String lastWorn) {
    this.lastWorn = lastWorn;
  }

  public int getWornCount() {
    return wornCount;
  }

  public void setWornCount(int wornCount) {
    this.wornCount = wornCount;
  }

  /**
   * Make Item return item name, price, and ID string instead of long path string.
   * @return    item name, price, and ID
   */
  public String toString() {
    return itemName + " " + itemPrice + " " + itemTypeID;
  }



}
