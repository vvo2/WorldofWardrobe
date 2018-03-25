package edu.cnm.deepdive.worldofwardrobe.fragments;


import static edu.cnm.deepdive.worldofwardrobe.MainActivity.TYPE_ID;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

  private ListView topListView;
  private ItemPicAdapter adapter;

  public TopFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View view = inflater.inflate(R.layout.fragment_top, container, false);

    new Thread(new Runnable() {
      @Override
      public void run() {
        List listTops = ((MainActivity)getActivity()).getDatabase()
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

  @Override
  public void onCreateContextMenu(ContextMenu menu, View v, ContextMenuInfo menuInfo) {

    super.onCreateContextMenu(menu, v, menuInfo);
  }
}
