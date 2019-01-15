package com.clidev.packmybakery;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @BindView(R.id.loading_bar) ProgressBar mProgressBar;

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

                // turn on loading icon
                mProgressBar.setVisibility(View.VISIBLE);


                // Calculate the optimal combinations
                new CalculateTask().execute();

            }
        });

    }


    public class CalculateTask extends AsyncTask<Void, Void,  ArrayList<ArrayList<Integer>>> {


        @Override
        protected ArrayList<ArrayList<Integer>> doInBackground(Void... voids) {
            ArrayList<Integer> vegemitePackage = PackageLooper.calculateVegemite(mVegemiteNumber);
            ArrayList<Integer> blueberryPackage = PackageLooper.calculateBlueberry(mBlueberryNumber);
            ArrayList<Integer> croissantPackage = PackageLooper.calculateCroissant(mCroissantNumber);

            ArrayList<ArrayList<Integer>> allPackage = new ArrayList<>();
            allPackage.add(vegemitePackage);
            allPackage.add(blueberryPackage);
            allPackage.add(croissantPackage);

            return allPackage;
        }

        @Override
        protected void onPostExecute(ArrayList<ArrayList<Integer>> arrayLists) {
            mProgressBar.setVisibility(View.INVISIBLE);
            ArrayList<Integer> vegemitePackage = arrayLists.get(0);
            ArrayList<Integer> blueberryPackage = arrayLists.get(1);
            ArrayList<Integer> croissantPackage = arrayLists.get(2);

            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            intent.putIntegerArrayListExtra("VEGEMITE", vegemitePackage);
            intent.putIntegerArrayListExtra("BLUEBERRY", blueberryPackage);
            intent.putIntegerArrayListExtra("CROISSANT", croissantPackage);
            startActivity(intent);
        }
    }
}
