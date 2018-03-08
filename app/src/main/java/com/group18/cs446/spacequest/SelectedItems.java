package com.group18.cs446.spacequest;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class SelectedItems extends Fragment {

    FragmentManager fragmentManager;

    ItemSummary weaponFragment;
    ItemSummary engineFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.items_selected, container, false);

        fragmentManager = getChildFragmentManager();

        weaponFragment = (ItemSummary) fragmentManager.findFragmentById(R.id.current_weapon);
        engineFragment = (ItemSummary) fragmentManager.findFragmentById(R.id.current_engine);

        return view;
    }

    public ItemSummary getWeaponFragment() {
        return weaponFragment;
    }

    public ItemSummary getEngineFragment() {
        return engineFragment;
    }

}
