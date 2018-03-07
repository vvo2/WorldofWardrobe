package edu.cnm.deepdive.worldofwardrobe.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.worldofwardrobe.model.ItemType;
import java.util.List;

@Dao
public interface ItemTypeDao {

  @Query("DELETE FROM item_type")
  public void deleteTypeTable();

  @Query("SELECT * FROM item_type")
  List<ItemType> getAll();

  @Query("SELECT * FROM item_type WHERE item_type_name = :itemTypeName")
  List<ItemType> getByName(String itemTypeName);

  @Insert
  void insertType(ItemType itemType);

  @Update
  void updateType(ItemType itemType);

  @Delete
  void deleteType(ItemType itemType);

}
