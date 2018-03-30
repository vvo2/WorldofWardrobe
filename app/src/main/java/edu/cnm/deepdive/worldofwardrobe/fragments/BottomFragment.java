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
 * BottomFragment purpose is to show item type ID 3 and 4 as defined in
 * {@link edu.cnm.deepdive.worldofwardrobe.MainActivity.SectionsPagerAdapter#getItem(int) getItem(int position)}.
 */
public class BottomFragment extends Fragment {

  private ListView bottomListView;
  private ItemPicAdapter adapter;

  /**
   * Required empty public constructor
   */
  public BottomFragment() {
  }

  /**
   * inflate the fragment_bottom and the {@link ItemPicAdapter} populate the ListView
   * with items that are type "bottom" and "shoe" (type_ID 3 and 4).
   * @param inflater          for the fragment_top layout
   * @param container           container for view
   * @param savedInstanceState  save the state
   * @return                  the view this fragment
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_bottom, container, false);

    new Thread(new Runnable() {
      @Override
      public void run() {
        List listBottoms = ((MainActivity)getActivity()).getDatabase()
            .getItemDao().getByTypeIDs(getArguments().getLongArray(TYPE_ID));
        bottomListView = view.findViewById(R.id.listview_bottom);
        adapter = new ItemPicAdapter(getActivity(), listBottoms);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            (bottomListView).setAdapter(adapter);
          }
        });
      }
    }).start();

    return view;
  }

}
