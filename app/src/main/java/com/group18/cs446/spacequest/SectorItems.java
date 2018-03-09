package com.group18.cs446.spacequest;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListAdapter;
import android.widget.ListView;

public class SectorItems extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.sector_items, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ListView list_view = getView().findViewById(R.id.sector_items);

        ItemDescription one = new ItemDescription();
        ItemDescription two = new ItemDescription();

        ItemDescription[] list = {one, two};

        ListAdapter adapter = new CustomAdapter(this.getContext(), list);

       list_view.setAdapter(adapter);
    }
}
