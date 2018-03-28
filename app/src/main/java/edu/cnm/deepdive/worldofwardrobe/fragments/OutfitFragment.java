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

  private ImageView imgVw1;
  private ImageView imgVw2;
  private ImageView imgVw3;
  private ImageView imgVw4;
  private ImageView imgVw5;
  private ImageView imgVw6;
  private ImageView imgVw7;
  private ImageView imgView;
//  private long itemID;

  private String photoFileName;
  private File photoFile;

  public void imageTop1(long id, File filesDir, long type){
//    itemID = id;
    photoFileName = "IMG_" + id + ".jpg";
    photoFile = new File(filesDir, photoFileName);
    switch ((byte)type) {
      case 1: imgView = imgVw1; break;
      case 2: imgView = imgVw2; break;
      case 3: imgView = imgVw3; break;
      case 4: imgView = imgVw4; break;
      case 5: imgView = imgVw5; break;
      case 6: imgView = imgVw6; break;
      case 7: imgView = imgVw7; break;
    }
    if (isAdded()) {
      imgView.setImageBitmap(PictureUtils.getScaledBitmap(photoFile.getPath(), 200, 200));
    }
  }

  public OutfitFragment() {
    // Required empty public constructor
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_outfit, container, false);
    imgVw1 = view.findViewById(R.id.img_top);
    imgVw2 = view.findViewById(R.id.img_outer);
    imgVw3 = view.findViewById(R.id.img_bottom);
    imgVw4 = view.findViewById(R.id.img_shoe);
    imgVw5 = view.findViewById(R.id.img_head);
    imgVw6 = view.findViewById(R.id.img_neck);
    imgVw7 = view.findViewById(R.id.img_hand);
    imgVw4 = view.findViewById(R.id.img_shoe2);

//    photoFileName = "IMG_" + itemID + ".jpg";
//    File filesDir = getActivity().getFilesDir();
//    photoFile = new File(filesDir, photoFileName);
//    if (imgView != null) {
//      imgView.setImageBitmap(PictureUtils.getScaledBitmap(photoFile.getPath(), 200, 200));
//    }
    return view;
  }



}
