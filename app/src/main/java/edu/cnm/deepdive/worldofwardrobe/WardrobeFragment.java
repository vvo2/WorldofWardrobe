package edu.cnm.deepdive.worldofwardrobe;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class WardrobeFragment extends Fragment {

  private ImageView warbdrobeImage1;
  private ImageView warbdrobeImage2;
  private ImageView warbdrobeImage3;
  private ImageView warbdrobeImage4;

  public WardrobeFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    View v = inflater.inflate(R.layout.fragment_wardrobe, container, false);

    warbdrobeImage1 = (ImageView) v.findViewById(R.id.wardrobe1);
    warbdrobeImage1.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ((MainActivity) getActivity()).switchTab(1);
      }
    });

    warbdrobeImage2 = (ImageView) v.findViewById(R.id.wardrobe1);
    warbdrobeImage2.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ((MainActivity) getActivity()).switchTab(1);
      }
    });

    warbdrobeImage3 = (ImageView) v.findViewById(R.id.wardrobe1);
    warbdrobeImage3.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ((MainActivity) getActivity()).switchTab(1);
      }
    });

    warbdrobeImage4 = (ImageView) v.findViewById(R.id.wardrobe1);
    warbdrobeImage4.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ((MainActivity) getActivity()).switchTab(1);
      }
    });

    return v;
  }



}
