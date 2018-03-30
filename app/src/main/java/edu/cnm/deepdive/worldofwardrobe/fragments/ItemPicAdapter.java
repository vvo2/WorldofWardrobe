package edu.cnm.deepdive.worldofwardrobe.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import edu.cnm.deepdive.worldofwardrobe.model.PictureUtils;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * A custom array adapter to populates the layout with items from the item
 * database. User can scroll the ListView and each item can be click and long click.
 */
public class ItemPicAdapter extends ArrayAdapter<Item> {

  private Activity topActivity; //instead of context and don't have to call Activity later
  private List<Item> itemPicList = new ArrayList<>();

  /**
   * Requires an activity and a list inorder to proceed with the rest of this custom adapter.
   * @param activity    this activity
   * @param list        Item list from the database
   */
  public ItemPicAdapter(@NonNull Activity activity, @LayoutRes List<Item> list) {
    super(activity, 0, list);
    topActivity = activity;
    itemPicList = list;
  }

  /**
   * Creates the view for the top, bottom, and accessories fragment and this custom array adapter.
   * OnClick the item will make the {@link OutfitFragment} display the item in the outfit layout.
   * OnLongClick will delete the item selected.
   * @param position      position of the item on the list
   * @param convertView   part of custom adapter
   * @param parent      parent root
   * @return            view for layout and custom array adapter
   */
  @NonNull
  @Override
  public View getView(final int position, @NonNull View convertView, @NonNull ViewGroup parent) {
    View listItem = convertView;
    if (listItem == null) {
      listItem = LayoutInflater.from(topActivity).inflate(R.layout.list_item, parent, false);
    }

    Item currentItemPic = itemPicList.get(position);

    final ImageView image = listItem.findViewById(R.id.image_item_tops);
    String photoFileName = "IMG_" + currentItemPic.getItemID() + ".jpg";
    File filesDir = topActivity.getFilesDir();
    File photoFile = new File(filesDir, photoFileName);
    final Bitmap bitmap = PictureUtils.getScaledBitmap(photoFile.getPath(), topActivity);
    image.setImageBitmap(bitmap);

    TextView name = listItem.findViewById(R.id.text_item_tops_name);
    name.setText(currentItemPic.getItemName());

    TextView location = listItem.findViewById(R.id.text_item_tops_location);
    location.setText(currentItemPic.getLocation());

    listItem.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        clicker(itemPicList.get(position), (byte) 0);
      }
    });

    listItem.setOnLongClickListener(new OnLongClickListener() {
      @Override
      public boolean onLongClick(View v) {

        clicker(itemPicList.get(position), (byte) 1);
        return true;
      }
    });

    return listItem;
  }

  @SuppressLint("StaticFieldLeak")
  private void clicker(final Item item, final byte click) {

    new AsyncTask<Item, Void, Long>() {

      @Override
      protected Long doInBackground(Item... items) {
        if (click == 1) {
          ((MainActivity) topActivity).getDatabase().getItemDao()
              .deleteOne(item);
        }
        return item.getItemID();
      }

      @Override
      protected void onPostExecute(Long longID) {
        String str = " was deleted";
        if (click == 0) {
          long typeID = item.getItemTypeID();
          ((MainActivity) topActivity).selectedItem(longID, typeID);
          str = " was selected";
        }

        Toast toast = Toast.makeText(getContext(), "item # " + longID + str, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
      }

    }.execute();
  }

}
