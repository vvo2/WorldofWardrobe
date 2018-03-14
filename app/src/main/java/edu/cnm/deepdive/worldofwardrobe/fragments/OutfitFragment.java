package edu.cnm.deepdive.worldofwardrobe.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.PictureUtils;
import java.io.File;


/**
 * A simple {@link Fragment} subclass.
 */
public class OutfitFragment extends Fragment {

  private ImageView topImageView;
  private String photoFileName;
  private File photoFile;


  public OutfitFragment() {
    // Required empty public constructor
  }


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    // Inflate the layout for this fragment
    final View view = inflater.inflate(R.layout.fragment_outfit, container, false);
    topImageView = view.findViewById(R.id.top_imageview);

    int itemID = 16;

    photoFileName = "IMG_" + itemID + ".jpg";
    File filesDir = getActivity().getFilesDir();
    photoFile = new File(filesDir, photoFileName);
    topImageView.setImageBitmap(PictureUtils.getScaledBitmap(photoFile.getPath(), 200, 200));
    return view;
  }

}
