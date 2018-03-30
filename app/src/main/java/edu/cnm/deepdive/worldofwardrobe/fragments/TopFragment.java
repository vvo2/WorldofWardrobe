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
 * This fragment purpose is to show item type ID 1 and 2 as defined in
 * {@link edu.cnm.deepdive.worldofwardrobe.MainActivity.SectionsPagerAdapter#getItem(int) getItem(int position)}.
 */
public class TopFragment extends Fragment{

  private ListView topListView;
  private ItemPicAdapter adapter;

  /**
   * Required empty public constructor
   */
  public TopFragment() {
  }

  /**
   * TopFragment Inflate the fragment_top and the {@link ItemPicAdapter} populate the ListView
   * with items that are type "tops" and "outer top" (type_ID 1 and 2).
   * @param inflater          for the fragment_top layout
   * @param container           container for view
   * @param savedInstanceState  save the state
   * @return                  the view for this fragment
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_top, container, false);

    new Thread(new Runnable() {
      @Override
      public void run() {
        List listTops = ((MainActivity) getActivity()).getDatabase()
            .getItemDao().getByTypeIDs(getArguments().getLongArray(TYPE_ID));
        topListView = view.findViewById(R.id.listview_tops);
        adapter = new ItemPicAdapter(getActivity(), listTops);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            (topListView).setAdapter(adapter);
          }
        });
      }
    }).start();

    return view;
  }

}
