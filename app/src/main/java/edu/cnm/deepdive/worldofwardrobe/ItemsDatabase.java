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

@Database(entities = {Item.class, ItemType.class, Wardrobe.class}, version = 1, exportSchema = true)
@TypeConverters(DateTypeConverter.class)
public abstract class ItemsDatabase extends RoomDatabase {

  public abstract WardrobeDao getWardrobeDao();

  public abstract ItemDao getItemDao();

  public abstract ItemTypeDao getItemTypeDao();

}
