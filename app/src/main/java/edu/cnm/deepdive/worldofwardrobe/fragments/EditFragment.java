package edu.cnm.deepdive.worldofwardrobe.fragments;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.MediaScannerConnection;
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
import android.widget.Toast;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import edu.cnm.deepdive.worldofwardrobe.model.ItemType;
import edu.cnm.deepdive.worldofwardrobe.model.PictureUtils;
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class EditFragment extends Fragment implements OnClickListener {

  private static final int REQUEST_PHOTO = 2;
  private static final int GALLERY = 1;

  private Spinner spinnerType;
  private Spinner spinnerWardrobe;
  private Button addWithCamera;
  private Button addWithFile;
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
    addWithCamera = view.findViewById(R.id.add_item_with_camera);
    addWithCamera.setOnClickListener(this);
    addWithFile = view.findViewById(R.id.add_item_with_file);
    addWithFile.setOnClickListener(this);

    addTypeButton = view.findViewById(R.id.button_add_itemtype);
    addTypeButton.setOnClickListener(this);
    addWardrobeButton = view.findViewById(R.id.button_add_wardrobe);
    addWardrobeButton.setOnClickListener(this);

    photoView = view.findViewById(R.id.view_photo);
    photoView.setOnClickListener(this);

    new TypesGetter().execute();
    new WardrobesGetter().execute();

    return view;
  }

  @Override
  public void onClick(final View v) {

    if (v == addWithCamera || v == addWithFile ) {

      final String strName = ((EditText)
          view.findViewById(R.id.edittext_name)).getText().toString();
      final double price = Double.parseDouble(((EditText)
          view.findViewById(R.id.edittext_price)).getText().toString());
      final String strLocation = ((EditText)
          view.findViewById(R.id.edittext_location)).getText().toString();
      final Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      final Intent galleryIntent = new Intent(Intent.ACTION_PICK,
          android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
          galleryIntent.setType("image/*");

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

          long itemID = ((MainActivity) getActivity()).getDatabase().getItemDao()
              .insertOne(addItem);

          photoFileName = "IMG_" + itemID + ".jpg";
          File filesDir = getActivity().getFilesDir();
          photoFile = new File(filesDir, photoFileName);
          Uri uri = FileProvider.getUriForFile(getActivity(),
              "edu.cnm.deepdive.worldofwardrobe.FileProvider", photoFile);

          if (v == addWithCamera) {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            List<ResolveInfo> cameraActivities = getActivity()
                .getPackageManager().queryIntentActivities(cameraIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo activity : cameraActivities) {
              getActivity().grantUriPermission(activity.activityInfo.packageName,
                  uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            startActivityForResult(cameraIntent, REQUEST_PHOTO);
          } else if (v == addWithFile) {
            galleryIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
            List<ResolveInfo> galleryActivities = getActivity()
                .getPackageManager().queryIntentActivities(galleryIntent,
                    PackageManager.MATCH_DEFAULT_ONLY);

            for (ResolveInfo activity : galleryActivities) {
              getActivity().grantUriPermission(activity.activityInfo.packageName,
                  uri, Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            }
            startActivityForResult(galleryIntent, GALLERY);
          }
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
    }
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
        "edu.cnm.deepdive.worldofwardrobe.FileProvider", photoFile);
    getActivity().revokeUriPermission(uri,
        Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
    if (photoFile == null || !photoFile.exists()) {
      photoView.setImageDrawable(null);
    } else {
      Bitmap bitmap = PictureUtils.getScaledBitmap(
          photoFile.getPath(), getActivity());
      photoView.setImageBitmap(bitmap);
    }

    if (requestCode == GALLERY) {
      if (data != null) {
        Uri selectedURI = data.getData();
        try {
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedURI);
        saveSelectedPic(bitmap);
        Toast.makeText(getActivity(), "Image Saved!", Toast.LENGTH_SHORT).show();
        photoView.setImageBitmap(bitmap);
        } catch (IOException e) {
          e.printStackTrace();
          Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
        }
      }
    }
  }

  private void saveSelectedPic(Bitmap saveBitmap) {
    ByteArrayOutputStream bytes = new ByteArrayOutputStream(); //output stream for data to be written in a byte array
    saveBitmap.compress(CompressFormat.PNG, 90, bytes); //TODO try PNG for transparent pixels
    File file = new File(photoFile.getPath());
    try {
      file.createNewFile();
      FileOutputStream outFile = new FileOutputStream(file); //streams of raw bytes such as image data
      outFile.write(bytes.toByteArray());
      MediaScannerConnection.scanFile(getActivity(),
          new String[]{file.getPath()},
          new String[]{"image/jpeg"}, null);
      outFile.close();
      Log.d("TAG", "File Saved::--->" + file.getAbsolutePath());
    } catch (IOException e) {
      e.printStackTrace();
      Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
    }
  }

}
