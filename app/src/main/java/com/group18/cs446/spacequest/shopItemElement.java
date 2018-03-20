package com.group18.cs446.spacequest;

import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class shopItemElement extends LinearLayout {

    Button purchaseButton;
    TextView testText;

    public shopItemElement(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater inflater = LayoutInflater.from(context);
        inflater.inflate(R.layout.shop_item_element, this);

        testText = findViewById(R.id.description_text);

        purchaseButton = findViewById(R.id.buy_button);
        purchaseButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                showNormalDialog(context);
            }
        });

    }

    private void showNormalDialog(Context context) {
        final AlertDialog.Builder normalDialog = new AlertDialog.Builder(context);

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
