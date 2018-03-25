package edu.cnm.deepdive.worldofwardrobe.fragments;


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
public class WardrobeFragment extends Fragment {

  private ListView wardListView;
  private WardrobePicAdapter wardAdapter;

  public WardrobeFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View viewWard = inflater.inflate(R.layout.fragment_wardrobe, container, false);

    new Thread(new Runnable() {
      @Override
      public void run() {
        List listWard = ((MainActivity)getActivity()).getDatabase().getWardrobeDao().getAll();
        wardListView = viewWard.findViewById(R.id.listview_wardrobe);
        wardAdapter = new WardrobePicAdapter(getActivity(), listWard);
        getActivity().runOnUiThread(new Runnable() {
          @Override
          public void run() {
            (wardListView).setAdapter(wardAdapter);
          }
        });
      }
    }).start();

    return viewWard;
  }

}
