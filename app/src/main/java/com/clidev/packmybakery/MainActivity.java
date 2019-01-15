package com.clidev.packmybakery;

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

                List<Integer> blueberryPackage = PackageCalculator.calculateBlueberry(mBlueberryNumber);

                Timber.d("small (2) package is: " + blueberryPackage.get(0));
                Timber.d("medium (5) package is: " + blueberryPackage.get(1));
                Timber.d("large (8) package is: " + blueberryPackage.get(2));


            }
        });

    }
}
