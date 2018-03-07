package edu.cnm.deepdive.worldofwardrobe.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;
import java.util.List;

@Dao
public interface WardrobeDao {

  @Query("DELETE FROM wardrobe")
  public void deleteWardrobeTable();

  @Query("SELECT * FROM wardrobe")
  List<Wardrobe> getAll();

  @Query("SELECT * FROM wardrobe WHERE wardrobe_name = :wardrobeName")
  List<Wardrobe> getByName(String wardrobeName);

  @Insert
  void insertWardrobe(Wardrobe wardrobe);

  @Update
  void updateWardrobe(Wardrobe wardrobe);

  @Delete
  void deleteWardrobe(Wardrobe wardrobe);

}
