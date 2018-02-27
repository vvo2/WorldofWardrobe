package edu.cnm.deepdive.worldofwardrobe.wardrobe;

import android.content.Context;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import java.util.ArrayList;

public class WardrobeStackAdapter extends BaseAdapter{

  ArrayList<WardrobeStackItems> wardrobeStackArrayList;
  LayoutInflater wardrobeStackInflater;
  ViewHolder wardrobeStackHolder = null;

  public WardrobeStackAdapter (Context context, ArrayList arraylist) {
    this.wardrobeStackArrayList = arraylist;
    this.wardrobeStackInflater = LayoutInflater.from(context);
  }

  @Override
  public int getCount() {
    return wardrobeStackArrayList.size();
  }

  @Override
  public WardrobeStackItems getItem(int position) {
    return wardrobeStackArrayList.get(position);
  }

  @Override
  public long getItemId(int position) {
    return position;
  }

  @Override
  public View getView(int position, View view, ViewGroup parent) {
    if (view == null) {
      view = wardrobeStackInflater.inflate(R.layout.item, parent, false);
      wardrobeStackHolder = new ViewHolder()

    }
  }
}
