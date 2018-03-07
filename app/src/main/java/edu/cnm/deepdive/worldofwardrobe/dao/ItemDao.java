package edu.cnm.deepdive.worldofwardrobe.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import java.util.List;

@Dao
public interface ItemDao {

  @Query("DELETE FROM item")
  public void deleteItemTable();

  @Query("SELECT * FROM item")
  List<Item> getAll();

  @Query("SELECT * FROM item WHERE item_name = :itemName")
  List<Item> getByName(String itemName);

  @Insert
  void insertOne(Item item);

  @Insert
  void insertMany(List<Item> itemList);

  @Update
  void updateOne(Item item);

  @Delete
  void deleteOne(Item item);

  @Delete
  void deleteMany(List<Item> itemList);


}
