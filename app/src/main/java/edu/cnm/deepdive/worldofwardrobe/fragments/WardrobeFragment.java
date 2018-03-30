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
 * this is the landing fragment page where clicking a wardrobe will only
 * take the screen to the next fragment page, specified fron the OnClickListener.
 * in {@link WardrobePicAdapter#getView}
 */
public class WardrobeFragment extends Fragment {

  private ListView wardListView;
  private WardrobePicAdapter wardAdapter;

  /**
   * Required empty public constructor
   */
  public WardrobeFragment() {
  }

  /**
   * This is where the {@link WardrobePicAdapter} view get inflated in the ListView of this fragment.
   * @param inflater        inflate Wardrobe fragment layout
   * @param container           container for the view
   * @param savedInstanceState   to save the instance state
   * @return                    The view for this fragment
   */
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
