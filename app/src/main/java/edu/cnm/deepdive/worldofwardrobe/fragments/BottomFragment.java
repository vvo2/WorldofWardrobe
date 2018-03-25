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

public class BottomFragment extends Fragment {

  private ListView bottomListView;
  private ItemPicAdapter adapter;

  public BottomFragment() {

  }

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
