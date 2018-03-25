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
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import edu.cnm.deepdive.worldofwardrobe.model.PictureUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ItemPicAdapter extends ArrayAdapter<Item> {

  private Activity topActivity; //instead of context and don't have to call Activity later
  private List<Item> itemPicList = new ArrayList<>();

  public ItemPicAdapter(@NonNull Activity activity, @LayoutRes List<Item> list) {
    super(activity, 0, list);
    topActivity = activity;
    itemPicList = list;
  }

  @NonNull
  @Override
  public View getView(int position, @NonNull View convertView, @NonNull ViewGroup parent) {
    View listItem = convertView;
    if (listItem == null) {
      listItem = LayoutInflater.from(topActivity).inflate(R.layout.list_item, parent, false);
    }

    Item currentItemPic = itemPicList.get(position);

    ImageView image = listItem.findViewById(R.id.image_item_tops);
    String photoFileName = "IMG_" + currentItemPic.getItemID() + ".jpg";
    File filesDir = topActivity.getFilesDir();
    File photoFile = new File(filesDir, photoFileName);
    Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), topActivity);
    image.setImageBitmap(bitmap);

    TextView name = listItem.findViewById(R.id.text_item_tops_name);
    name.setText(currentItemPic.getItemName());

    TextView location = listItem.findViewById(R.id.text_item_tops_location);
    location.setText(currentItemPic.getLocation());

    return listItem;
  }
}
