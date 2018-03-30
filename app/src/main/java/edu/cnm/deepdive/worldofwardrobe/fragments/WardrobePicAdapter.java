package edu.cnm.deepdive.worldofwardrobe.fragments;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;
import java.util.ArrayList;
import java.util.List;

/**
 * This Custom adapter is like the {@link ItemPicAdapter} except
 * it has nothing to do with the OutfitFragment.
 * The landing page get populated with the the wardrobe listed in the database.
 */
public class WardrobePicAdapter extends ArrayAdapter<Wardrobe> {

  private Activity wardrobeActivity;
  private List<Wardrobe> wardrobePicList = new ArrayList<>();

  /**
   * Requires an activity and a list inorder to proceed with the rest of this custom adapter.
   * @param activity    this activity
   * @param list        Wardrobe list from the database
   */
  public WardrobePicAdapter(@NonNull Activity activity, @LayoutRes List<Wardrobe> list) {
    super(activity, 0, list);
    wardrobeActivity = activity;
    wardrobePicList = list;
  }

  /**
   * Creates the view for the wardrobe fragment
   * and populate the lis_wardrobe.xml.
   * The OnClick to the list will take to the {@link TopFragment} and
   * OnLongClick to the list will delete the selected wardrobe.
   * @param position      position of the item on the list
   * @param convertView   part of custom adapter
   * @param parent      parent root
   * @return            view for layout and custom array adapter
   */
  @NonNull
  @Override
  public View getView(final int position, @NonNull View convertView, @NonNull ViewGroup parent) {
    View listWardrobe = convertView;
    if (listWardrobe == null) {
      listWardrobe = LayoutInflater.from(wardrobeActivity)
          .inflate(R.layout.list_wardrobe, parent, false);
    }

    Wardrobe currentWardPic = wardrobePicList.get(position);

    ImageView image = listWardrobe.findViewById(R.id.image_wardrobe);
//    String wardFileName = "WRD_" + currentWardPic.getWardrobeID() + ".jpg";
//    File wardFileDir = wardrobeActivity.getFilesDir();
//    File wardPicFile = new File(wardFileDir, wardFileName);
//    Bitmap wardBitmap = PictureUtils.getScaledBitmap(wardPicFile.getPath(), wardrobeActivity);
//    image.setImageBitmap(wardBitmap);
    //TODO finish camera and gallery intent wardrobe.

    TextView wardName = listWardrobe.findViewById(R.id.text_wardrobe_name);
    wardName.setText(currentWardPic.getWardrobeName());

    TextView description = listWardrobe.findViewById(R.id.text_wardrobe_desc);
    description.setText(currentWardPic.getDescription());

    listWardrobe.setOnClickListener(new OnClickListener() {
      @Override
      public void onClick(View v) {
        ((MainActivity) wardrobeActivity).switchTab(1);
      }
    });

    listWardrobe.setOnLongClickListener(new OnLongClickListener() {
      @SuppressLint("StaticFieldLeak")
      @Override
      public boolean onLongClick(View v) {

        new AsyncTask<Void, Void, Void>() {
          @Override
          protected Void doInBackground(Void... voids) {
            ((MainActivity) wardrobeActivity).getDatabase().getWardrobeDao()
                .deleteWardrobe(wardrobePicList.get(position));
            return null;
          }

          @Override
          protected void onPostExecute(Void aVoid) {
            Toast toast = Toast.makeText(wardrobeActivity, "wardrobe deleted", Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
          }
        }.execute();

        return true;
      }
    });

    return listWardrobe;
  }
}
