package edu.cnm.deepdive.worldofwardrobe.fragments;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.PictureUtils;
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WardrobePicAdapter extends ArrayAdapter<Wardrobe> {

  private Activity wardrobeActivity;
  private List<Wardrobe> wardrobePicList = new ArrayList<>();

  public WardrobePicAdapter(@NonNull Activity activity, @LayoutRes List<Wardrobe> list) {
    super(activity, 0, list);
    wardrobeActivity = activity;
    wardrobePicList = list;
  }

  @NonNull
  @Override
  public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
    View listWardrobe = convertView;
    if (listWardrobe == null)
      listWardrobe = LayoutInflater.from(wardrobeActivity).inflate(R.layout.list_wardrobe, parent, false);

    Wardrobe currentWardPic = wardrobePicList.get(position);

    ImageView image = listWardrobe.findViewById(R.id.image_wardrobe);
    String wardFileName = "WRD_" + currentWardPic.getWardrobeID() + ".jpg";
    File wardFileDir = wardrobeActivity.getFilesDir();
    File wardPicFile = new File(wardFileDir, wardFileName);
    Bitmap wardBitmap = PictureUtils.getScaledBitmap(wardPicFile.getPath(), wardrobeActivity);
    image.setImageBitmap(wardBitmap);

    TextView wardName = listWardrobe.findViewById(R.id.text_wardrobe_name);
    wardName.setText(currentWardPic.getWardrobeName());

    TextView description = listWardrobe.findViewById(R.id.text_wardrobe_desc);
    description.setText(currentWardPic.getDescription());

    return listWardrobe;
  }
}
