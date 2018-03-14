package edu.cnm.deepdive.worldofwardrobe.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import edu.cnm.deepdive.worldofwardrobe.model.ItemType;
import edu.cnm.deepdive.worldofwardrobe.model.PictureUtils;
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;
import java.io.File;
import java.util.List;

public class EditFragment extends Fragment implements OnClickListener {

  private static final int REQUEST_PHOTO= 2;

  private Spinner spinnerType;
  private Spinner spinnerWardrobe;
  private ImageButton addButton;
  private Button addWardrobeButton;
  private Button addTypeButton;
  private ImageView photoView;
  private String photoFileName;
  private View view;
  private Item item;
  File photoFile; //dont need to be final regardless from anonomous

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_edit, container, false);

    spinnerType = view.findViewById(R.id.spinner_type);
    spinnerWardrobe = view.findViewById(R.id.spinner_wardrobe);

    addButton = (ImageButton) view.findViewById(R.id.button_additem);
    addButton.setOnClickListener(this);
    addTypeButton = (Button) view.findViewById(R.id.button_add_itemtype);
    addTypeButton.setOnClickListener(this);
    addWardrobeButton = (Button) view.findViewById(R.id.button_add_wardrobe);
    addWardrobeButton.setOnClickListener(this);

    photoView = (ImageView) view.findViewById(R.id.view_photo);
    photoView.setOnClickListener(this);


    new TypesGetter().execute();
    new WardrobesGetter().execute();

    return view;
  }

  @Override
  public void onClick(View v) {

    if (v == addButton) {

      final String strName = ((EditText)
          view.findViewById(R.id.edittext_name)).getText().toString();
      final double price = Double.parseDouble(((EditText)
          view.findViewById(R.id.edittext_price)).getText().toString());
      final String strLocation = ((EditText)
          view.findViewById(R.id.edittext_location)).getText().toString();
      final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

      new Thread(new Runnable() {
        @Override
        public void run() {



          Item addItem = new Item();
          addItem.setItemTypeID(((ItemType) spinnerType.getSelectedItem()).getItemTypeID());
          addItem.setWardrobeID(((Wardrobe) spinnerWardrobe.getSelectedItem()).getWardrobeID());
          addItem.setItemName(strName);
          addItem.setItemPrice(price);
          addItem.setLocation(strLocation);
          addItem.setWornCount(0);

          long itemID = ((MainActivity) getActivity()).getDatabase().getItemDao().insertOne(addItem);

          photoFileName = "IMG_" + itemID + ".jpg";
          File filesDir = getActivity().getFilesDir();
          photoFile = new File(filesDir, photoFileName);

          Uri uri = FileProvider.getUriForFile(getActivity(),
              "edu.cnm.deepdive.worldofwardrobe.FileProvider", photoFile);
          cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);

          List<ResolveInfo> cameraActivities = getActivity()
              .getPackageManager().queryIntentActivities(cameraIntent,
                  PackageManager.MATCH_DEFAULT_ONLY);

          for (ResolveInfo activity : cameraActivities) {
            getActivity().grantUriPermission(activity.activityInfo.packageName,
                uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
          Log.v("fileLocation", "where's my file?" + uri );
            startActivityForResult(cameraIntent, REQUEST_PHOTO);



          //TODO finsih camera intent
        }
      }).start();
    } else if (v == addTypeButton) {
      final String strTypeName = ((EditText)
          view.findViewById(R.id.edittext_itemtype)).getText().toString();

      new Thread(new Runnable() {
        @Override
        public void run() {
          ItemType addType = new ItemType();
          addType.setItemTypeName(strTypeName);

          ((MainActivity) getActivity()).getDatabase().getItemTypeDao().insertType(addType);
        }
      }).start();
    } else if (v == addWardrobeButton) {
      final String strWardrobeName = ((EditText)
          view.findViewById(R.id.edittext_wardrobe)).getText().toString();

      new Thread(new Runnable() {
        @Override
        public void run() {
          Wardrobe addWardrobe = new Wardrobe();
          addWardrobe.setWardrobeName(strWardrobeName);

          ((MainActivity) getActivity()).getDatabase().getWardrobeDao().insertWardrobe(addWardrobe);
        }
      }).start();
    } else if (v == photoView) {


    } //TODO OnClick for photo_view to select file from local
  }


  private class TypesGetter extends AsyncTask<Object, Object, List<ItemType>> {

    @Override
    protected List<ItemType> doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().getItemTypeDao().getAll();
    }

    @Override
    protected void onPostExecute(List<ItemType> itemTypes) {
      spinnerType.setAdapter(
          new ArrayAdapter<ItemType>(getActivity(), android.R.layout.simple_spinner_item,
              itemTypes));
    }
  }

  private class WardrobesGetter extends AsyncTask<Object, Object, List<Wardrobe>> {

    @Override
    protected List<Wardrobe> doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().getWardrobeDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Wardrobe> wardrobes) {
      spinnerWardrobe.setAdapter(
          new ArrayAdapter<Wardrobe>(getActivity(), android.R.layout.simple_spinner_item,
              wardrobes));
    }
  }

  @Override
  public void onActivityResult(int requestCode, int resultCode, Intent data) { //get back from activity camera
    Uri uri = FileProvider.getUriForFile(getActivity(),
        "edu.cnm.deepdive.worldofwardrobe.FileProvider",
        photoFile);
    getActivity().revokeUriPermission(uri,
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    if (photoFile == null || !photoFile.exists()) {
      photoView.setImageDrawable(null);
    } else {
      Bitmap bitmap = PictureUtils.getScaledBitmap(
          photoFile.getPath(), getActivity());
      photoView.setImageBitmap(bitmap);
    }
  }
}
