package edu.cnm.deepdive.worldofwardrobe;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import edu.cnm.deepdive.worldofwardrobe.dao.ItemDao;
import edu.cnm.deepdive.worldofwardrobe.dao.ItemTypeDao;
import edu.cnm.deepdive.worldofwardrobe.dao.WardrobeDao;
import edu.cnm.deepdive.worldofwardrobe.model.DateTypeConverter;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import edu.cnm.deepdive.worldofwardrobe.model.ItemType;
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;

/**
 * This is the database with schema version 1, and
 * set 3 entities and an abstract method for each entities.
 */
@Database(entities = {Item.class, ItemType.class, Wardrobe.class}, version = 1, exportSchema = true)
@TypeConverters(DateTypeConverter.class)
public abstract class ItemsDatabase extends RoomDatabase {

  /**
   * @return      List query from wardrobe Doa {@link WardrobeDao}
   */
  public abstract WardrobeDao getWardrobeDao();

  /**
   * @return      List query from item Doa {@link ItemDao}
   */
  public abstract ItemDao getItemDao();

  /**
   * @return      list query from Item type Doa {@link ItemTypeDao}
   */
  public abstract ItemTypeDao getItemTypeDao();

}
