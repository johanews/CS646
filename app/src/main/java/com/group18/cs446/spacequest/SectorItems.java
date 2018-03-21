package com.group18.cs446.spacequest;

import android.content.ComponentName;
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

import com.group18.cs446.spacequest.game.enums.Engines;
import com.group18.cs446.spacequest.game.enums.Hulls;
import com.group18.cs446.spacequest.game.enums.Shields;
import com.group18.cs446.spacequest.game.enums.Weapons;
import com.group18.cs446.spacequest.game.objects.player.ComponentFactory;
import com.group18.cs446.spacequest.game.objects.player.Engine;
import com.group18.cs446.spacequest.game.objects.player.Hull;
import com.group18.cs446.spacequest.game.objects.player.Shield;
import com.group18.cs446.spacequest.game.objects.player.Weapon;

public class SectorItems extends Fragment {
    View mView;

    ImageView weaponIconButton;
    ImageView shieldIconButton;
    ImageView engineIconButton;
    ImageView hullIconButton;

    ComponentFactory cf = new ComponentFactory();

    // weaponList

    int [] weaponIMAGES = {R.drawable.item_basic_laser_image, R.drawable.item_chain_laser_image, R.drawable.item_dual_laser_image};

    Weapons[] weaponIDs = cf.getWeaponIDs();
    String [] weaponNAMES = new String[weaponIDs.length];
    String [] weaponDESCRIPTIONS = new String[weaponIDs.length];;
    String [] weaponPRICE = new String[weaponIDs.length];

    // shieldList

    int [] shieldIMAGES = {R.drawable.item_basic_shield_image, R.drawable.item_laser_only_shield_image};

    Shields[] shieldIDs = cf.getShieldIDs();
    String [] shieldNAMES = new String[shieldIDs.length];
    String [] shieldDESCRIPTIONS = new String[shieldIDs.length];
    String [] shieldPRICE = new String[shieldIDs.length];

    // engineList

    int [] engineIMAGES = {R.drawable.item_basic_engine_image, R.drawable.item_fast_engine_image};

    Engines[] engineIDs = cf.getEngineIDs();
    String [] engineNAMES = new String[engineIDs.length];
    String [] engineDESCRIPTIONS = new String[engineIDs.length];
    String [] enginePRICE = new String[engineIDs.length];

    int [] hullIMAGES = {R.drawable.item_basic_hull_image};

    Hulls[] hullIDs = cf.getHullIDs();
    String [] hullNAMES = new String[hullIDs.length];
    String [] hullDESCRIPTIONS = new String[hullIDs.length];
    String [] hullPRICE = new String[hullIDs.length];

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.sector_items, container, false);


        for(int i = 0; i < weaponIDs.length; i++) {

            Weapon weapon = cf.getWeaponComponent(weaponIDs[i], getContext());
            weaponNAMES[i] = weapon.getName();
            weaponDESCRIPTIONS[i] = weapon.getDescription();
            weaponPRICE[i] = Integer.toString(weapon.getPrice());
        }

        for(int i = 0; i < shieldIDs.length; i++) {

            Shield shield = cf.getShieldComponent(shieldIDs[i], getContext());
            shieldNAMES[i] = shield.getName();
            shieldDESCRIPTIONS[i] = shield.getDescription();
            shieldPRICE[i] = Integer.toString(shield.getPrice());
        }

        for(int i = 0; i < hullIDs.length; i++) {

            Hull hull = cf.getHullComponent(hullIDs[i], getContext());
            hullNAMES[i] = hull.getName();
            hullDESCRIPTIONS[i] = hull.getDescription();
            hullPRICE[i] = Integer.toString(hull.getPrice());
        }

        for(int i = 0; i < engineIDs.length; i++) {

            Engine engine = cf.getEngineComponent(engineIDs[i], getContext());
            engineNAMES[i] = engine.getName();
            engineDESCRIPTIONS[i] = engine.getDescription();
            enginePRICE[i] = Integer.toString(engine.getPrice());
        }

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

        // hull list
        ListView hullList = mView.findViewById(R.id.hull_list);
        hullListAdapter hullAdapter = new hullListAdapter();
        hullList.setAdapter(hullAdapter);
        hullList.setVisibility(View.INVISIBLE);

        weaponIconButton = mView.findViewById(R.id.weapon_icon_button);
        weaponIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaponList.setVisibility(View.VISIBLE);
                shieldList.setVisibility(View.INVISIBLE);
                engineList.setVisibility(View.INVISIBLE);
                hullList.setVisibility(View.INVISIBLE);
            }
        });

        shieldIconButton = mView.findViewById(R.id.shield_icon_button);
        shieldIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaponList.setVisibility(View.INVISIBLE);
                shieldList.setVisibility(View.VISIBLE);
                engineList.setVisibility(View.INVISIBLE);
                hullList.setVisibility(View.INVISIBLE);
            }
        });

        engineIconButton = mView.findViewById(R.id.engine_icon_button);
        engineIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaponList.setVisibility(View.INVISIBLE);
                shieldList.setVisibility(View.INVISIBLE);
                engineList.setVisibility(View.VISIBLE);
                hullList.setVisibility(View.INVISIBLE);
            }
        });

        hullIconButton = mView.findViewById(R.id.hull_icon_button);
        hullIconButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                weaponList.setVisibility(View.INVISIBLE);
                shieldList.setVisibility(View.INVISIBLE);
                engineList.setVisibility(View.INVISIBLE);
                hullList.setVisibility(View.VISIBLE);
            }
        });

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
            TextView nameText = view.findViewById(R.id.name_text);
            TextView descriptionText = view.findViewById(R.id.description_text);
            TextView priceText = view.findViewById(R.id.price_text);
            Button testButton = view.findViewById(R.id.buy_button);
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNormalDialog(weaponNAMES[i], weaponIDs[i], "weapon", Integer.parseInt(weaponPRICE[i]));
                }
            });

            imageView.setImageResource(weaponIMAGES[i]);
            nameText.setText(weaponNAMES[i]);
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
            TextView nameText = view.findViewById(R.id.name_text);
            TextView descriptionText = view.findViewById(R.id.description_text);
            TextView priceText = view.findViewById(R.id.price_text);
            Button testButton = view.findViewById(R.id.buy_button);
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNormalDialog(shieldNAMES[i], shieldIDs[i], "shield", Integer.parseInt(shieldPRICE[i]));
                }
            });

            imageView.setImageResource(shieldIMAGES[i]);
            nameText.setText(shieldNAMES[i]);
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
            TextView nameText = view.findViewById(R.id.name_text);
            TextView descriptionText = view.findViewById(R.id.description_text);
            TextView priceText = view.findViewById(R.id.price_text);
            Button testButton = view.findViewById(R.id.buy_button);
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNormalDialog(engineNAMES[i], engineIDs[i], "engine", Integer.parseInt(enginePRICE[i]));
                }
            });

            imageView.setImageResource(engineIMAGES[i]);
            nameText.setText(engineNAMES[i]);
            descriptionText.setText(engineDESCRIPTIONS[i]);
            priceText.setText(enginePRICE[i]);

            return view;
        }
    } // engineList

    class hullListAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return hullNAMES.length;
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
            TextView nameText = view.findViewById(R.id.name_text);
            TextView descriptionText = view.findViewById(R.id.description_text);
            TextView priceText = view.findViewById(R.id.price_text);
            Button testButton = view.findViewById(R.id.buy_button);
            testButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    showNormalDialog(hullNAMES[i], hullIDs[i], "hull", Integer.parseInt(hullPRICE[i]));
                }
            });

            imageView.setImageResource(hullIMAGES[i]);
            nameText.setText(hullNAMES[i]);
            descriptionText.setText(hullDESCRIPTIONS[i]);
            priceText.setText(hullPRICE[i]);

            return view;
        }
    } // engineList


    // dialog part, costumization needed
    private void showNormalDialog(String name, Enum itemID, String category, int itemPrice) {

        int currentMoney = ((ShopActivity) getActivity()).getPlayerInfo().getMoney();

        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(getContext());

        if (itemPrice > currentMoney) {
            //normalDialog.setIcon();
            normalDialog.setTitle("Im a dialog");
            normalDialog.setMessage("You don't have enough money");
            normalDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {}
            });

        } else {
            //normalDialog.setIcon();
            normalDialog.setTitle("Im a dialog");
            normalDialog.setMessage("Do you want to buy " + name + "?");
            normalDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    if (category.equals("weapon")) {
                        ((ShopActivity) getActivity()).getPlayerInfo().setWeapon((Weapons) itemID);
                    } else if (category.equals("shield")) {
                        ((ShopActivity) getActivity()).getPlayerInfo().setShield((Shields) itemID);
                    } else if (category.equals("engine")) {
                        ((ShopActivity) getActivity()).getPlayerInfo().setEngine((Engines) itemID);
                    } else if (category.equals("hull")) {
                        ((ShopActivity) getActivity()).getPlayerInfo().setHull((Hulls) itemID);
                    } else {
                    }
                    ((ShopActivity) getActivity()).refresh();
                    // deduct money here
                }
            });
            normalDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                }
            });
        }

        normalDialog.show();
    }


}
