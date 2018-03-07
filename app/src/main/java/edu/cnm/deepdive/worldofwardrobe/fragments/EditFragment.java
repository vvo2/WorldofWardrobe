package edu.cnm.deepdive.worldofwardrobe.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import edu.cnm.deepdive.worldofwardrobe.model.ItemType;
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;
import java.util.List;

public class EditFragment extends Fragment implements OnClickListener{

  private Spinner spinnerType;
  private Spinner spinnerWardrobe;
  private ImageButton addButton;
  private ImageButton removeButton;
  private View view;


  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
     view = inflater.inflate(R.layout.fragment_edit, container, false);

    addButton = (ImageButton) view.findViewById(R.id.button_additem);
    addButton.setOnClickListener(this);

//    removeButton = (ImageButton) view.findViewById(R.id.button_subtract);
//    removeButton.setOnClickListener(removeMethod);

    spinnerType = view.findViewById(R.id.spinner_type);
    spinnerWardrobe = view.findViewById(R.id.spinner_wardrobe);

    new TypesGetter().execute();
    new WardrobesGetter().execute();
    //TODO new wardrobe Adder
    //TODO new type Adder

    return view;
  }

  @Override
  public void onClick(View v) {
    final String strName = ((EditText) view.findViewById(R.id.edittext_name)).getText().toString();
    final double price = Double.parseDouble(((EditText) view.findViewById(R.id.edittext_price)).getText().toString());
    final String strLocation = ((EditText) view.findViewById(R.id.edittext_location)).getText().toString();

    new Thread(new Runnable() {
      @Override
      public void run() {

        Item additem = new Item();
        additem.setItemTypeID(((ItemType)spinnerType.getSelectedItem()).getItemTypeID());
        additem.setWardrobeID(((Wardrobe)spinnerWardrobe.getSelectedItem()).getWardrobeID());
        additem.setItemName(strName);
        additem.setItemPrice(price);
        additem.setLocation(strLocation);
        additem.setWornCount(0);

        ((MainActivity) getActivity()).getDatabase().getItemDao().insertOne(additem);

      }
    }).start();
  }

  private class TypesGetter extends AsyncTask<Object, Object, List<ItemType>> {

    @Override
    protected List<ItemType> doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().getItemTypeDao().getAll();
    }

    @Override
    protected void onPostExecute(List<ItemType> itemTypes) {
      spinnerType.setAdapter(new ArrayAdapter<ItemType>(getActivity(), android.R.layout.simple_spinner_item, itemTypes));
    }
  }

  private class WardrobesGetter extends AsyncTask<Object, Object, List<Wardrobe>> {

    @Override
    protected List<Wardrobe> doInBackground(Object... objects) {
      return ((MainActivity) getActivity()).getDatabase().getWardrobeDao().getAll();
    }

    @Override
    protected void onPostExecute(List<Wardrobe> wardrobes) {
      spinnerWardrobe.setAdapter(new ArrayAdapter<Wardrobe>(getActivity(), android.R.layout.simple_spinner_item, wardrobes));
    }
  }

}
