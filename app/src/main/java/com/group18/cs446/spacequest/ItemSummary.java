package com.group18.cs446.spacequest;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ItemSummary extends Fragment {

    private TextView item_title;
    private ImageView item_image;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.item_summary, container, false);

        item_title = view.findViewById(R.id.item_title);
        item_image = view.findViewById(R.id.item_image);

        return view;
    }

    public void setTitle(String title) {
        item_title.setText(title);
    }

    public void setImage(Bitmap image) {
        item_image.setImageBitmap(image);
    }

}
