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
 * A simple {@link Fragment} subclass.
 */
public class AccessoriesFragment extends Fragment {

  private ListView accListView;
  private ItemPicAdapter adapter;

  public AccessoriesFragment() {
    // Required empty public constructor
  }


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
