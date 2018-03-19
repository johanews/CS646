package com.group18.cs446.spacequest;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class SectorItems extends Fragment {
    View mView;

    ImageView weaponIconButton;
    ImageView shieldIconButton;
    ImageView engineIconButton;

    // weaponList
    int [] weaponIMAGES = {};
    String [] weaponNAMES = {"NAME1", "NAME2", "NAME3", "NAME4"};
    String [] weaponDESCRIPTIONS = {"weaponDESCRIPTION1", "weaponDESCRIPTION2", "weaponDESCRIPTION3", "weaponDESCRIPTION4"};
    String [] weaponPRICE = {"1000", "2000", "3000", "4000"};
    //int [] weaponID = getWeaponComponent();

    // shieldList
    int [] shieldIMAGES = {};
    String [] shieldNAMES = {"shieldNAME1", "shieldNAME2", "shieldNAME3", "shieldNAME4"};
    String [] shieldDESCRIPTIONS = {"shieldDESCRIPTION1", "shieldDESCRIPTION2", "shieldDESCRIPTION3", "shieldDESCRIPTION4"};
    String [] shieldPRICE = {"1000", "2000", "3000", "4000"};

    // engineList
    int [] engineIMAGES = {};
    String [] engineNAMES = {"engineNAME1", "engineNAME2", "engineNAME3", "engineNAME4"};
    String [] engineDESCRIPTIONS = {"engineDESCRIPTION1", "engineDESCRIPTION2", "engineDESCRIPTION3", "engineDESCRIPTION4"};
    String [] enginePRICE = {"1000", "2000", "3000", "4000"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.sector_items, container, false);

        // weapon list
        ListView weaponList = mView.findViewById(R.id.weapon_list);
        weaponListAdapter weaponAdapter = new weaponListAdapter();
        weaponList.setAdapter(weaponAdapter);
        weaponList.setVisibility(View.VISIBLE);

        // shield list
        ListView shieldList = mView.findViewById(R.id.shield_list);
        shieldListAdapter shieldAdapter = new shieldListAdapter();
        shieldList.setAdapter(shieldAdapter);
        shieldList.setVisibility(View.INVISIBLE);
        // engine list
        ListView engineList = mView.findViewById(R.id.engine_list);
        engineListAdapter engineAdapter = new engineListAdapter();
        engineList.setAdapter(engineAdapter);
        engineList.setVisibility(View.INVISIBLE);

        weaponIconButton = mView.findViewById(R.id.weapon_icon_button);
        weaponIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaponList.setVisibility(View.VISIBLE);
                shieldList.setVisibility(View.INVISIBLE);
                engineList.setVisibility(View.INVISIBLE);
            }
        });

        shieldIconButton = mView.findViewById(R.id.shield_icon_button);
        shieldIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaponList.setVisibility(View.INVISIBLE);
                shieldList.setVisibility(View.VISIBLE);
                engineList.setVisibility(View.INVISIBLE);
            }
        });

        engineIconButton = mView.findViewById(R.id.engine_icon_button);
        engineIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaponList.setVisibility(View.INVISIBLE);
                shieldList.setVisibility(View.INVISIBLE);
                engineList.setVisibility(View.VISIBLE);
            }
        });
        // add listener
        return mView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    class weaponListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return weaponNAMES.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.shop_item_element,null);
            ImageView imageView = view.findViewById(R.id.description_image);
            TextView descriptionText = view.findViewById(R.id.description_text);
            TextView priceText = view.findViewById(R.id.price_text);
            Button testButton = view.findViewById(R.id.buy_button);
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //priceText.setText("pressed");
                    showNormalDialog();
                }
            });

            descriptionText.setText(weaponDESCRIPTIONS[i]);
            priceText.setText(weaponPRICE[i]);

            return view;
        }
    } // weaponList

    class shieldListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return shieldNAMES.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.shop_item_element,null);
            ImageView imageView = view.findViewById(R.id.description_image);
            TextView descriptionText = view.findViewById(R.id.description_text);
            TextView priceText = view.findViewById(R.id.price_text);

            descriptionText.setText(shieldDESCRIPTIONS[i]);
            priceText.setText(shieldPRICE[i]);

            return view;
        }
    } // weaponList

    class engineListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return engineNAMES.length;
        }
        @Override
        public Object getItem(int i) {
            return null;
        }
        @Override
        public long getItemId(int i) {
            return 0;
        }
        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = getLayoutInflater().inflate(R.layout.shop_item_element,null);
            ImageView imageView = view.findViewById(R.id.description_image);
            TextView descriptionText = view.findViewById(R.id.description_text);
            TextView priceText = view.findViewById(R.id.price_text);

            descriptionText.setText(engineDESCRIPTIONS[i]);
            priceText.setText(enginePRICE[i]);

            return view;
        }
    } // engineList



    private void showNormalDialog() {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());

        normalDialog.setTitle("im a dialog");
        normalDialog.setMessage("u sure u want to buy?");

        normalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // update playerinfo

            }
        });
        normalDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                // dont do anything
            }
        });

        normalDialog.show();
    }


}
