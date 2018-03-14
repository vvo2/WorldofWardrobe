package edu.cnm.deepdive.worldofwardrobe.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class TopFragment extends Fragment {

  private ListView topListView;

  public TopFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View view = inflater.inflate(R.layout.fragment_top, container, false);
    topListView = view.findViewById(R.id.listview_top);


    new Thread(new Runnable() {
      @Override
      public void run() {
        List<Item> items = ((MainActivity)getActivity())
            .getDatabase().getItemDao().getByTypeID("1");
        final ListAdapter adapter = new ArrayAdapter<Item>(getActivity(),
            android.R.layout.simple_list_item_1, items);
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
//TODO custom adapter
}
