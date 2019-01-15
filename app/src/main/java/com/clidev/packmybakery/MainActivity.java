package com.clidev.packmybakery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class MainActivity extends AppCompatActivity {

    private int mVegemiteNumber;
    private int mBlueberryNumber;
    private int mCroissantNumber;

    @BindView(R.id.vegemite_scroll_et) EditText mVegemiteScrollEt;
    @BindView(R.id.blueberry_muffin_et) EditText mBlueberryMuffinEt;
    @BindView(R.id.croissant_et) EditText mCroissantEt;
    @BindView(R.id.place_order_button) Button mPlaceOrderButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        // get the input value of each product
        placeOrderClicked();



    }

    // Get the inputted order number
    private void placeOrderClicked() {
        mPlaceOrderButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Obtain the number of each order
                try {
                    mVegemiteNumber = Integer.parseInt(mVegemiteScrollEt.getText().toString());
                } catch (Exception e) {
                    mVegemiteNumber = 0;
                }

                try {
                    mBlueberryNumber = Integer.parseInt(mBlueberryMuffinEt.getText().toString());
                } catch (Exception e) {
                    mBlueberryNumber = 0;
                }

                try {
                    mCroissantNumber = Integer.parseInt(mCroissantEt.getText().toString());
                } catch (Exception e) {
                    mCroissantNumber = 0;
                }

                // Calculate the optimal combinations
                ArrayList<Integer> vegemitePackage = PackageLooper.calculateVegemite(mVegemiteNumber);
                ArrayList<Integer> blueberryPackage = PackageLooper.calculateBlueberry(mBlueberryNumber);
                ArrayList<Integer> croissantPackage = PackageLooper.calculateCroissant(mCroissantNumber);

                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                intent.putIntegerArrayListExtra("VEGEMITE", vegemitePackage);
                intent.putIntegerArrayListExtra("BLUEBERRY", blueberryPackage);
                intent.putIntegerArrayListExtra("CROISSANT", croissantPackage);
                startActivity(intent);


                /*
                if (vegemitePackage.size() >= 1) {
                    Timber.d("///////////////////VEGEMITE //////////////////");
                    Timber.d("small (3) package is: " + vegemitePackage.get(0));
                    Timber.d("medium (5) package is: " + vegemitePackage.get(1));


                } else {
                    Timber.d("///////////////////BLUEBERRY //////////////////");
                    Timber.d("Optimal blueberry package method not found");
                }



                if (blueberryPackage.size() >= 1) {
                    Timber.d("///////////////////BLUEBERRY //////////////////");
                    Timber.d("small (2) package is: " + blueberryPackage.get(0));
                    Timber.d("medium (5) package is: " + blueberryPackage.get(1));
                    Timber.d("large (8) package is: " + blueberryPackage.get(2));

                } else {
                    Timber.d("///////////////////BLUEBERRY //////////////////");
                    Timber.d("Optimal blueberry package method not found");
                }


                if (croissantPackage.size() >= 1) {
                    Timber.d("///////////////////CROISSANT //////////////////");
                    Timber.d("small (3) package is: " + croissantPackage.get(0));
                    Timber.d("medium (5) package is: " + croissantPackage.get(1));
                    Timber.d("large (9) package is: " + croissantPackage.get(2));

                } else {
                    Timber.d("///////////////////CROISSANT //////////////////");
                    Timber.d("Optimal croissant package method not found");
                }
                */


            }
        });

    }
}
