package edu.cnm.deepdive.worldofwardrobe.fragments;


import static edu.cnm.deepdive.worldofwardrobe.MainActivity.TYPE_ID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import java.util.List;


/**
 * AccessoriesFragment purpose is to show item type ID 5, 6, and 7 as defined in
 * {@link edu.cnm.deepdive.worldofwardrobe.MainActivity.SectionsPagerAdapter#getItem(int) getItem(int position)}
 */
public class AccessoriesFragment extends Fragment {

  private ListView accListView;
  private ItemPicAdapter adapter;

  /**
   * Required empty public constructor
   */
  public AccessoriesFragment() {
  }

  /**
   * Inflate the fragment_accessories and the {@link ItemPicAdapter} populate the ListView
   * with items that are type "head item", "face item", and "hand item" (type_ID 5, 6, and 7).
   * @param inflater          for the fragment_top layout
   * @param container           container for view
   * @param savedInstanceState  save the state
   * @return                  the view this fragment
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View view = inflater.inflate(R.layout.fragment_accessories, container, false);

    new Thread(new Runnable() {
      @Override
      public void run() {
        List listAcc = ((MainActivity)getActivity()).getDatabase()
            .getItemDao().getByTypeIDs(getArguments().getLongArray(TYPE_ID));
        accListView = view.findViewById(R.id.listview_accessories);
        adapter = new ItemPicAdapter(getActivity(), listAcc);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            (accListView).setAdapter(adapter);
          }
        });
      }
    }).start();

    return view;
  }

}
