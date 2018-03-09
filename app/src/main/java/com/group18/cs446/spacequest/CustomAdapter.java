package com.group18.cs446.spacequest;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

public class CustomAdapter extends ArrayAdapter<ItemDescription> {

    public CustomAdapter(Context context, ItemDescription[] characters) {
        super(context, R.layout.item_description, characters);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater inflater = LayoutInflater.from(getContext());

        return inflater.inflate(R.layout.item_description, parent, false);
    }
}