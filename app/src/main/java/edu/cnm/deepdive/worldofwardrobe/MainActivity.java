package edu.cnm.deepdive.worldofwardrobe;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase.Callback;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import edu.cnm.deepdive.worldofwardrobe.fragments.AccessoriesFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.EditFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.BottomFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.OutfitFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.TopFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.WardrobeFragment;
import java.io.File;
import java.util.concurrent.Executors;

/**
 * Main class with android's pre-coded viewpager a {@link PlaceholderFragment place holder},
 * {@link SectionsPagerAdapter pager adapter}
 * and methods to view 6 fragments.
 */
public class MainActivity extends AppCompatActivity {

  /**
   * 4 extracted constant for the database and 1 constant for the pager.
   * The TYPE_ID is to import into {@link TopFragment},
   * {@link BottomFragment}, and {@link AccessoriesFragment}
   */
  public static final String TYPE_ID = "itemTypeID";
  public static final String ITEM_DB = "itemDB";
  public static final String DEFAULT_WARDROBE = "INSERT INTO wardrobe (`wardrobe_name`, `description`) VALUES ('formal', 'wardrobe for the business casual and formal attire'), ('casual', 'wardrobe for casual items');";
  public static final String DEFAULT_TYPES = "INSERT INTO item_type (`item_type_name`) VALUES ('top'), ('outer top'), ('bottom'), ('shoe'), ('head item'), ('face item'), ('hand item');";
  public static final String SECTION_NUMBER = "section_number";
  private ItemsDatabase database;
  private SectionsPagerAdapter mSectionsPagerAdapter;
  private ViewPager mViewPager;
  private OutfitFragment outfitFragment = new OutfitFragment();

  /**
   * Creates what applies to all fragments which is
   * the pager adapter, view pager, and floating buttons.
   * @param savedInstanceState    Need to save the instance state
   */
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    FloatingActionButton fab = findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mViewPager.setCurrentItem(4, true);
      }
    });

    FloatingActionButton fabEdit = findViewById(R.id.fab_edit);
    fabEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mViewPager.setCurrentItem(5, true);
      }
    });
  }

  /**
   * Created this method for the Wardrobe Adapter  to jump the fragments when user select a wardrobe.
   * @param position      position is the page number for the fragment
   */
  public void switchTab(int position) { //TODO add entities
    mViewPager.setCurrentItem(position, true);
  }

  /**
   * Create database if null and then insert raw query string for default wardrobe_name and item_type_name.
   * @return        return a database
   */
  public ItemsDatabase getDatabase() {
    if (database == null) {
      database = Room.databaseBuilder
          (getApplicationContext(), ItemsDatabase.class, ITEM_DB).addCallback(new Callback() {
        @Override
        public void onCreate(@NonNull final SupportSQLiteDatabase db) {
          super.onCreate(db);
          Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
              db.execSQL(DEFAULT_WARDROBE);
              db.execSQL(DEFAULT_TYPES);
            }
          });
        }
      }).build();
    }


    return database;
  }

  /**
   * A placeholder fragment containing a simple view.
   */
  public static class PlaceholderFragment extends Fragment {

    /**
     * The fragment argument representing the section number for this fragment.
     */
    private static final String ARG_SECTION_NUMBER = SECTION_NUMBER;

    public PlaceholderFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section number.
     */
    public static PlaceholderFragment newInstance(int sectionNumber) {
      PlaceholderFragment fragment = new PlaceholderFragment();
      Bundle args = new Bundle();
      args.putInt(ARG_SECTION_NUMBER, sectionNumber);
      fragment.setArguments(args);
      return fragment;
    }

  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    /**
     * User swipe the touch screen left and right to gets position
     * getItem is called to instantiate the fragment for the given page.
     * @param position    corresponding fragment number
     * @return        a PlaceholderFragment
     */
    @Override
    public Fragment getItem(int position) { //
      //
      // Return
      switch (position) {
        case 0:
          return new WardrobeFragment();
        case 1:
          TopFragment topFragment = new TopFragment();
          Bundle bundleTop = new Bundle();
          bundleTop.putLongArray(TYPE_ID, new long[]{1, 2});
          topFragment.setArguments(bundleTop);
          return topFragment;
        case 2:
          BottomFragment bottomFragment = new BottomFragment();
          Bundle bundleBottom = new Bundle();
          bundleBottom.putLongArray(TYPE_ID, new long[]{3, 4});
          bottomFragment.setArguments(bundleBottom);
          return bottomFragment;
        case 3:
          AccessoriesFragment accFragment = new AccessoriesFragment();
          Bundle bundleAcc = new Bundle();
          bundleAcc.putLongArray(TYPE_ID, new long[]{5, 6, 7});
          accFragment.setArguments(bundleAcc);
          return accFragment;
        case 4:
          return outfitFragment;
        case 5:
          return new EditFragment();
        default:
          return PlaceholderFragment.newInstance(position + 1);
      }

    }

    /**
     * This short method configures the total number of sliding fragments for this app.
     * @return
     */
    @Override
    public int getCount() {
      return 6;
    }
  }

  /**
   * Pass the item ID and item type ID from the
   * {@link edu.cnm.deepdive.worldofwardrobe.fragments.ItemPicAdapter ItemPicAdapter}
   * for the outfitFragment instance to prior to instantiating outfitFragment.
   * @param id      item ID from the item database
   * @param type    item type ID from the item database
   */
  public void selectedItem(long id, long type) {
    File filesDir = this.getFilesDir();
    outfitFragment.imageRefer(id, filesDir, type);
  }
}

