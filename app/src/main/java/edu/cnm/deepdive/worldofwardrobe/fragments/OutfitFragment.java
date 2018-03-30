package edu.cnm.deepdive.worldofwardrobe.fragments;


import android.graphics.Bitmap;
import android.graphics.Matrix;
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
 * The OutfitFragment display the one of each selected item type
 * from the Top, Bottom, and Accessories Fragemtn to form an outfit.
 */
public class OutfitFragment extends Fragment {

  /**
   * A string for photo file to use in a serialize saved state.
   */
  public static final String PHOTO_FILE = "PHOTO_FILE";
  private File[] img = new File[7];
  private ImageView[] imgViews = new ImageView[7];

  private String photoFileName;
  private File photoFile;
  private ImageView imgViewShoe2;

  /**
   * the item id reference the item photo files
   * while the 7 type id reference the 7 ImageView.
   * @param id        item ID
   * @param filesDir   for photo file
   * @param type      item type ID
   */
  public void imageRefer(long id, File filesDir, long type) {
    photoFileName = "IMG_" + id + ".jpg";
    photoFile = new File(filesDir, photoFileName);

    if (isAdded()) {
      imgViews[(int) (type - 1)]
          .setImageBitmap(PictureUtils.getScaledBitmap(photoFile.getPath(), 200, 200));
    }
    img[(int) type - 1] = photoFile;
  }

  /**
   * Required empty public constructor
   */
  public OutfitFragment() {
  }

  /**
   * Display each referenced imageview and close the shoe to
   * flip the shoe image by using matrix and show it on the 2nd shoe ImageView.
   * @param inflater           for the fragment_outfit layout
   * @param container           container for view
   * @param savedInstanceState  save the state
   * @return                  the view for this fragment
   */
  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    final View view = inflater.inflate(R.layout.fragment_outfit, container, false);
    imgViews[0] = view.findViewById(R.id.img_top);
    imgViews[1] = view.findViewById(R.id.img_outer);
    imgViews[2] = view.findViewById(R.id.img_bottom);
    imgViews[3] = view.findViewById(R.id.img_shoe);
    imgViews[4] = view.findViewById(R.id.img_head);
    imgViews[5] = view.findViewById(R.id.img_neck);
    imgViews[6] = view.findViewById(R.id.img_hand);
    imgViewShoe2 = view.findViewById(R.id.img_shoe2);

    if (img[3] != null) {
      Bitmap bOutput, bInput;
      bInput = PictureUtils.getScaledBitmap(img[3].getPath(), 200, 200);
      Matrix matrix = new Matrix();
      matrix.preScale(-1.0f, 1.0f);
      bOutput = Bitmap
          .createBitmap(bInput, 0, 0, bInput.getWidth(), bInput.getHeight(), matrix, true);
      imgViewShoe2.setImageBitmap(bOutput);
    }

    for (int i = 0; i < img.length; i++) {
      if (img[i] != null) {
        imgViews[i].setImageBitmap(PictureUtils.getScaledBitmap(img[i].getPath(), 200, 200));
      }
    }

    return view;
  }

  /**
   * This keep the selected item images from disappearing
   * when user is 2 fragment screen away form this fragment
   * @param savedInstanceState    save the instance state
   */
  @Override
  public void onActivityCreated(Bundle savedInstanceState) {
    super.onActivityCreated(savedInstanceState);

    if (savedInstanceState != null) {
      img = (File[]) savedInstanceState.getSerializable(PHOTO_FILE);
    }
  }

  /**
   * This serialize the image files during save instance state
   * @param outState    the bundle of serialized image files
   */
  @Override
  public void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
    outState.putSerializable(PHOTO_FILE, img);

    //Save the fragment's state here
  }

}
