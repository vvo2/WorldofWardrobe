package edu.cnm.deepdive.worldofwardrobe.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import edu.cnm.deepdive.worldofwardrobe.MainActivity;
import edu.cnm.deepdive.worldofwardrobe.R;
import edu.cnm.deepdive.worldofwardrobe.model.Item;
import edu.cnm.deepdive.worldofwardrobe.model.ItemType;
import edu.cnm.deepdive.worldofwardrobe.model.Wardrobe;
import java.util.List;

public class EditFragment extends Fragment implements OnClickListener {

  private Spinner spinnerType;
  private Spinner spinnerWardrobe;
  private ImageButton addButton;
  private Button addWardrobeButton;
  private Button addTypeButton;
  private View view;


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

          ((MainActivity) getActivity()).getDatabase().getItemDao().insertOne(addItem);

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

}
