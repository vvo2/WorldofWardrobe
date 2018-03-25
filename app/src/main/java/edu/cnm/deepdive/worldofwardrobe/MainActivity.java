package edu.cnm.deepdive.worldofwardrobe;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Insert;
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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import edu.cnm.deepdive.worldofwardrobe.fragments.AccessoriesFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.EditFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.BottomFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.OutfitFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.TopFragment;
import edu.cnm.deepdive.worldofwardrobe.fragments.WardrobeFragment;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {

  public static final String TYPE_ID = "itemTypeID";
  private ItemsDatabase database;

  /**
   * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the
   * sections. We use a {@link FragmentPagerAdapter} derivative, which will keep every loaded
   * fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link
   * android.support.v4.app.FragmentStatePagerAdapter}.
   */
  private SectionsPagerAdapter mSectionsPagerAdapter;

  /**
   * The {@link ViewPager} that will host the section contents.
   */
  private ViewPager mViewPager;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    //Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    //setSupportActionBar(toolbar);
    // Create the adapter that will return a fragment for each of the three
    // primary sections of the activity.
    mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

    // Set up the ViewPager with the sections adapter.
    mViewPager = (ViewPager) findViewById(R.id.container);
    mViewPager.setAdapter(mSectionsPagerAdapter);

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mViewPager.setCurrentItem(4, true);
      }
    });

    FloatingActionButton fabEdit = (FloatingActionButton) findViewById(R.id.fab_edit);
    fabEdit.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        mViewPager.setCurrentItem(5, true);
      }
    });
  }


  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  public void switchTab(int position) { //TODO add entities
    mViewPager.setCurrentItem(position, true);
  }

  /**
   * create database if null and then insert raw query string for default wardrobe_name and item_type_name
   * @return
   */
  public ItemsDatabase getDatabase() {
    if (database == null) {
      database = Room.databaseBuilder
          (getApplicationContext(), ItemsDatabase.class, "itemDB").addCallback(new Callback() {
        @Override
        public void onCreate(@NonNull final SupportSQLiteDatabase db) {
          super.onCreate(db);
          Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
            @Override
            public void run() {
              db.execSQL("INSERT INTO wardrobe (`wardrobe_name`) VALUES ('formal'), ('casual'), ('medival'), ('armory')");
              db.execSQL("INSERT INTO item_type (`item_type_name`) VALUES ('top'), ('outer top'), ('bottom'), ('shoe'), ('head piece'), ('scarf'), ('glove');");
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
    private static final String ARG_SECTION_NUMBER = "section_number";

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

//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//        Bundle savedInstanceState) {
//      View rootView = inflater.inflate(R.layout.fragment_edit, container, false);
//
//      return rootView;
//    }
  }

  /**
   * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the
   * sections/tabs/pages.
   */
  public class SectionsPagerAdapter extends FragmentPagerAdapter {

    public SectionsPagerAdapter(FragmentManager fm) {
      super(fm);
    }

    @Override
    public Fragment getItem(int position) { //swipe left and right gets position
      // getItem is called to instantiate the fragment for the given page.
      // Return a PlaceholderFragment (defined as a static inner class below).
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
          return new OutfitFragment();
        case 5:
          return new EditFragment();
        default:
          return PlaceholderFragment.newInstance(position + 1);
      }

    }

    @Override
    public int getCount() {
      // Show 5 total pages.
      return 6;
    }

//    @Override
//    public CharSequence getPageTitle(int position) {
//      switch (position) {
//        case 0:
//          return "Wardrobes";
//        case 1:
//          return "Tops";
//        case 2:
//          return "Bottom";
//        case 3:
//          return "Accessories";
//        case 4:
//          return "Outfits";
//      }
//      return null;
//    }
  }
}

