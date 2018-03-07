package edu.cnm.deepdive.worldofwardrobe.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import edu.cnm.deepdive.worldofwardrobe.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class OutfitFragment extends Fragment {


  public OutfitFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View view = inflater.inflate(R.layout.fragment_outfit, container, false);

    return view;
  }

}
